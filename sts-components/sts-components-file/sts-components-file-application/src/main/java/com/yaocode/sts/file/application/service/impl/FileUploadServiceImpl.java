package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
import com.yaocode.sts.common.crypto.utils.HexUtils;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.file.application.converter.FileUploadApplicationConverter;
import com.yaocode.sts.file.application.model.command.CancelMultipartCommand;
import com.yaocode.sts.file.application.model.command.CompleteMultipartCommand;
import com.yaocode.sts.file.application.model.command.FastUploadCommand;
import com.yaocode.sts.file.application.model.command.InitMultipartCommand;
import com.yaocode.sts.file.application.model.command.UploadBatchCommand;
import com.yaocode.sts.file.application.model.command.UploadFileCommand;
import com.yaocode.sts.file.application.model.command.UploadPartCommand;
import com.yaocode.sts.file.application.model.dto.FileObjectDto;
import com.yaocode.sts.file.application.model.dto.FileUploadDto;
import com.yaocode.sts.file.application.model.query.FileExistenceQuery;
import com.yaocode.sts.file.application.model.query.MultipartSessionQuery;
import com.yaocode.sts.file.application.model.query.UploadProgressQuery;
import com.yaocode.sts.file.application.model.result.CancelMultipartResult;
import com.yaocode.sts.file.application.model.result.FileExistenceResult;
import com.yaocode.sts.file.application.model.result.MultipartInitResult;
import com.yaocode.sts.file.application.model.result.MultipartSessionResult;
import com.yaocode.sts.file.application.model.result.UploadPartResult;
import com.yaocode.sts.file.application.model.result.UploadProgressResult;
import com.yaocode.sts.file.application.model.result.UploadResult;
import com.yaocode.sts.file.application.service.FileUploadService;
import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.core.constants.FileI18nKeyConstants;
import com.yaocode.sts.file.core.enums.ChunkStatusEnums;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.exception.FileUploadException;
import com.yaocode.sts.file.application.service.handler.FileDeduplicationHandler;
import com.yaocode.sts.file.application.service.handler.FilePersistenceHandler;
import com.yaocode.sts.file.application.service.handler.FileStorageSelectionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadCleanupHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadExecutionHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadPreparationHandler;
import com.yaocode.sts.file.application.service.handler.FileUploadValidationHandler;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.infrastructure.dao.FileBaseInfoDao;
import com.yaocode.sts.file.infrastructure.dao.FileChunkDao;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.dao.UploadSessionDao;
import com.yaocode.sts.file.infrastructure.entity.FileBasicInfoEntity;
import com.yaocode.sts.file.infrastructure.entity.FileChunkEntity;
import com.yaocode.sts.file.infrastructure.entity.UploadSessionEntity;
import com.yaocode.sts.file.infrastructure.manager.StoragePluginManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传服务实现（完整优化版）
 * <p>
 * 优化点：
 * 1. 流式处理 + 临时文件（解决内存问题）
 * 2. 短事务（解决事务范围过大）
 * 3. 职责清晰（策略执行委托给 Executor）
 * 4. 统一配置管理
 * 5. 统一异常处理
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private FileUploadValidationHandler validationHandler;
    @Resource
    private FileUploadPreparationHandler preparationHandler;
    @Resource
    private FileStorageSelectionHandler storageSelectionHandler;
    @Resource
    private FileDeduplicationHandler deduplicationHandler;
    @Resource
    private FileUploadExecutionHandler executionHandler;
    @Resource
    private FilePersistenceHandler persistenceHandler;
    @Resource
    private FileUploadCleanupHandler cleanupHandler;
    // 仅用于 checkFileExists 接口查询
    @Resource
    private FileDeduplicationDao fileDeduplicationDao;
    @Resource
    private FileBaseInfoDao fileBaseInfoDao;
    @Resource
    private FileUploadApplicationConverter fileUploadApplicationConverter;

    @Resource
    private MessageUtils messageUtils;

    @Resource
    private UploadSessionDao uploadSessionDao;
    @Resource
    private FileChunkDao fileChunkDao;
    @Resource
    private StoragePluginManager storagePluginManager;

    // ==================== 1. 主入口 ====================

    @Override
    public UploadResult upload(UploadFileCommand command) {
        FileUploadDto fileUploadDto = FileUploadDto.builder()
                .command(command)
                .startTime(System.currentTimeMillis())
                .build();
        try {
            validationHandler.handle(fileUploadDto);
            preparationHandler.handle(fileUploadDto);
            storageSelectionHandler.handle(fileUploadDto);
            deduplicationHandler.handle(fileUploadDto);
            executionHandler.handle(fileUploadDto);
            persistenceHandler.handle(fileUploadDto);
            return fileUploadDto.getResult();
        } catch (Exception e) {
            cleanupHandler.cleanupOnFailure(fileUploadDto);
            throw e;
        }
    }

    @Override
    public List<UploadResult> uploadBatch(UploadBatchCommand command) {
        List<UploadResult> results = new ArrayList<>();
        if (command.getFiles() == null || command.getFiles().isEmpty()) {
            return results;
        }
        for (FileObjectDto file : command.getFiles()) {
            try {
                UploadFileCommand fileCommand = fileUploadApplicationConverter.buildUploadFileCommand(command, file);
                UploadResult result = this.upload(fileCommand);
                results.add(result);
            } catch (Exception e) {
                log.warn("批量上传文件失败: {}", file.getFileName(), e);
                results.add(UploadResult.builder()
                        .fileName(file.getFileName())
                        .fileSize(file.getFileSize())
                        .fileMd5(file.getMd5())
                        .uploadStatus(UploadStatusEnums.FAILED.getCode())
                        .uploadStatusDesc(UploadStatusEnums.FAILED.getDesc())
                        .message(messageUtils.resolveExceptionMessage(e))
                        .build());
            }
        }
        return results;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MultipartInitResult initMultipartUpload(InitMultipartCommand command) {
        long startTime = System.currentTimeMillis();
        Long chunkSize = command.getChunkSize() != null ? command.getChunkSize() : FileConstants.ONE_MB * 10;
        long fileSize = command.getFileSize() != null ? command.getFileSize() : 0L;
        StorageTypeEnums storageType = StorageTypeEnums.fromCode(command.getStorageType());
        if (storageType == null) {
            storageType = StorageTypeEnums.LOCAL;
        }

        // === 第一重检查：秒传（相同MD5的文件已存在） ===
        if (command.getFileMd5() != null && !command.getFileMd5().isEmpty()) {
            FileExistenceResult existResult = deduplicationHandler.checkFileExists(
                    command.getFileMd5(), fileSize, storageType.getCode(), command.getTenantId()
            );
            if (existResult != null && Boolean.TRUE.equals(existResult.getExists())) {
                log.info("秒传命中: md5={}, fileId={}", command.getFileMd5(), existResult.getFileId());
                return fileUploadApplicationConverter.toDuplicateInitResult(
                        command, existResult.getFileId()
                );
            }

            // === 第二重检查：续传（已有活动中的上传会话） ===
            UploadSessionEntity activeSession = uploadSessionDao.selectActiveSession(
                    command.getFileMd5(), fileSize, storageType.getCode(), command.getTenantId()
            );
            if (activeSession != null) {
                log.info("续传命中: md5={}, uploadId={}, 已完成分片={}",
                        command.getFileMd5(), activeSession.getUploadId(), activeSession.getCompletedChunks());
                return fileUploadApplicationConverter.toResumeInitResult(activeSession);
            }
        }

        // === 第三重：创建新会话 ===
        String uploadId = IdFactory.generate(IdGeneratorType.UUID);
        String fileId = IdFactory.generate(IdGeneratorType.UUID);
        int totalChunks = fileSize > 0 ? (int) Math.ceil((double) fileSize / chunkSize) : 1;
        LocalDateTime expireTime = LocalDateTime.now().plusHours(24);

        UploadSessionEntity sessionEntity = fileUploadApplicationConverter.toUploadSessionEntity(
                command, uploadId, fileId, fileSize, chunkSize, totalChunks,
                storageType.getCode(), expireTime
        );
        uploadSessionDao.save(sessionEntity);

        log.info("初始化分片上传(新会话): uploadId={}, fileId={}, totalChunks={}, 耗时={}ms",
                uploadId, fileId, totalChunks, System.currentTimeMillis() - startTime);

        return fileUploadApplicationConverter.toMultipartInitResult(
                command, uploadId, fileId, chunkSize, totalChunks, expireTime
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadPartResult uploadPart(UploadPartCommand command) {
        long startTime = System.currentTimeMillis();
        // 1. 校验会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                command.getUploadId(), command.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }
        if (UploadStatusEnums.CANCELLED.getCode().equals(sessionEntity.getUploadStatus())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CANCEL_FAILED);
        }
        if (UploadStatusEnums.COMPLETED.getCode().equals(sessionEntity.getUploadStatus())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_COMPLETED);
        }
        // 1.1 校验会话是否过期
        if (sessionEntity.getExpireTime() != null && sessionEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_EXPIRED);
        }
        // 1.2 校验分片序号范围
        Integer chunkNumber = command.getChunkNumber();
        Integer totalChunks = sessionEntity.getTotalChunks();
        if (chunkNumber == null || chunkNumber < 1 || chunkNumber > totalChunks) {
            throw new FileUploadException(FileErrorCodeEnums.CHUNK_NUMBER_OUT_OF_RANGE);
        }
        // 1.3 校验 fileId 归属
        if (!command.getFileId().equals(sessionEntity.getFileId())) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_ID_NOT_IN_UPLOAD);
        }
        // 2. 获取存储插件
        StorageTypeEnums storageEnum = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageEnum);
        if (plugin == null) {
            throw new FileUploadException(FileErrorCodeEnums.STORAGE_TYPE_NOT_SUPPORTED);
        }
        // 3. 获取或创建分片记录
        FileChunkEntity chunkEntity = fileChunkDao.selectByUploadIdAndChunkNumber(
                command.getUploadId(), chunkNumber);
        // 3.1 检测分片编号与内容是否匹配（防止客户端错把分片内容传错编号）
        if (chunkEntity != null && chunkEntity.getChunkMd5() != null
                && command.getChunkMd5() != null
                && !chunkEntity.getChunkMd5().equals(command.getChunkMd5())) {
            log.warn("分片编号与内容不匹配: uploadId={}, chunkNumber={}, 存储的md5={}, 客户端md5={}",
                    command.getUploadId(), chunkNumber, chunkEntity.getChunkMd5(), command.getChunkMd5());
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MD5_MISMATCH);
        }
        // 3.2 如果分片已完成且MD5一致，直接返回（幂等）
        if (chunkEntity != null && ChunkStatusEnums.COMPLETED.getCode().equals(chunkEntity.getChunkStatus())
                && chunkEntity.getChunkMd5() != null
                && chunkEntity.getChunkMd5().equals(command.getChunkMd5())) {
            int completedChunks = sessionEntity.getCompletedChunks() != null
                    ? sessionEntity.getCompletedChunks() : 0;
            return fileUploadApplicationConverter.toUploadPartResult(
                    command.getUploadId(), command.getFileId(), chunkNumber, totalChunks, completedChunks);
        }
        long chunkSize = fileUploadApplicationConverter.resolveChunkSize(command, sessionEntity);
        // 3.1 校验分片大小（最后一片允许小于等于 chunkSize）
        if (chunkSize > sessionEntity.getChunkSize() + 1) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_SIZE_INVALID);
        }
        if (chunkEntity == null) {
            chunkEntity = fileUploadApplicationConverter.toNewFileChunkEntity(command, sessionEntity, chunkNumber, chunkSize);
            fileChunkDao.save(chunkEntity);
        } else {
            // 重试场景：更新状态
            chunkEntity.setChunkStatus(ChunkStatusEnums.UPLOADING.getCode());
            chunkEntity.setRetryCount(chunkEntity.getRetryCount() != null ? chunkEntity.getRetryCount() + 1 : 1);
            chunkEntity.setErrorMessage(null);
            chunkEntity.setUploadStartTime(LocalDateTime.now());
            fileChunkDao.updateById(chunkEntity);
        }
        // 4. 上传分片到存储（同时计算 MD5）
        String chunkPath = null;
        String actualMd5 = null;
        FileObjectDto file = command.getFile();
        try {
            if (file == null || file.getInputStream() == null) {
                throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MISSING);
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            try (DigestInputStream dis = new DigestInputStream(file.getInputStream(), md)) {
                chunkPath = plugin.uploadChunk(dis, command.getUploadId(), chunkNumber, chunkSize);
            } catch (IOException e) {
                throw new FileUploadException(FileErrorCodeEnums.UPLOAD_FAILED, e);
            }
            actualMd5 = HexUtils.bytesToHex(md.digest());
            // 4.1 MD5 校验：客户端传了 chunkMd5 则必须与实际一致
            if (command.getChunkMd5() != null && !command.getChunkMd5().equalsIgnoreCase(actualMd5)) {
                log.warn("分片MD5校验失败: uploadId={}, chunkNumber={}, 客户端md5={}, 实际md5={}",
                        command.getUploadId(), chunkNumber, command.getChunkMd5(), actualMd5);
                throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MD5_MISMATCH);
            }
            // 5. 更新分片状态为已完成
            chunkEntity.setChunkMd5(actualMd5);
            fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                    ChunkStatusEnums.COMPLETED.getCode(), chunkPath);
            // 6. 更新会话已完成分片数
            int completedChunks = fileChunkDao.countCompletedByUploadId(command.getUploadId());
            sessionEntity.setCompletedChunks(completedChunks);
            sessionEntity.setLastActiveTime(LocalDateTime.now());
            uploadSessionDao.updateById(sessionEntity);
        } catch (FileUploadException e) {
            // 标记分片失败
            if (!Objects.equals(chunkEntity.getChunkStatus(), ChunkStatusEnums.FAILED.getCode())) {
                fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                        ChunkStatusEnums.FAILED.getCode(), null);
            }
            throw e;
        } catch (Exception e) {
            // 标记分片失败
            fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                    ChunkStatusEnums.FAILED.getCode(), null);
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_FAILED, e);
        }
        // 7. 构建返回
        int completedChunks = sessionEntity.getCompletedChunks() != null
                ? sessionEntity.getCompletedChunks() : 0;
        log.info("分片上传成功: uploadId={}, chunkNumber={}, completedChunks={}, 耗时={}ms",
                command.getUploadId(), chunkNumber, completedChunks, System.currentTimeMillis() - startTime);
        return fileUploadApplicationConverter.toUploadPartResult(
                command.getUploadId(), command.getFileId(), chunkNumber, totalChunks, completedChunks);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult completeMultipartUpload(CompleteMultipartCommand command) {
        long startTime = System.currentTimeMillis();
        String uploadId = command.getUploadId();
        String tenantId = command.getTenantId();

        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(uploadId, tenantId);
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }

        // 2. 幂等检查：会话已完成，直接返回已持久化的文件信息，避免重复请求
        if (UploadStatusEnums.COMPLETED.getCode().equals(sessionEntity.getUploadStatus())) {
            log.info("分片上传已完成（幂等返回）: uploadId={}, fileId={}", uploadId, sessionEntity.getFileId());
            FileBasicInfoEntity existed = fileBaseInfoDao.selectByFileIdAndTenant(
                    sessionEntity.getFileId(), tenantId);
            if (existed != null) {
                long processingTime = System.currentTimeMillis() - startTime;
                String message = messageUtils.getMessage(FileI18nKeyConstants.UPLOAD_SUCCESS);
                return fileUploadApplicationConverter.toUploadResultFromMultipart(
                        existed, command, message, processingTime
                );
            }
            // 文件记录不存在（异常情况），继续走合并流程
            log.warn("会话已完成但文件记录不存在，重新执行合并: uploadId={}", uploadId);
        }

        // 3. 状态检查：已取消或已失败的会话不能再完成
        if (UploadStatusEnums.CANCELLED.getCode().equals(sessionEntity.getUploadStatus())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CANCEL_FAILED);
        }
        if (UploadStatusEnums.FAILED.getCode().equals(sessionEntity.getUploadStatus())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_FAILED);
        }

        // 4. 检查所有分片是否完成（包含已软删除的分片记录）
        List<FileChunkEntity> allChunks = fileChunkDao.selectByUploadIdAndTenantId(uploadId, tenantId);
        int completedCount = (int) allChunks.stream()
                .filter(c -> ChunkStatusEnums.COMPLETED.getCode().equals(c.getChunkStatus()))
                .count();
        if (completedCount < sessionEntity.getTotalChunks()) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MISSING);
        }

        // 5. 获取存储插件并合并分片
        StorageTypeEnums storageTypeEnums = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageTypeEnums);
        if (plugin == null) {
            throw new FileUploadException(FileErrorCodeEnums.STORAGE_TYPE_NOT_SUPPORTED);
        }
        String mergedPath = plugin.mergeChunks(uploadId, sessionEntity.getFileId());
        String fileUrl = plugin.getFileUrl(mergedPath);
        String fileMd5 = sessionEntity.getFileMd5();
        // 7. 持久化文件信息
        FileBasicInfoEntity fileInfoEntity = fileUploadApplicationConverter.toFileInfoEntityFromMultipart(
                command, sessionEntity, sessionEntity.getFileId(), mergedPath, fileUrl, fileMd5
        );
        fileBaseInfoDao.save(fileInfoEntity);

        // 8. 更新会话状态为已完成
        uploadSessionDao.updateStatus(uploadId,
                UploadStatusEnums.COMPLETED.getCode(), sessionEntity.getTotalChunks());

        // 9. 清理分片记录和临时文件（物理清理存储，逻辑清理分片记录）
        fileChunkDao.deleteByUploadIdAndTenantId(uploadId, tenantId);
        try {
            plugin.cleanupChunks(uploadId);
        } catch (Exception e) {
            // 清理失败不影响主流程，避免客户端无法获取已合并的文件
            log.warn("清理分片临时文件失败: uploadId={}, error={}", uploadId, e.getMessage());
        }

        long processingTime = System.currentTimeMillis() - startTime;
        log.info("分片上传完成: uploadId={}, fileId={}, 耗时={}ms",
                uploadId, sessionEntity.getFileId(), processingTime);
        String message = messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS);
        return fileUploadApplicationConverter.toUploadResultFromMultipart(
                command, sessionEntity.getFileId(), fileUrl, sessionEntity.getFileSize(), fileMd5, message, processingTime
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CancelMultipartResult cancelMultipartUpload(CancelMultipartCommand command) {
        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                command.getUploadId(), command.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }

        // 2. 幂等处理：已取消则直接返回成功
        if (UploadStatusEnums.CANCELLED.getCode().equals(sessionEntity.getUploadStatus())) {
            log.info("分片上传已取消（幂等返回）: uploadId={}", command.getUploadId());
            int totalUploadedChunks = sessionEntity.getCompletedChunks() != null
                    ? sessionEntity.getCompletedChunks() : 0;
            return fileUploadApplicationConverter.toCancelMultipartResult(
                    command.getUploadId(), sessionEntity.getFileId(),
                    0, totalUploadedChunks,
                    command.getReason(), "会话已取消");
        }

        // 3. 更新会话状态为已取消
        int completedChunks = sessionEntity.getCompletedChunks() != null
                ? sessionEntity.getCompletedChunks() : 0;
        uploadSessionDao.updateStatus(command.getUploadId(),
                UploadStatusEnums.CANCELLED.getCode(), completedChunks);

        // 4. 批量更新所有未完成的分片状态为已取消
        int cancelledChunks = fileChunkDao.batchUpdateStatusByUploadId(
                command.getUploadId(), command.getTenantId(),
                ChunkStatusEnums.CANCELLED.getCode(),
                ChunkStatusEnums.COMPLETED.getCode(),
                command.getReason()
        );

        // 5. 清理存储中的临时分片（异常容错）
        StorageTypeEnums storageTypeEnums = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageTypeEnums);
        if (plugin != null) {
            try {
                plugin.cleanupChunks(command.getUploadId());
            } catch (Exception e) {
                log.warn("清理分片临时文件失败: uploadId={}, error={}", command.getUploadId(), e.getMessage());
            }
        }

        log.info("取消分片上传: uploadId={}, fileId={}, cancelledChunks={}, reason={}",
                command.getUploadId(), sessionEntity.getFileId(), cancelledChunks, command.getReason());

        String message = messageUtils.getMessage(FileI18nKeyConstants.UPLOAD_CANCEL_FAILED);
        return fileUploadApplicationConverter.toCancelMultipartResult(
                command.getUploadId(), sessionEntity.getFileId(),
                cancelledChunks, completedChunks,
                command.getReason(), message
        );
    }

    @Override
    public UploadProgressResult getMultipartProgress(UploadProgressQuery query) {
        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                query.getUploadId(), query.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }
        // 1.1 校验会话是否过期
        if (sessionEntity.getExpireTime() != null && sessionEntity.getExpireTime().isBefore(LocalDateTime.now())) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_EXPIRED);
        }
        // 2. 统计已上传大小
        List<FileChunkEntity> completedChunks = fileChunkDao.selectCompletedByUploadIdAndTenantId(query.getUploadId(), query.getTenantId());
        long uploadedSize = completedChunks.stream()
                .mapToLong(c -> c.getChunkSize() != null ? c.getChunkSize() : 0L)
                .sum();
        // 3. 获取状态描述
        String status = UploadStatusEnums.fromCode(sessionEntity.getUploadStatus()).getDesc();
        String message = null;
        if (UploadStatusEnums.COMPLETED.getCode().equals(sessionEntity.getUploadStatus())) {
            message = messageUtils.getMessage(FileI18nKeyConstants.UPLOAD_SUCCESS);
        } else if (UploadStatusEnums.UPLOADING.getCode().equals(sessionEntity.getUploadStatus())) {
            message = messageUtils.getMessage(FileI18nKeyConstants.UPLOAD_IN_PROGRESS);
        }
        return fileUploadApplicationConverter.toUploadProgressResult(
                sessionEntity, uploadedSize, status, message
        );
    }

    @Override
    public PageResult<MultipartSessionResult> getMultipartSessions(MultipartSessionQuery query) {
        int page = query.getPage() != null ? query.getPage() : 1;
        int size = query.getSize() != null ? query.getSize() : 10;
        // 1. 分页查询会话
        PageResult<UploadSessionEntity> pageResult = uploadSessionDao.pageByTenant(
                query.getTenantId(), query.getStatus(), page, size);
        // 2. 转换结果
        List<MultipartSessionResult> records = pageResult.getRecords().stream()
                .map(fileUploadApplicationConverter::toMultipartSessionResult)
                .toList();
        return PageResult.<MultipartSessionResult>builder()
                .total(pageResult.getTotal())
                .page(pageResult.getPage())
                .size(pageResult.getSize())
                .records(records)
                .build();
    }

    // ==================== 7. 对外接口 ====================

    @Override
    public FileExistenceResult checkFileExists(FileExistenceQuery query) {
        return deduplicationHandler.checkFileExists(query.getFileMd5(), query.getFileSize(), query.getStorageType(), query.getTenantId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult fastUpload(FastUploadCommand command) {
        long startTime = System.currentTimeMillis();

        // 1. 检查文件是否已存在
        FileExistenceResult existenceResult = deduplicationHandler.checkFileExists(
                command.getFileMd5(),
                command.getFileSize(),
                command.getStorageType(),
                command.getTenantId()
        );

        // 2. 文件不存在，无法秒传
        if (existenceResult == null || !existenceResult.getExists()) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_NOT_FOUND);
        }

        // 3. 查询原文件实体（获取完整存储信息）
        FileBasicInfoEntity originalEntity = fileBaseInfoDao.selectByFileIdAndTenant(
                existenceResult.getFileId(), command.getTenantId()
        );
        if (originalEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.FILE_NOT_FOUND);
        }

        // 4. 创建引用记录（新 fileId，复用物理文件）
        String newFileId = IdFactory.generate(IdGeneratorType.UUID);
        FileBasicInfoEntity refEntity = fileUploadApplicationConverter.toFastUploadEntity(
                command, originalEntity, newFileId
        );
        fileBaseInfoDao.save(refEntity);

        // 5. 构建返回结果
        long processingTime = System.currentTimeMillis() - startTime;
        String message = messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS);
        UploadResult uploadResult = fileUploadApplicationConverter.toFastUploadResult(
                newFileId, command, originalEntity, message, processingTime
        );

        log.info("秒传成功: newFileId={}, originalFileId={}, 耗时={}ms",
                newFileId, originalEntity.getFileId(), processingTime);
        return uploadResult;
    }
}