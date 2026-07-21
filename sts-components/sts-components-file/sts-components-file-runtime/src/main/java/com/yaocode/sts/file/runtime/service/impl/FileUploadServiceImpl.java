package com.yaocode.sts.file.runtime.service.impl;

import com.yaocode.sts.common.basic.exception.BusinessException;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.runtime.converter.FileUploadRuntimeConverter;
import com.yaocode.sts.file.runtime.entity.FileInfoEntity;
import com.yaocode.sts.file.runtime.manager.StoragePluginManager;
import com.yaocode.sts.file.runtime.mapper.FileChunkMapper;
import com.yaocode.sts.file.runtime.mapper.FileInfoMapper;
import com.yaocode.sts.file.runtime.mapper.UploadSessionMapper;
import com.yaocode.sts.file.runtime.mapper.UploadTaskMapper;
import com.yaocode.sts.file.runtime.model.command.UploadFileCommand;
import com.yaocode.sts.file.runtime.model.query.FileExistenceQuery;
import com.yaocode.sts.file.runtime.model.result.FileExistenceResult;
import com.yaocode.sts.file.runtime.model.result.UploadResult;
import com.yaocode.sts.file.runtime.selector.StorageSelector;
import com.yaocode.sts.file.runtime.service.FileUploadService;
import com.yaocode.sts.file.runtime.util.FileMd5Util;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 文件上传服务实现
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Resource
    private FileInfoMapper fileInfoMapper;
    @Resource
    private FileChunkMapper fileChunkMapper;
    @Resource
    private UploadSessionMapper uploadSessionMapper;
    @Resource
    private UploadTaskMapper uploadTaskMapper;
    @Resource
    private StoragePluginManager pluginManager;
    @Resource
    private StorageSelector storageSelector;
    @Resource
    private FileUploadRuntimeConverter fileUploadRuntimeConverter;

    @Value("${sts.file.upload.chunk-size:10485760}")
    private long defaultChunkSize;

    @Value("${sts.file.upload.max-file-size:1073741824}")
    private long maxFileSize;

//    @Value("${sts.file.upload.temp-expire-hours:24}")
//    private int tempExpireHours;

    /**
     * 存储异步上传任务状态（内存缓存）
     */
//    private final Map<String, AsyncTaskResult> asyncTaskCache = new ConcurrentHashMap<>();

    // ==================== 1. 普通上传 ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UploadResult upload(UploadFileCommand command) {
        // 1. 参数校验
        if (command.getFile() == null) {
            throw new BusinessException("文件不能为空");
        }
        if (command.getFileSize() <= 0) {
            throw new BusinessException("文件大小必须大于0");
        }

        if (command.getFileSize() > maxFileSize) {
            throw new BusinessException("文件大小超过限制: " + maxFileSize + " bytes");
        }

        // 业务规则：文件类型限制
//            if (!allowedFileTypes.contains(command.getFile().getFileExtension())) {
//                throw new BusinessException("不支持的文件类型");
//            }

        // 6. 生成文件ID
        String fileId = IdFactory.generate(IdGeneratorType.UUID);

        // 2. 选择存储类型
        StorageSelectionContext context = StorageSelectionContext.builder()
                .specifiedStorage(command.getStorageType())
                .fileSize(command.getFile().getFileSize())
                .fileExtension(command.getFile().getFileExtension())
                .preferredStorages(command.getPreferredStorages())
                .strategy(command.getStrategy())
                .tenantId(command.getTenantId())
                .userId(command.getUserId())
                .build();

        StorageTypeEnums storageType = storageSelector.selectStorage(context);
        command.setStorageType(storageType.getCode());

        // 4. 计算MD5（如果未提供）
        String fileMd5 = command.getFileMd5();
        if (!StringUtils.hasText(fileMd5)) {
            try {
                fileMd5 = FileMd5Util.calculateMd5(command.getFile().getInputStream());
                command.getFile().getInputStream().reset();
            } catch (Exception e) {
                log.warn("计算MD5失败: {}", e.getMessage());
            }
        }

        // 5. 检查是否启用去重
        if (command.getEnableDeduplication() != null && command.getEnableDeduplication()) {
            FileExistenceResult existCheck = checkFileExists(
                    FileExistenceQuery.builder()
                            .fileMd5(fileMd5)
                            .fileSize(command.getFileSize())
                            .storageType(command.getStorageType())
                            .tenantId(command.getTenantId())
                            .build()
            );
            if (Objects.nonNull(existCheck) && existCheck.getExists()) {
                // 秒传
                return fileUploadRuntimeConverter.toUploadResult(existCheck);
            }
        }

        // 3. 获取存储插件
        StoragePlugin plugin = pluginManager.getPlugin(StorageTypeEnums.fromCode(command.getStorageType()));
        if (plugin == null) {
            throw new BusinessException("存储类型不支持: " + command.getStorageType());
        }

        // 8. 上传文件到存储
        String filePath = plugin.upload(
                command.getFile().getInputStream(),
                command.getFileName(),
                command.getFileSize(),
                command.getTenantId(),
                ""
        );

        // 9. 构建文件URL
        String fileUrl = plugin.getFileUrl(filePath);

        // 10. 保存文件信息到数据库
        FileInfoEntity entity = fileUploadRuntimeConverter.toFileInfoEntity(
                command, fileId, filePath, fileUrl, fileMd5
        );
        fileInfoMapper.insert(entity);

        // 6. 构建结果
        return fileUploadRuntimeConverter.toUploadResult(entity);
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public List<UploadResult> uploadBatch(UploadBatchCommand command) {
//        List<UploadResult> results = new ArrayList<>();
//
//        for (FileObjectDto file : command.getFiles()) {
//            try {
//                UploadFileCommand singleCommand = UploadFileCommand.builder()
//                        .file(file)
//                        .storageType(command.getStorageType())
//                        .businessId(command.getBusinessId())
//                        .businessType(command.getBusinessType())
//                        .tags(command.getTags())
//                        .description(command.getDescription())
//                        .isPublic(command.getIsPublic())
//                        .tenantId(command.getTenantId())
//                        .userId(command.getUserId())
//                        .build();
//                results.add(upload(singleCommand));
//            } catch (Exception e) {
//                log.error("批量上传失败: {}", file.getFileName(), e);
//                // 继续上传其他文件
//                results.add(UploadResult.builder()
//                        .fileName(file.getFileName())
//                        .message("上传失败: " + e.getMessage())
//                        .uploadStatus(UploadStatusEnums.FAILED.getCode())
//                        .build());
//            }
//        }
//
//        return results;
//    }

    // ==================== 2. 分片上传 ====================

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public MultipartInitResult initMultipartUpload(InitMultipartCommand command) {
//        try {
//            // 1. 参数校验
//            validateInitMultipartCommand(command);
//
//            // 2. 检查文件大小
//            if (command.getFileSize() > maxFileSize) {
//                throw new BusinessException("文件大小超过限制: " + maxFileSize + " bytes");
//            }
//
//            // 3. 生成上传ID和文件ID
//            String uploadId = IdFactory.generate(IdGeneratorType.UUID);
//            String fileId = IdFactory.generate(IdGeneratorType.UUID);
//
//            // 4. 计算分片大小
//            long chunkSize = command.getChunkSize() != null ? command.getChunkSize() : defaultChunkSize;
//            int totalChunks = (int) Math.ceil((double) command.getFileSize() / chunkSize);
//
//            // 5. 创建上传会话
//            UploadSessionEntity session = new UploadSessionEntity();
//            session.setUploadId(uploadId);
//            session.setFileId(fileId);
//            session.setFileName(command.getFileName());
//            session.setFileSize(command.getFileSize());
//            session.setChunkSize(chunkSize);
//            session.setTotalChunks(totalChunks);
//            session.setCompletedChunks(0);
//            session.setStorageType(command.getStorageType());
//            session.setTenantId(command.getTenantId());
//            session.setCreatedUserId(command.getUserId());
//            session.setUploadStatus(UploadStatusEnums.UPLOADING.getCode());
//            session.setExpireTime(LocalDateTime.now().plusHours(24));
//            session.setCreatedTime(LocalDateTime.now());
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.insert(session);
//
//            // 6. 返回初始化响应
//            return MultipartInitResult.builder()
//                    .uploadId(uploadId)
//                    .fileId(fileId)
//                    .fileName(command.getFileName())
//                    .fileSize(command.getFileSize())
//                    .chunkSize(chunkSize)
//                    .totalChunks(totalChunks)
//                    .expireTime(session.getExpireTime().getTime())
//                    .storageType(command.getStorageType())
//                    .build();
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("初始化分片上传失败", e);
//            throw new BusinessException("初始化分片上传失败: " + e.getMessage());
//        }
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadPartResult uploadPart(UploadPartCommand command) {
//        try {
//            // 1. 验证上传会话是否存在
//            UploadSessionEntity session = uploadSessionMapper.selectByUploadId(command.getUploadId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + command.getUploadId());
//            }
//
//            // 2. 验证会话状态
//            if (!Objects.equals(session.getUploadStatus(), UploadStatusEnums.UPLOADING.getCode())) {
//                throw new BusinessException("上传会话已结束，状态: " + session.getUploadStatus());
//            }
//
//            // 3. 验证分片序号
//            if (command.getChunkNumber() > session.getTotalChunks()) {
//                throw new BusinessException("分片序号超过总分片数: " + session.getTotalChunks());
//            }
//
//            // 4. 检查分片是否已上传
//            FileChunkEntity existingChunk = fileChunkMapper.selectByUploadIdAndChunkNumber(
//                    command.getUploadId(), command.getChunkNumber());
//            if (existingChunk != null && existingChunk.getChunkStatus() == 2) {
//                // 分片已上传，直接返回成功
//                return UploadPartResult.builder()
//                        .uploadId(command.getUploadId())
//                        .fileId(command.getFileId())
//                        .chunkNumber(command.getChunkNumber())
//                        .totalChunks(session.getTotalChunks())
//                        .success(true)
//                        .uploadedChunks(session.getCompletedChunks())
//                        .progress(calculateProgress(session))
//                        .build();
//            }
//
//            // 5. 获取存储插件
//            StoragePlugin plugin = getStoragePlugin(session.getStorageType());
//
//            // 6. 上传分片到临时存储
//            String chunkPath = plugin.uploadChunk(
//                    command.getFile().getInputStream(),
//                    command.getUploadId(),
//                    command.getChunkNumber(),
//                    command.getFile().getSize()
//            );
//
//            // 7. 保存分片记录
//            FileChunkEntity chunk = new FileChunkEntity();
//            chunk.setUploadId(command.getUploadId());
//            chunk.setFileId(command.getFileId());
//            chunk.setChunkNumber(command.getChunkNumber());
//            chunk.setChunkSize(command.getFile().getSize());
//            chunk.setChunkMd5(command.getChunkMd5());
//            chunk.setChunkPath(chunkPath);
//            chunk.setStorageType(session.getStorageType());
//            chunk.setChunkStatus(2); // 已完成
//            chunk.setUploadEndTime(LocalDateTime.now());
//            chunk.setTenantId(session.getTenantId());
//            chunk.setCreatedUserId(session.getCreatedUserId());
//            chunk.setCreatedTime(LocalDateTime.now());
//            chunk.setUpdatedTime(LocalDateTime.now());
//            fileChunkMapper.insert(chunk);
//
//            // 8. 更新上传进度
//            int completedChunks = session.getCompletedChunks() + 1;
//            session.setCompletedChunks(completedChunks);
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.updateById(session);
//
//            // 9. 构建响应
//            return UploadPartResult.builder()
//                    .uploadId(command.getUploadId())
//                    .fileId(command.getFileId())
//                    .chunkNumber(command.getChunkNumber())
//                    .totalChunks(session.getTotalChunks())
//                    .success(true)
//                    .uploadedChunks(completedChunks)
//                    .progress(calculateProgress(session))
//                    .build();
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("上传分片失败", e);
//            throw new BusinessException("上传分片失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult completeMultipartUpload(CompleteMultipartCommand command) {
//        try {
//            // 1. 获取上传会话
//            UploadSessionEntity session = uploadSessionMapper.selectByUploadId(command.getUploadId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + command.getUploadId());
//            }
//
//            // 2. 验证所有分片是否已上传
//            if (session.getCompletedChunks() < session.getTotalChunks()) {
//                throw new BusinessException("还有分片未上传: " +
//                        (session.getTotalChunks() - session.getCompletedChunks()));
//            }
//
//            // 3. 获取存储插件
//            StoragePlugin plugin = getStoragePlugin(session.getStorageType());
//
//            // 4. 合并分片
//            String filePath = plugin.mergeChunks(command.getUploadId(), command.getFileId());
//            String fileUrl = plugin.getFileUrl(filePath);
//
//            // 5. 获取文件信息
//            FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                    command.getFileId(), session.getTenantId());
//
//            if (entity == null) {
//                // 创建文件记录
//                entity = new FileInfoEntity();
//                entity.setFileId(command.getFileId());
//                entity.setFileName(command.getFileName() != null ? command.getFileName() : session.getFileName());
//                entity.setFilePath(filePath);
//                entity.setFileSize(session.getFileSize());
//                entity.setStorageType(session.getStorageType());
//                entity.setStorageUrl(fileUrl);
//                entity.setTenantId(session.getTenantId());
//                entity.setCreatedUserId(session.getCreatedUserId());
//                entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
//                entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
//                entity.setUploadProgress(100);
//                entity.setUploadTime(LocalDateTime.now());
//                entity.setCreatedTime(LocalDateTime.now());
//                entity.setUpdatedTime(LocalDateTime.now());
//
//                // 设置标签和描述
//                if (StringUtils.hasText(command.getTags())) {
//                    entity.setTags(command.getTags());
//                }
//                if (StringUtils.hasText(command.getDescription())) {
//                    entity.setDescription(command.getDescription());
//                }
//                if (command.getIsPublic() != null) {
//                    entity.setIsPublic(command.getIsPublic() ? 1 : 0);
//                }
//
//                fileInfoMapper.insert(entity);
//            } else {
//                // 更新已存在的文件记录
//                entity.setFilePath(filePath);
//                entity.setStorageUrl(fileUrl);
//                entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
//                entity.setUploadProgress(100);
//                entity.setUploadEndTime(LocalDateTime.now());
//                entity.setUpdatedTime(LocalDateTime.now());
//                fileInfoMapper.updateById(entity);
//            }
//
//            // 6. 更新会话状态
//            session.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.updateById(session);
//
//            // 7. 构建响应
//            return buildUploadResponse(entity);
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("完成分片上传失败", e);
//            throw new BusinessException("完成分片上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void cancelMultipartUpload(CancelMultipartCommand command) {
//        try {
//            // 1. 获取上传会话
//            UploadSessionEntity session = uploadSessionMapper.selectByUploadId(command.getUploadId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + command.getUploadId());
//            }
//
//            // 2. 更新会话状态
//            session.setUploadStatus(UploadStatusEnums.CANCELLED.getCode());
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.updateById(session);
//
//            // 3. 获取存储插件，清理临时分片
//            StoragePlugin plugin = getStoragePlugin(session.getStorageType());
//            plugin.cleanupChunks(command.getUploadId());
//
//            // 4. 删除分片记录
//            fileChunkMapper.deleteByUploadId(command.getUploadId());
//
//            log.info("取消分片上传: {}", command.getUploadId());
//
//        } catch (Exception e) {
//            log.error("取消分片上传失败", e);
//            throw new BusinessException("取消分片上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public UploadProgressResult getMultipartProgress(UploadProgressQuery query) {
//        try {
//            UploadSessionEntity session = uploadSessionMapper.selectByUploadId(query.getUploadId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + query.getUploadId());
//            }
//
//            return UploadProgressResult.builder()
//                    .uploadId(session.getUploadId())
//                    .fileId(session.getFileId())
//                    .fileName(session.getFileName())
//                    .fileSize(session.getFileSize())
//                    .chunkSize(session.getChunkSize())
//                    .totalChunks(session.getTotalChunks())
//                    .uploadedChunks(session.getCompletedChunks())
//                    .progress(calculateProgress(session))
//                    .uploadedSize(session.getCompletedChunks() * session.getChunkSize())
//                    .status(getUploadStatusDesc(session.getUploadStatus()))
//                    .lastActiveTime(session.getUpdatedTime().getTime())
//                    .build();
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("获取分片上传进度失败", e);
//            throw new BusinessException("获取分片上传进度失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public PageResult<MultipartSessionResult> getMultipartSessions(MultipartSessionQuery query) {
//        try {
//            List<UploadSessionEntity> sessions = uploadSessionMapper.selectByTenantAndStatus(
//                    query.getTenantId(), query.getStatus());
//
//            long total = sessions.size();
//
//            // 分页处理
//            int start = (query.getPage() - 1) * query.getSize();
//            int end = Math.min(start + query.getSize(), sessions.size());
//            List<UploadSessionEntity> pageList = start < sessions.size() ?
//                    sessions.subList(start, end) : Collections.emptyList();
//
//            List<MultipartSessionResult> records = pageList.stream()
//                    .map(this::convertToMultipartSessionResult)
//                    .collect(Collectors.toList());
//
//            return PageResult.<MultipartSessionResult>builder()
//                    .total(total)
//                    .page(query.getPage())
//                    .size(query.getSize())
//                    .totalPages((int) Math.ceil((double) total / query.getSize()))
//                    .records(records)
//                    .build();
//
//        } catch (Exception e) {
//            log.error("获取分片上传会话列表失败", e);
//            throw new BusinessException("获取分片上传会话列表失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 3. 断点续传 ====================
//
//    @Override
//    public ResumeInfoResult getResumeInfo(ResumeInfoQuery query) {
//        try {
//            // 1. 查询文件信息
//            FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                    query.getFileId(), query.getTenantId());
//            if (entity == null) {
//                throw new BusinessException("文件不存在: " + query.getFileId());
//            }
//
//            // 2. 查询上传会话
//            UploadSessionEntity session = uploadSessionMapper.selectByFileId(query.getFileId());
//
//            // 3. 获取已上传分片列表
//            List<FileChunkEntity> chunks = fileChunkMapper.selectByUploadId(session.getUploadId());
//
//            List<UploadedChunkInfo> uploadedChunks = chunks.stream()
//                    .map(c -> UploadedChunkInfo.builder()
//                            .chunkNumber(c.getChunkNumber())
//                            .chunkMd5(c.getChunkMd5())
//                            .chunkSize(c.getChunkSize())
//                            .uploadTime(c.getUploadEndTime().getTime())
//                            .build())
//                    .collect(Collectors.toList());
//
//            // 4. 计算缺失分片
//            Set<Integer> uploadedSet = chunks.stream()
//                    .map(FileChunkEntity::getChunkNumber)
//                    .collect(Collectors.toSet());
//            List<Integer> missingChunks = new ArrayList<>();
//            for (int i = 1; i <= session.getTotalChunks(); i++) {
//                if (!uploadedSet.contains(i)) {
//                    missingChunks.add(i);
//                }
//            }
//
//            // 5. 构建响应
//            return ResumeInfoResult.builder()
//                    .fileId(query.getFileId())
//                    .fileName(entity.getFileName())
//                    .fileSize(entity.getFileSize())
//                    .uploadId(session.getUploadId())
//                    .totalChunks(session.getTotalChunks())
//                    .uploadedChunks(session.getCompletedChunks())
//                    .progress(calculateProgress(session))
//                    .uploadedChunks(uploadedChunks)
//                    .missingChunks(missingChunks)
//                    .canResume(!missingChunks.isEmpty())
//                    .status(getUploadStatusDesc(session.getUploadStatus()))
//                    .build();
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("获取断点续传信息失败", e);
//            throw new BusinessException("获取断点续传信息失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult restoreUpload(ResumeUploadCommand command) {
//        try {
//            // 1. 获取上传会话
//            UploadSessionEntity session = uploadSessionMapper.selectByUploadId(command.getUploadId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + command.getUploadId());
//            }
//
//            // 2. 检查会话状态
//            if (!Objects.equals(session.getUploadStatus(), UploadStatusEnums.COMPLETED.getCode())) {
//                // 已上传完成，直接返回文件信息
//                FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                        command.getFileId(), command.getTenantId());
//                return buildUploadResponse(entity);
//            }
//
//            // 3. 如果有缺失分片需要重传，返回需要重传的分片列表
//            if (command.getMissingChunks() != null && !command.getMissingChunks().isEmpty()) {
//                // 标记缺失分片为待上传
//                for (Integer chunkNumber : command.getMissingChunks()) {
//                    fileChunkMapper.updateStatusByUploadIdAndChunkNumber(
//                            command.getUploadId(), chunkNumber, 0); // 0-待上传
//                }
//            }
//
//            // 4. 更新会话状态为上传中
//            session.setUploadStatus(UploadStatusEnums.UPLOADING.getCode());
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.updateById(session);
//
//            // 5. 获取当前进度
//            int completedChunks = fileChunkMapper.countCompletedByUploadId(command.getUploadId());
//
//            return UploadResult.builder()
//                    .fileId(command.getFileId())
//                    .uploadStatus(UploadStatusEnums.UPLOADING.getCode())
//                    .message("恢复上传成功，已上传 " + completedChunks + "/" + session.getTotalChunks() + " 个分片")
//                    .build();
//
//        } catch (Exception e) {
//            log.error("恢复上传失败", e);
//            throw new BusinessException("恢复上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void pauseUpload(PauseUploadCommand command) {
//        try {
//            // 1. 获取上传会话
//            UploadSessionEntity session = uploadSessionMapper.selectByFileId(command.getFileId());
//            if (session == null) {
//                throw new BusinessException("上传会话不存在: " + command.getFileId());
//            }
//
//            // 2. 更新状态为暂停
//            session.setUploadStatus(UploadStatusEnums.PAUSED.getCode());
//            session.setUpdatedTime(LocalDateTime.now());
//            uploadSessionMapper.updateById(session);
//
//            log.info("暂停上传: {}", command.getFileId());
//
//        } catch (Exception e) {
//            log.error("暂停上传失败", e);
//            throw new BusinessException("暂停上传失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 4. 流式直传 ====================
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult streamUpload(StreamUploadCommand command) {
//        try {
//            // 1. 参数校验
//            if (!StringUtils.hasText(command.getFileName())) {
//                throw new BusinessException("文件名不能为空");
//            }
//
//            // 2. 生成文件ID
//            String fileId = IdFactory.generate(IdGeneratorType.UUID);
//
//            // 3. 获取存储插件
//            StoragePlugin plugin = getStoragePlugin(command.getStorageType());
//
//            // 4. 上传流
//            String filePath = plugin.upload(
//                    command.getInputStream(),
//                    command.getFileName(),
//                    command.getFileSize()
//            );
//            String fileUrl = plugin.getFileUrl(filePath);
//
//            // 5. 保存文件信息
//            FileInfoEntity entity = new FileInfoEntity();
//            entity.setFileId(fileId);
//            entity.setFileName(command.getFileName());
//            entity.setFilePath(filePath);
//            entity.setFileSize(command.getFileSize());
//            entity.setStorageType(command.getStorageType());
//            entity.setStorageUrl(fileUrl);
//            entity.setTenantId(command.getTenantId());
//            entity.setCreatedUserId(command.getUserId());
//            entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
//            entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
//            entity.setUploadProgress(100);
//            entity.setUploadTime(LocalDateTime.now());
//            entity.setCreatedTime(LocalDateTime.now());
//            entity.setUpdatedTime(LocalDateTime.now());
//
//            if (StringUtils.hasText(command.getTags())) {
//                entity.setTags(command.getTags());
//            }
//            if (StringUtils.hasText(command.getDescription())) {
//                entity.setDescription(command.getDescription());
//            }
//            if (command.getIsPublic() != null) {
//                entity.setIsPublic(command.getIsPublic() ? 1 : 0);
//            }
//
//            fileInfoMapper.insert(entity);
//
//            return buildUploadResponse(entity);
//
//        } catch (Exception e) {
//            log.error("流式上传失败", e);
//            throw new BusinessException("流式上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult chunkedStreamUpload(StreamUploadCommand command) {
//        // 分块流式上传与流式上传逻辑相同，只是标记为分块模式
//        return streamUpload(command);
//    }
//
    // ==================== 5. 秒传 ====================

    @Override
    public FileExistenceResult checkFileExists(FileExistenceQuery query) {
//        try {
//            // 构建指纹
//            String fingerprint = buildFingerprint(query);
//
//            // 查询去重记录
//            FileDeduplicationEntity dedup = fileDeduplicationMapper.selectByFingerprint(fingerprint);
//
//            FileExistenceResult result = FileExistenceResult.builder()
//                    .exists(dedup != null)
//                    .build();
//
//            if (dedup != null) {
//                FileInfoEntity file = fileInfoMapper.selectByFileIdAndTenant(
//                        dedup.getFileId(), query.getTenantId());
//                if (file != null) {
//                    result.setFileId(file.getFileId());
//                    result.setFileName(file.getFileName());
//                    result.setFileSize(file.getFileSize());
//                    result.setFileMd5(file.getFileMd5());
//                    result.setFileUrl(file.getStorageUrl());
//                    result.setStorageType(file.getStorageType());
//
//                    // 查找所有重复文件
//                    List<FileInfoEntity> duplicates = fileInfoMapper.selectByMd5AndTenant(
//                            query.getFileMd5(), query.getTenantId());
//                    result.setIsDuplicate(duplicates.size() > 1);
//                    result.setDuplicateFiles(duplicates.stream()
//                            .map(this::convertToFileInfoResult)
//                            .collect(Collectors.toList()));
//                }
//            }
//
//            return result;
//
//        } catch (Exception e) {
//            log.error("检查文件是否存在失败", e);
//            throw new BusinessException("检查文件是否存在失败: " + e.getMessage());
//        }
        return null;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult fastUpload(FastUploadCommand command) {
//        try {
//            // 1. 构建查询
//            FileExistenceQuery query = FileExistenceQuery.builder()
//                    .fileMd5(command.getFileMd5())
//                    .fileSize(command.getFileSize())
//                    .storageType(command.getStorageType())
//                    .tenantId(command.getTenantId())
//                    .build();
//
//            // 2. 检查文件是否存在
//            FileExistenceResult existCheck = checkFileExists(query);
//
//            if (!existCheck.getExists()) {
//                throw new BusinessException("文件不存在，无法秒传");
//            }
//
//            // 3. 创建新的文件记录（引用已存在的文件）
//            String fileId = IdFactory.generate(IdGeneratorType.UUID);
//            FileInfoEntity entity = new FileInfoEntity();
//            entity.setFileId(fileId);
//            entity.setFileName(command.getFileName());
//            entity.setFilePath(existCheck.getFilePath());
//            entity.setFileSize(command.getFileSize());
//            entity.setFileMd5(command.getFileMd5());
//            entity.setStorageType(command.getStorageType());
//            entity.setStorageUrl(existCheck.getFileUrl());
//            entity.setTenantId(command.getTenantId());
//            entity.setCreatedUserId(command.getUserId());
//            entity.setFileStatus(FileStatusEnums.NORMAL.getCode());
//            entity.setUploadStatus(UploadStatusEnums.COMPLETED.getCode());
//            entity.setUploadProgress(100);
//            entity.setUploadTime(LocalDateTime.now());
//            entity.setCreatedTime(LocalDateTime.now());
//            entity.setUpdatedTime(LocalDateTime.now());
//
//            if (StringUtils.hasText(command.getTags())) {
//                entity.setTags(command.getTags());
//            }
//            if (StringUtils.hasText(command.getDescription())) {
//                entity.setDescription(command.getDescription());
//            }
//            if (command.getIsPublic() != null) {
//                entity.setIsPublic(command.getIsPublic() ? 1 : 0);
//            }
//
//            fileInfoMapper.insert(entity);
//
//            // 4. 更新去重引用计数
//            fileDeduplicationMapper.incrementReferenceCount(existCheck.getFileId());
//
//            // 5. 构建响应
//            UploadResult result = buildUploadResponse(entity);
//            result.setIsDuplicate(true);
//            result.setDuplicateFileId(existCheck.getFileId());
//            result.setMessage("秒传成功");
//
//            return result;
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("秒传失败", e);
//            throw new BusinessException("秒传失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 6. 异步上传 ====================
//
//    @Override
//    public AsyncUploadResult asyncUpload(AsyncUploadCommand command) {
//        try {
//            // 1. 生成任务ID
//            String taskId = IdFactory.generate(IdGeneratorType.UUID);
//
//            // 2. 创建异步任务记录
//            UploadTaskEntity task = new UploadTaskEntity();
//            task.setTaskId(taskId);
//            task.setFileName(command.getFileName());
//            task.setFileSize(command.getFileSize());
//            task.setFileMd5(command.getFileMd5());
//            task.setStorageType(parseStorageType(command.getStorageType()));
//            task.setTaskStatus(0); // 排队
//            task.setTaskType(1); // 上传
//            task.setTaskProgress(0);
//            task.setTenantId(command.getTenantId());
//            task.setCreatedUserId(command.getUserId());
//            task.setPriority(command.getPriority() != null ? command.getPriority() : 5);
//            task.setTimeoutSeconds(command.getTimeoutSeconds() != null ? command.getTimeoutSeconds() : 300);
//            task.setCallbackUrl(command.getCallbackUrl());
//            task.setCreatedTime(LocalDateTime.now());
//            task.setUpdatedTime(LocalDateTime.now());
//            uploadTaskMapper.insert(task);
//
//            // 3. 异步执行上传
//            asyncExecuteUpload(taskId, command);
//
//            // 4. 返回响应
//            return AsyncUploadResult.builder()
//                    .taskId(taskId)
//                    .fileName(command.getFileName())
//                    .fileSize(command.getFileSize())
//                    .status("QUEUED")
//                    .progress(0)
//                    .message("任务已提交，正在排队处理")
//                    .statusUrl("/files/async/task/" + taskId)
//                    .build();
//
//        } catch (Exception e) {
//            log.error("提交异步上传任务失败", e);
//            throw new BusinessException("提交异步上传任务失败: " + e.getMessage());
//        }
//    }
//
//    @Async("asyncTaskExecutor")
//    @Transactional(rollbackFor = Exception.class)
//    public void asyncExecuteUpload(String taskId, AsyncUploadCommand command) {
//        try {
//            // 1. 更新任务状态为处理中
//            updateTaskStatus(taskId, 1, 0); // 处理中
//
//            // 2. 获取文件内容
//            byte[] fileData;
//            if (StringUtils.hasText(command.getFileContentBase64())) {
//                // 从Base64解码
//                fileData = Base64.getDecoder().decode(command.getFileContentBase64());
//            } else if (StringUtils.hasText(command.getFileUrl())) {
//                // 从URL下载
//                fileData = downloadFromUrl(command.getFileUrl());
//            } else {
//                throw new BusinessException("文件内容或URL不能为空");
//            }
//
//            // 3. 构建上传命令
//            FileObjectDto fileObject = FileObjectDto.builder()
//                    .fileName(command.getFileName())
//                    .fileSize((long) fileData.length)
//                    .inputStream(new ByteArrayInputStream(fileData))
//                    .build();
//
//            UploadFileCommand uploadCommand = UploadFileCommand.builder()
//                    .file(fileObject)
//                    .fileName(command.getFileName())
//                    .fileSize((long) fileData.length)
//                    .fileMd5(command.getFileMd5())
//                    .storageType(command.getStorageType())
//                    .businessId(command.getBusinessId())
//                    .businessType(command.getBusinessType())
//                    .tags(command.getTags())
//                    .description(command.getDescription())
//                    .isPublic(command.getIsPublic())
//                    .metadata(command.getMetadata())
//                    .tenantId(command.getTenantId())
//                    .userId(command.getUserId())
//                    .build();
//
//            // 4. 执行上传
//            UploadResult result = upload(uploadCommand);
//
//            // 5. 更新任务状态为完成
//            updateTaskStatus(taskId, 2, 100); // 已完成
//            uploadTaskMapper.updateResult(taskId, result);
//
//            // 6. 发送回调通知
//            if (StringUtils.hasText(command.getCallbackUrl())) {
//                sendCallback(command.getCallbackUrl(), taskId, result);
//            }
//
//            log.info("异步上传任务完成: {}", taskId);
//
//        } catch (Exception e) {
//            log.error("异步上传任务失败: {}", taskId, e);
//            updateTaskStatus(taskId, 3, 0); // 失败
//            uploadTaskMapper.updateError(taskId, e.getMessage());
//        }
//    }
//
//    @Override
//    public AsyncTaskResult getAsyncTaskStatus(AsyncTaskQuery query) {
//        try {
//            // 先从缓存获取
//            AsyncTaskResult cached = asyncTaskCache.get(query.getTaskId());
//            if (cached != null) {
//                return cached;
//            }
//
//            // 从数据库查询
//            UploadTaskEntity task = uploadTaskMapper.selectByTaskId(query.getTaskId());
//            if (task == null) {
//                throw new BusinessException("任务不存在: " + query.getTaskId());
//            }
//
//            return convertToAsyncTaskResult(task);
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("获取异步任务状态失败", e);
//            throw new BusinessException("获取异步任务状态失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void cancelAsyncTask(CancelAsyncTaskCommand command) {
//        try {
//            UploadTaskEntity task = uploadTaskMapper.selectByTaskId(command.getTaskId());
//            if (task == null) {
//                throw new BusinessException("任务不存在: " + command.getTaskId());
//            }
//
//            if (task.getTaskStatus() >= 2) {
//                throw new BusinessException("任务已完成或已失败，无法取消");
//            }
//
//            task.setTaskStatus(4); // 已取消
//            task.setUpdatedTime(LocalDateTime.now());
//            uploadTaskMapper.updateById(task);
//
//            asyncTaskCache.remove(command.getTaskId());
//
//            log.info("取消异步任务: {}", command.getTaskId());
//
//        } catch (Exception e) {
//            log.error("取消异步任务失败", e);
//            throw new BusinessException("取消异步任务失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public PageResult<AsyncTaskResult> getAsyncTasks(AsyncTaskListQuery query) {
//        try {
//            List<UploadTaskEntity> tasks = uploadTaskMapper.selectByTenantAndStatus(
//                    query.getTenantId(), query.getTaskStatus());
//
//            long total = tasks.size();
//
//            int start = (query.getPage() - 1) * query.getSize();
//            int end = Math.min(start + query.getSize(), tasks.size());
//            List<UploadTaskEntity> pageList = start < tasks.size() ?
//                    tasks.subList(start, end) : Collections.emptyList();
//
//            List<AsyncTaskResult> records = pageList.stream()
//                    .map(this::convertToAsyncTaskResult)
//                    .collect(Collectors.toList());
//
//            return PageResult.<AsyncTaskResult>builder()
//                    .total(total)
//                    .page(query.getPage())
//                    .size(query.getSize())
//                    .totalPages((int) Math.ceil((double) total / query.getSize()))
//                    .records(records)
//                    .build();
//
//        } catch (Exception e) {
//            log.error("获取异步任务列表失败", e);
//            throw new BusinessException("获取异步任务列表失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public void handleUploadCallback(UploadCallbackCommand command) {
//        // 处理上传回调 - 用于异步上传完成后的通知
//        log.info("处理上传回调: taskId={}, status={}", command.getTaskId(), command.getStatus());
//        // 实际实现中，可以更新任务状态、发送通知等
//    }
//
//    // ==================== 7. 上传状态管理 ====================
//
//    @Override
//    public UploadStatusResult getUploadStatus(UploadStatusQuery query) {
//        try {
//            FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
//                    query.getFileId(), query.getTenantId());
//            if (entity == null) {
//                throw new BusinessException("文件不存在: " + query.getFileId());
//            }
//
//            // 如果是分片上传，获取会话信息
//            UploadSessionEntity session = uploadSessionMapper.selectByFileId(query.getFileId());
//
//            return UploadStatusResult.builder()
//                    .fileId(entity.getFileId())
//                    .fileName(entity.getFileName())
//                    .fileSize(entity.getFileSize())
//                    .fileMd5(entity.getFileMd5())
//                    .storageType(entity.getStorageType())
//                    .uploadStatus(entity.getUploadStatus())
//                    .uploadStatusDesc(getUploadStatusDesc(entity.getUploadStatus()))
//                    .progress(entity.getUploadProgress() != null ? entity.getUploadProgress() : 0)
//                    .uploadId(session != null ? session.getUploadId() : null)
//                    .totalChunks(session != null ? session.getTotalChunks() : null)
//                    .uploadedChunks(session != null ? session.getCompletedChunks() : null)
//                    .uploadStartTime(entity.getUploadTime() != null ? entity.getUploadTime().getTime() : null)
//                    .uploadEndTime(entity.getUploadEndTime() != null ? entity.getUploadEndTime().getTime() : null)
//                    .build();
//
//        } catch (BusinessException e) {
//            throw e;
//        } catch (Exception e) {
//            log.error("获取上传状态失败", e);
//            throw new BusinessException("获取上传状态失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public PageResult<UploadFileResult> getUserFiles(UploadFileListQuery query) {
//        try {
//            return fileInfoMapper.selectUserFiles(query);
//        } catch (Exception e) {
//            log.error("获取用户文件列表失败", e);
//            throw new BusinessException("获取用户文件列表失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public UploadStatisticsResult getUploadStatistics(UploadStatisticsQuery query) {
//        try {
//            // 从Mapper获取统计信息
//            return fileInfoMapper.getUploadStatistics(query);
//        } catch (Exception e) {
//            log.error("获取上传统计失败", e);
//            throw new BusinessException("获取上传统计失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 8. 大文件上传 ====================
//
//    @Override
//    public UploadResult autoUpload(AutoUploadCommand command) {
//        try {
//            // 获取文件大小
//            long fileSize = command.getFile().getFileSize();
//            long threshold = command.getChunkThreshold() != null ?
//                    command.getChunkThreshold() : defaultChunkSize;
//
//            // 根据文件大小选择上传策略
//            if (fileSize <= threshold) {
//                // 小文件：普通上传
//                UploadFileCommand uploadCommand = UploadFileCommand.builder()
//                        .file(command.getFile())
//                        .storageType(command.getStorageType())
//                        .businessId(command.getBusinessId())
//                        .businessType(command.getBusinessType())
//                        .tags(command.getTags())
//                        .description(command.getDescription())
//                        .isPublic(command.getIsPublic())
//                        .tenantId(command.getTenantId())
//                        .userId(command.getUserId())
//                        .build();
//                return upload(uploadCommand);
//            } else {
//                // 大文件：分片上传
//                // 1. 初始化分片上传
//                InitMultipartCommand initCommand = InitMultipartCommand.builder()
//                        .fileName(command.getFile().getFileName())
//                        .fileSize(fileSize)
//                        .chunkSize(threshold)
//                        .storageType(command.getStorageType())
//                        .businessId(command.getBusinessId())
//                        .businessType(command.getBusinessType())
//                        .tags(command.getTags())
//                        .description(command.getDescription())
//                        .isPublic(command.getIsPublic())
//                        .tenantId(command.getTenantId())
//                        .userId(command.getUserId())
//                        .build();
//
//                MultipartInitResult initResult = initMultipartUpload(initCommand);
//
//                // 2. 分片上传
//                // 实际实现中需要循环上传分片
//                // 这里简化为返回初始化结果
//                return UploadResult.builder()
//                        .fileId(initResult.getFileId())
//                        .fileName(initResult.getFileName())
//                        .fileSize(initResult.getFileSize())
//                        .storageType(initResult.getStorageType())
//                        .uploadStatus(UploadStatusEnums.UPLOADING.getCode())
//                        .message("大文件分片上传已初始化，请继续上传分片")
//                        .build();
//            }
//
//        } catch (Exception e) {
//            log.error("自动上传失败", e);
//            throw new BusinessException("自动上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult resumableUpload(ResumableUploadCommand command) {
//        try {
//            // 可恢复上传 - 支持服务端自动恢复
//            String fileId = command.getFileId();
//            FileObjectDto file = command.getFile();
//
//            if (StringUtils.hasText(fileId)) {
//                // 断点续传
//                ResumeInfoQuery query = ResumeInfoQuery.builder()
//                        .fileId(fileId)
//                        .tenantId(command.getTenantId())
//                        .build();
//                ResumeInfoResult resumeInfo = getResumeInfo(query);
//
//                if (resumeInfo.getCanResume()) {
//                    // 恢复上传
//                    ResumeUploadCommand resumeCommand = ResumeUploadCommand.builder()
//                            .fileId(fileId)
//                            .uploadId(resumeInfo.getUploadId())
//                            .missingChunks(resumeInfo.getMissingChunks())
//                            .tenantId(command.getTenantId())
//                            .userId(command.getUserId())
//                            .build();
//                    return restoreUpload(resumeCommand);
//                }
//            }
//
//            // 新文件上传
//            UploadFileCommand uploadCommand = UploadFileCommand.builder()
//                    .file(file)
//                    .storageType(command.getStorageType())
//                    .businessId(command.getBusinessId())
//                    .businessType(command.getBusinessType())
//                    .tags(command.getTags())
//                    .description(command.getDescription())
//                    .tenantId(command.getTenantId())
//                    .userId(command.getUserId())
//                    .build();
//            return upload(uploadCommand);
//
//        } catch (Exception e) {
//            log.error("可恢复上传失败", e);
//            throw new BusinessException("可恢复上传失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 9. Base64上传 ====================
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult base64Upload(Base64UploadCommand command) {
//        try {
//            // 1. 解码Base64
//            byte[] content = Base64.getDecoder().decode(command.getBase64Content());
//
//            // 2. 构建文件对象
//            FileObjectDto fileObject = FileObjectDto.builder()
//                    .fileName(command.getFileName())
//                    .fileSize((long) content.length)
//                    .inputStream(new ByteArrayInputStream(content))
//                    .build();
//
//            // 3. 执行上传
//            UploadFileCommand uploadCommand = UploadFileCommand.builder()
//                    .file(fileObject)
//                    .fileName(command.getFileName())
//                    .fileSize((long) content.length)
//                    .storageType(command.getStorageType())
//                    .businessId(command.getBusinessId())
//                    .businessType(command.getBusinessType())
//                    .tags(command.getTags())
//                    .description(command.getDescription())
//                    .isPublic(command.getIsPublic())
//                    .metadata(command.getMetadata())
//                    .tenantId(command.getTenantId())
//                    .userId(command.getUserId())
//                    .build();
//
//            return upload(uploadCommand);
//
//        } catch (Exception e) {
//            log.error("Base64上传失败", e);
//            throw new BusinessException("Base64上传失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 10. URL拉取上传 ====================
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public UploadResult fetchFromUrl(UrlFetchCommand command) {
//        try {
//            // 1. 从URL下载文件
//            byte[] content = downloadFromUrl(command.getFileUrl());
//
//            // 2. 确定文件名
//            String fileName = command.getFileName();
//            if (!StringUtils.hasText(fileName)) {
//                fileName = extractFileNameFromUrl(command.getFileUrl());
//            }
//
//            // 3. 构建文件对象
//            FileObjectDto fileObject = FileObjectDto.builder()
//                    .fileName(fileName)
//                    .fileSize((long) content.length)
//                    .inputStream(new ByteArrayInputStream(content))
//                    .build();
//
//            // 4. 执行上传
//            UploadFileCommand uploadCommand = UploadFileCommand.builder()
//                    .file(fileObject)
//                    .fileName(fileName)
//                    .fileSize((long) content.length)
//                    .storageType(command.getStorageType())
//                    .businessId(command.getBusinessId())
//                    .businessType(command.getBusinessType())
//                    .tags(command.getTags())
//                    .description(command.getDescription())
//                    .isPublic(command.getIsPublic())
//                    .metadata(command.getMetadata())
//                    .tenantId(command.getTenantId())
//                    .userId(command.getUserId())
//                    .build();
//
//            return upload(uploadCommand);
//
//        } catch (Exception e) {
//            log.error("URL拉取上传失败", e);
//            throw new BusinessException("URL拉取上传失败: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public AsyncUploadResult asyncFetchFromUrl(UrlFetchCommand command) {
//        // 异步URL拉取
//        AsyncUploadCommand asyncCommand = AsyncUploadCommand.builder()
//                .fileName(command.getFileName())
//                .fileSize(null) // 未知
//                .fileUrl(command.getFileUrl())
//                .storageType(command.getStorageType())
//                .businessId(command.getBusinessId())
//                .businessType(command.getBusinessType())
//                .tags(command.getTags())
//                .description(command.getDescription())
//                .isPublic(command.getIsPublic())
//                .metadata(command.getMetadata())
//                .tenantId(command.getTenantId())
//                .userId(command.getUserId())
//                .build();
//
//        return asyncUpload(asyncCommand);
//    }
//
//    // ==================== 11. 混合云上传 ====================
//
//    @Override
//    public HybridUploadResult hybridUpload(HybridUploadCommand command) {
//        try {
//            // 1. 如果指定了存储类型，直接使用
//            if (StringUtils.hasText(command.getStorageType())) {
//                UploadResult result = upload(UploadFileCommand.builder()
//                        .file(command.getFile())
//                        .storageType(command.getStorageType())
//                        .businessId(command.getBusinessId())
//                        .businessType(command.getBusinessType())
//                        .tags(command.getTags())
//                        .description(command.getDescription())
//                        .tenantId(command.getTenantId())
//                        .userId(command.getUserId())
//                        .build());
//
//                return HybridUploadResult.builder()
//                        .fileId(result.getFileId())
//                        .fileName(result.getFileName())
//                        .fileSize(result.getFileSize())
//                        .fileMd5(result.getFileMd5())
//                        .fileUrl(result.getFileUrl())
//                        .storageType(result.getStorageType())
//                        .tenantId(result.getTenantId())
//                        .uploadStatus(result.getUploadStatus())
//                        .selectedStorage(command.getStorageType())
//                        .strategy("specified")
//                        .selectionReason("用户指定存储类型")
//                        .build();
//            }
//
//            // 2. 选择最优存储
//            StoragePlugin selectedPlugin = pluginManager.selectOptimalPlugin(
//                    command.getFile().getFileSize(),
//                    command.getFile().getFileExtension(),
//                    command.getPreferredStorages(),
//                    command.getStrategy()
//            );
//
//            if (selectedPlugin == null) {
//                throw new BusinessException("没有可用的存储插件");
//            }
//
//            // 3. 执行上传
//            UploadResult result = upload(UploadFileCommand.builder()
//                    .file(command.getFile())
//                    .storageType(selectedPlugin.getStorageType())
//                    .businessId(command.getBusinessId())
//                    .businessType(command.getBusinessType())
//                    .tags(command.getTags())
//                    .description(command.getDescription())
//                    .tenantId(command.getTenantId())
//                    .userId(command.getUserId())
//                    .build());
//
//            // 4. 构建混合云响应
//            return HybridUploadResult.builder()
//                    .fileId(result.getFileId())
//                    .fileName(result.getFileName())
//                    .fileSize(result.getFileSize())
//                    .fileMd5(result.getFileMd5())
//                    .fileUrl(result.getFileUrl())
//                    .storageType(result.getStorageType())
//                    .tenantId(result.getTenantId())
//                    .uploadStatus(result.getUploadStatus())
//                    .selectedStorage(selectedPlugin.getStorageType())
//                    .strategy(command.getStrategy() != null ? command.getStrategy() : "auto")
//                    .selectionReason("根据策略自动选择")
//                    .candidates(pluginManager.getCandidateInfo(command.getFile()))
//                    .build();
//
//        } catch (Exception e) {
//            log.error("混合云上传失败", e);
//            throw new BusinessException("混合云上传失败: " + e.getMessage());
//        }
//    }
//
//    // ==================== 私有辅助方法 ====================
//
//    /**
//     * 验证上传命令
//     */
//    private void validateUploadCommand(UploadFileCommand command) {
//        if (command.getFile() == null) {
//            throw new BusinessException("文件不能为空");
//        }
//        if (command.getFile().getFileSize() <= 0) {
//            throw new BusinessException("文件大小必须大于0");
//        }
//    }
//
//    /**
//     * 验证分片初始化命令
//     */
//    private void validateInitMultipartCommand(InitMultipartCommand command) {
//        if (!StringUtils.hasText(command.getFileName())) {
//            throw new BusinessException("文件名不能为空");
//        }
//        if (command.getFileSize() == null || command.getFileSize() <= 0) {
//            throw new BusinessException("文件大小必须大于0");
//        }
//    }
//
//    /**
//     * 构建文件信息实体
//     */
//    private FileInfoEntity buildFileInfoEntity(UploadFileCommand command) {
//
//    }
//
//    /**
//     * 构建指纹
//     */
//    private String buildFingerprint(FileExistenceQuery query) {
//        return query.getFileMd5() + "_" + query.getFileSize() + "_" +
//                (query.getStorageType() != null ? query.getStorageType() : "default") +
//                "_" + query.getTenantId();
//    }
//
//    /**
//     * 计算进度
//     */
//    private int calculateProgress(UploadSessionEntity session) {
//        if (session.getTotalChunks() == 0) return 0;
//        return (int) ((double) session.getCompletedChunks() / session.getTotalChunks() * 100);
//    }
//
//    /**
//     * 转换存储类型
//     */
//    private Integer parseStorageType(String storageType) {
//        if (!StringUtils.hasText(storageType)) return 0;
//        return switch (storageType.toLowerCase()) {
//            case "local" -> 0;
//            case "minio" -> 1;
//            case "oss" -> 2;
//            case "rustfs" -> 3;
//            default -> 0;
//        };
//    }
//
//    /**
//     * 从URL下载文件
//     */
//    private byte[] downloadFromUrl(String urlString) throws IOException {
//        URL url = new URL(urlString);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setConnectTimeout(30000);
//        connection.setReadTimeout(60000);
//
//        try (InputStream inputStream = connection.getInputStream()) {
//            return inputStream.readAllBytes();
//        }
//    }
//
//    /**
//     * 从URL提取文件名
//     */
//    private String extractFileNameFromUrl(String urlString) {
//        String fileName = urlString.substring(urlString.lastIndexOf("/") + 1);
//        if (fileName.contains("?")) {
//            fileName = fileName.substring(0, fileName.indexOf("?"));
//        }
//        return fileName;
//    }
//
//    /**
//     * 更新任务状态
//     */
//    private void updateTaskStatus(String taskId, int status, int progress) {
//        UploadTaskEntity task = uploadTaskMapper.selectByTaskId(taskId);
//        if (task != null) {
//            task.setTaskStatus(status);
//            task.setTaskProgress(progress);
//            task.setUpdatedTime(LocalDateTime.now());
//            uploadTaskMapper.updateById(task);
//        }
//    }
//
//    /**
//     * 发送回调通知
//     */
//    private void sendCallback(String callbackUrl, String taskId, UploadResult result) {
//        // 实现回调通知逻辑
//        log.info("发送回调通知: {}, taskId={}", callbackUrl, taskId);
//    }
//
//    /**
//     * 转换为文件信息结果
//     */
//    private FileInfoResult convertToFileInfoResult(FileInfoEntity entity) {
//        return FileInfoResult.builder()
//                .fileId(entity.getFileId())
//                .fileName(entity.getFileName())
//                .fileSize(entity.getFileSize())
//                .fileMd5(entity.getFileMd5())
//                .fileType(entity.getFileType())
//                .storageType(entity.getStorageType())
//                .storageUrl(entity.getStorageUrl())
//                .fileStatus(entity.getFileStatus())
//                .downloadCount(entity.getDownloadCount())
//                .tags(parseTags(entity.getTags()))
//                .description(entity.getDescription())
//                .createdUserId(entity.getCreatedUserId())
//                .createdUserName(entity.getCreatedUserName())
//                .createdTime(entity.getCreatedTime())
//                .build();
//    }
//
//    /**
//     * 解析标签
//     */
//    private List<String> parseTags(String tags) {
//        if (!StringUtils.hasText(tags)) return Collections.emptyList();
//        return Arrays.asList(tags.split(","));
//    }
//
//    /**
//     * 转换为分片会话结果
//     */
//    private MultipartSessionResult convertToMultipartSessionResult(UploadSessionEntity session) {
//        return MultipartSessionResult.builder()
//                .uploadId(session.getUploadId())
//                .fileId(session.getFileId())
//                .fileName(session.getFileName())
//                .fileSize(session.getFileSize())
//                .chunkSize(session.getChunkSize())
//                .totalChunks(session.getTotalChunks())
//                .uploadedChunks(session.getCompletedChunks())
//                .progress(calculateProgress(session))
//                .status(getUploadStatusDesc(session.getUploadStatus()))
//                .expireTime(session.getExpireTime().getTime())
//                .lastActiveTime(session.getUpdatedTime().getTime())
//                .createdTime(session.getCreatedTime())
//                .build();
//    }
//
//    /**
//     * 转换为异步任务结果
//     */
//    private AsyncTaskResult convertToAsyncTaskResult(UploadTaskEntity task) {
//        return AsyncTaskResult.builder()
//                .taskId(task.getTaskId())
//                .fileId(task.getFileId())
//                .fileName(task.getFileName())
//                .fileSize(task.getFileSize())
//                .fileMd5(task.getFileMd5())
//                .status(getTaskStatusDesc(task.getTaskStatus()))
//                .statusDesc(getTaskStatusDesc(task.getTaskStatus()))
//                .progress(task.getTaskProgress())
//                .errorMessage(task.getErrorMessage())
//                .retryCount(task.getRetryCount())
//                .startTime(task.getStartTime() != null ? task.getStartTime().getTime() : null)
//                .endTime(task.getEndTime() != null ? task.getEndTime().getTime() : null)
//                .createdTime(task.getCreatedTime())
//                .callbackUrl(task.getCallbackUrl())
//                .build();
//    }
//
//    /**
//     * 获取任务状态描述
//     */
//    private String getTaskStatusDesc(Integer status) {
//        if (status == null) return "未知";
//        return switch (status) {
//            case 0 -> "排队";
//            case 1 -> "处理中";
//            case 2 -> "已完成";
//            case 3 -> "失败";
//            case 4 -> "已取消";
//            case 5 -> "超时";
//            default -> "未知";
//        };
//    }
}