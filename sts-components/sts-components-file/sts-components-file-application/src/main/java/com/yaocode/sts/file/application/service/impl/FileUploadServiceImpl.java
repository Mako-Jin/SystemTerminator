package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.basic.model.PageResult;
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

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        // 2. 获取存储插件
        StorageTypeEnums storageEnum = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageEnum);
        if (plugin == null) {
            throw new FileUploadException(FileErrorCodeEnums.STORAGE_TYPE_NOT_SUPPORTED);
        }
        // 3. 获取或创建分片记录
        Integer chunkNumber = command.getChunkNumber();
        FileChunkEntity chunkEntity = fileChunkDao.selectByUploadIdAndChunkNumber(
                command.getUploadId(), chunkNumber);
        if (chunkEntity == null) {
            chunkEntity = new FileChunkEntity();
            chunkEntity.setUploadId(command.getUploadId());
            chunkEntity.setFileId(command.getFileId());
            chunkEntity.setChunkNumber(chunkNumber);
            chunkEntity.setChunkSize(command.getFile() != null ? command.getFile().getFileSize() : sessionEntity.getChunkSize());
            chunkEntity.setChunkMd5(command.getChunkMd5());
            chunkEntity.setStorageType(sessionEntity.getStorageType());
            chunkEntity.setChunkStatus(ChunkStatusEnums.UPLOADING.getCode());
            chunkEntity.setUploadStartTime(LocalDateTime.now());
            chunkEntity.setTenantId(command.getTenantId());
            fileChunkDao.save(chunkEntity);
        } else {
            // 重试场景：更新状态
            chunkEntity.setChunkStatus(ChunkStatusEnums.UPLOADING.getCode());
            chunkEntity.setRetryCount(chunkEntity.getRetryCount() != null ? chunkEntity.getRetryCount() + 1 : 1);
            chunkEntity.setErrorMessage(null);
            chunkEntity.setUploadStartTime(LocalDateTime.now());
            fileChunkDao.updateById(chunkEntity);
        }
        // 4. 上传分片到存储
        String chunkPath = null;
        try {
            FileObjectDto file = command.getFile();
            if (file == null || file.getInputStream() == null) {
                throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MISSING);
            }
            try (InputStream is = file.getInputStream()) {
                chunkPath = plugin.uploadChunk(is, command.getUploadId(), chunkNumber, chunkEntity.getChunkSize());
            } catch (IOException e) {
                throw new FileUploadException(FileErrorCodeEnums.UPLOAD_FAILED, e);
            }
            // 5. 更新分片状态为已完成
            fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                    ChunkStatusEnums.COMPLETED.getCode(), chunkPath);
            // 6. 更新会话已完成分片数
            int completedChunks = fileChunkDao.countCompletedByUploadId(command.getUploadId());
            sessionEntity.setCompletedChunks(completedChunks);
            sessionEntity.setLastActiveTime(LocalDateTime.now());
            uploadSessionDao.updateById(sessionEntity);
        } catch (FileUploadException e) {
            // 标记分片失败
            fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                    ChunkStatusEnums.FAILED.getCode(), null);
            // 业务层主动抛出的异常（如 UPLOAD_CHUNK_MISSING）保持原样
            throw e;
        } catch (Exception e) {
            // 标记分片失败
            fileChunkDao.updateStatus(command.getUploadId(), chunkNumber,
                    ChunkStatusEnums.FAILED.getCode(), null);
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_FAILED, e);
        }
        // 7. 构建返回
        Integer totalChunks = sessionEntity.getTotalChunks();
        int completedChunks = sessionEntity.getCompletedChunks() != null
                ? sessionEntity.getCompletedChunks() : 0;
        int progress = totalChunks > 0 ? (int) Math.round((completedChunks * 100.0) / totalChunks) : 0;
        log.info("分片上传成功: uploadId={}, chunkNumber={}, completedChunks={}, 耗时={}ms",
                command.getUploadId(), chunkNumber, completedChunks, System.currentTimeMillis() - startTime);
        return UploadPartResult.builder()
                .uploadId(command.getUploadId())
                .fileId(command.getFileId())
                .chunkNumber(chunkNumber)
                .totalChunks(totalChunks)
                .success(true)
                .uploadedChunks(completedChunks)
                .progress(progress)
                .build();
    }

    @Override
    public UploadResult completeMultipartUpload(CompleteMultipartCommand command) {
        long startTime = System.currentTimeMillis();
        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                command.getUploadId(), command.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }
        // 2. 检查所有分片是否完成
        List<FileChunkEntity> allChunks = fileChunkDao.selectByUploadIdAndTenantId(command.getUploadId(), command.getTenantId());
        int completedCount = (int) allChunks.stream()
                .filter(c -> ChunkStatusEnums.COMPLETED.getCode().equals(c.getChunkStatus()))
                .count();
        if (completedCount < sessionEntity.getTotalChunks()) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_CHUNK_MISSING);
        }
        // 3. 获取存储插件并合并分片
        StorageTypeEnums storageTypeEnums = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageTypeEnums);
        if (plugin == null) {
            throw new FileUploadException(FileErrorCodeEnums.STORAGE_TYPE_NOT_SUPPORTED);
        }
        String mergedPath = plugin.mergeChunks(command.getUploadId(), sessionEntity.getFileId());
        String fileUrl = plugin.getFileUrl(mergedPath);
        // 4. 获取合并后文件的MD5
        String fileMd5 = null;
        if (!allChunks.isEmpty()) {
            fileMd5 = allChunks.get(0).getChunkMd5();
        }
        // 5. 持久化文件信息
        FileBasicInfoEntity fileInfoEntity = fileUploadApplicationConverter.toFileInfoEntityFromMultipart(
                command, sessionEntity, sessionEntity.getFileId(), mergedPath, fileUrl, fileMd5
        );
        fileBaseInfoDao.save(fileInfoEntity);
        // 6. 更新会话状态为已完成
        uploadSessionDao.updateStatus(command.getUploadId(),
                UploadStatusEnums.COMPLETED.getCode(), sessionEntity.getTotalChunks());
        // 7. 清理分片记录和临时文件
        fileChunkDao.deleteByUploadIdAndTenantId(command.getUploadId(), command.getTenantId());
        plugin.cleanupChunks(command.getUploadId());
        long processingTime = System.currentTimeMillis() - startTime;
        log.info("分片上传完成: uploadId={}, fileId={}, 耗时={}ms",
                command.getUploadId(), sessionEntity.getFileId(), processingTime);
        String message = messageUtils.getMessage(FileI18nKeyConstants.STRATEGY_REUSE_SUCCESS);
        return fileUploadApplicationConverter.toUploadResultFromMultipart(
                command, sessionEntity.getFileId(), fileUrl, sessionEntity.getFileSize(), fileMd5, message, processingTime
        );
    }

    @Override
    public void cancelMultipartUpload(CancelMultipartCommand command) {
        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                command.getUploadId(), command.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
        }
        // 2. 更新会话状态为已取消
        uploadSessionDao.updateStatus(command.getUploadId(),
                UploadStatusEnums.CANCELLED.getCode(),
                sessionEntity.getCompletedChunks() != null ? sessionEntity.getCompletedChunks() : 0);
        // 3. 更新所有分片状态为已取消
        List<FileChunkEntity> allChunks = fileChunkDao.selectByUploadIdAndTenantId(command.getUploadId(), command.getTenantId());
        for (FileChunkEntity chunk : allChunks) {
            if (!ChunkStatusEnums.COMPLETED.getCode().equals(chunk.getChunkStatus())) {
                chunk.setChunkStatus(ChunkStatusEnums.CANCELLED.getCode());
                chunk.setErrorMessage(command.getReason());
                fileChunkDao.updateById(chunk);
            }
        }
        // 4. 清理存储中的临时分片
        StorageTypeEnums storageTypeEnums = StorageTypeEnums.fromCode(sessionEntity.getStorageType());
        StoragePlugin plugin = storagePluginManager.getPlugin(storageTypeEnums);
        if (plugin != null) {
            plugin.cleanupChunks(command.getUploadId());
        }
        log.info("取消分片上传: uploadId={}, reason={}", command.getUploadId(), command.getReason());
    }

    @Override
    public UploadProgressResult getMultipartProgress(UploadProgressQuery query) {
        // 1. 查询会话
        UploadSessionEntity sessionEntity = uploadSessionDao.selectByUploadIdAndTenant(
                query.getUploadId(), query.getTenantId());
        if (sessionEntity == null) {
            throw new FileUploadException(FileErrorCodeEnums.UPLOAD_SESSION_NOT_FOUND);
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
            message = "上传完成";
        } else if (UploadStatusEnums.UPLOADING.getCode().equals(sessionEntity.getUploadStatus())) {
            message = "上传中";
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