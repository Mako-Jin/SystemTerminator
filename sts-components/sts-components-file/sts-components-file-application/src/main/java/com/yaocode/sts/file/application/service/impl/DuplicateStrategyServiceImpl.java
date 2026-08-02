package com.yaocode.sts.file.application.service.impl;

import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.file.application.model.command.CreateVersionCommand;
import com.yaocode.sts.file.application.service.FileVersionService;
import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.UploadStatusEnums;
import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.core.exception.FilePermissionException;
import com.yaocode.sts.file.core.exception.FileStorageException;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.application.service.DuplicateStrategyService;
import com.yaocode.sts.file.core.spi.DuplicateStrategySelector;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import com.yaocode.sts.file.core.utils.FileFingerprintUtils;
import com.yaocode.sts.file.core.utils.FileNameUtils;
import com.yaocode.sts.file.infrastructure.dao.FileDeduplicationDao;
import com.yaocode.sts.file.infrastructure.manager.StoragePluginManager;
import com.yaocode.sts.file.infrastructure.mapper.FileBaseInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Component
public class DuplicateStrategyServiceImpl implements DuplicateStrategyService {

    @Resource
    private DuplicateStrategySelector strategySelector;

    @Resource
    private FileBaseInfoMapper fileInfoMapper;

    @Resource
    private FileDeduplicationDao fileDeduplicationDao;

    @Resource
    private StoragePluginManager pluginManager;

    @Resource
    private FileVersionService fileVersionService;

    /**
     * 自注入代理对象，用于解决 @Transactional 自调用失效问题。
     * <p>
     * Spring AOP 通过代理拦截事务方法，同类内部的 this 调用不会经过代理，
     * 导致 @Transactional 注解无效。通过自注入代理对象即可绕过此限制。
     * </p>
     *
     * <p>使用 @Lazy 避免循环依赖初始化时的提前暴露问题。</p>
     */
    @Lazy
    @Resource
    private DuplicateStrategyServiceImpl self;

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256
    ) {
        return execute(context, existFile, tempFile, fileMd5, fileSha256, null);
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256,
            StrategyCallback callback
    ) {
        if (Objects.isNull(existFile) || !existFile.getExists()) {
            log.debug("文件不存在，无需执行策略");
            return null;
        }
        DuplicateFileStrategyEnums strategy = strategySelector.selectStrategy(context, existFile);

        if (strategy == null) {
            log.debug("无需处理重复文件，走正常上传流程");
            return null;
        }

        if (callback != null) {
            callback.onStrategySelected(strategy);
        }

        log.info("执行重复文件策略: strategy={}, fileId={}, fileName={}",
                strategy, existFile.getFileId(), context.getFileName());

        try {
            ExecuteResult result = doExecute(strategy, context, existFile, tempFile, fileMd5, fileSha256);
            if (callback != null) {
                callback.onStrategyExecuted(strategy, result);
            }
            return result;
        } catch (Exception e) {
            log.error("策略执行失败: {}", strategy, e);
            if (callback != null) {
                callback.onError(strategy, e);
            }
            throw e;
        }
    }

    private ExecuteResult doExecute(
            DuplicateFileStrategyEnums strategy,
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256
    ) {
        return switch (strategy) {
            case REUSE -> handleReuse(existFile, tempFile);
            case NEW_VERSION -> handleNewVersion(context, existFile, tempFile, fileMd5, fileSha256);
            case OVERWRITE -> handleOverwrite(context, existFile, tempFile, fileMd5, fileSha256);
            case AUTO_RENAME -> handleAutoRename(context, tempFile);
            default -> throw new FileException(FileErrorCodeEnums.FILE_ALREADY_EXISTS, existFile.getFileId(), existFile.getFileName());
        };
    }

    private ExecuteResult handleReuse(FileExistenceContext existFile, Path tempFile) {
        cleanupTempFile(tempFile);
        return ExecuteResult.builder()
                .fileId(existFile.getFileId())
                .fileName(existFile.getFileName())
                .fileSize(existFile.getFileSize())
                .fileMd5(existFile.getFileMd5())
                .fileSha256(existFile.getFileSha256())
                .fileUrl(existFile.getFileUrl())
                .storageType(existFile.getStorageType())
                .tenantId(existFile.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .isDuplicate(true)
                .sourceFileId(existFile.getFileId())
                .versionNumber(existFile.getVersionNumber())
                .message("秒传成功（已复用已有文件）")
                .build();
    }

    private ExecuteResult handleNewVersion(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256
    ) {
        if (!Objects.equals(context.getTenantId(), existFile.getTenantId())) {
            throw new FilePermissionException(FileErrorCodeEnums.PERMISSION_DENIED);
        }

        int currentVersion = fileInfoMapper.getMaxVersionByFileId(
                existFile.getFileId(), context.getTenantId());
        int newVersion = currentVersion + 1;

        StorageTypeEnums storageType = StorageTypeEnums.fromCode(context.getStorageType());
        StoragePlugin plugin = pluginManager.getPlugin(storageType);
        if (plugin == null) {
            throw new FileStorageException("STORAGE_TYPE_NOT_SUPPORTED",
                    "storage.type.not.supported", String.valueOf(context.getStorageType()));
        }

        String filePath;
        try (InputStream is = Files.newInputStream(tempFile)) {
            String bucket = context.getBucket() != null ? context.getBucket() : "default";
            filePath = plugin.upload(is, context.getFileName(), context.getFileSize(),
                    context.getTenantId(), bucket);
        } catch (IOException e) {
            throw new FileStorageException("STORAGE_UPLOAD_FAILED", e, context.getStorageType());
        }
        String fileUrl = plugin.getFileUrl(filePath);

        // Step2: 持久化（短事务）
        try {
            self.persistNewVersion(context, existFile, fileMd5, fileSha256, tempFile);
        } catch (Exception e) {
            cleanupTempFile(tempFile);
            plugin.delete(filePath);
            throw e;
        }

        cleanupTempFile(tempFile);

        return ExecuteResult.builder()
                .fileId(existFile.getFileId())
                .fileName(context.getFileName())
                .fileSize(context.getFileSize())
                .fileMd5(fileMd5)
                .fileSha256(fileSha256)
                .fileUrl(fileUrl)
                .storageType(context.getStorageType())
                .tenantId(context.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .isDuplicate(true)
                .sourceFileId(existFile.getFileId())
                .versionNumber(newVersion)
                .message("已创建新版本 v" + newVersion)
                .build();
    }

    /**
     * 新建版本的持久化操作（短事务）
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void persistNewVersion(
            FileUploadContext context,
            FileExistenceContext existFile,
            String fileMd5,
            String fileSha256,
            Path tempFile
    ) {
        CreateVersionCommand versionCommand = CreateVersionCommand.builder()
                .fileId(existFile.getFileId())
                .fileName(context.getFileName())
                .fileContent(getInputStreamSafely(tempFile))
                .fileSize(context.getFileSize())
                .fileMd5(fileMd5)
                .fileSha256(fileSha256)
                .versionType(2)
                .versionRemark(context.getVersionRemark() != null ? context.getVersionRemark() : "上传新版本")
                .setAsCurrent(true)
                .tenantId(context.getTenantId())
                .userId(context.getUserId())
                .userName(context.getUsername())
                .build();

        fileVersionService.createVersion(versionCommand);
    }

    public ExecuteResult handleOverwrite(
            FileUploadContext context,
            FileExistenceContext existFile,
            Path tempFile,
            String fileMd5,
            String fileSha256
    ) {
        if (!Objects.equals(context.getUserId(), existFile.getUserId())) {
            throw new FilePermissionException(FileErrorCodeEnums.OVERWRITE_DENIED, existFile.getFileId());
        }

        StorageTypeEnums storageType = StorageTypeEnums.fromCode(context.getStorageType());
        StoragePlugin plugin = pluginManager.getPlugin(storageType);
        if (plugin == null) {
            throw new FileStorageException("STORAGE_TYPE_NOT_SUPPORTED",
                    "storage.type.not.supported", String.valueOf(context.getStorageType()));
        }

        String filePath;
        try (InputStream is = Files.newInputStream(tempFile)) {
            String bucket = context.getBucket() != null ? context.getBucket() : "default";
            filePath = plugin.upload(is, context.getFileName(), context.getFileSize(),
                    context.getTenantId(), bucket);
        } catch (IOException e) {
            throw new FileStorageException("STORAGE_UPLOAD_FAILED", e, context.getStorageType());
        }
        String fileUrl = plugin.getFileUrl(filePath);

        try {
            self.persistOverwrite(existFile, context, fileMd5, fileSha256, filePath, fileUrl);
        } catch (Exception e) {
            // 事务回滚后，补偿删除远程文件
            cleanupTempFile(tempFile);
            plugin.delete(filePath);  // 补偿操作，清理孤儿文件
            throw e;
        }

        cleanupTempFile(tempFile);

        return ExecuteResult.builder()
                .fileId(existFile.getFileId())
                .fileName(context.getFileName())
                .fileSize(context.getFileSize())
                .fileMd5(fileMd5)
                .fileSha256(fileSha256)
                .fileUrl(fileUrl)
                .storageType(context.getStorageType())
                .tenantId(context.getTenantId())
                .uploadStatus(UploadStatusEnums.COMPLETED.getCode())
                .isDuplicate(true)
                .sourceFileId(existFile.getFileId())
                .versionNumber(existFile.getVersionNumber())
                .message("文件已覆盖更新")
                .build();
    }

    /**
     * 覆盖更新的持久化操作（短事务，仅包含DB写）
     * <p>通过 self 代理调用，确保 @Transactional 生效</p>
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void persistOverwrite(
            FileExistenceContext existFile,
            FileUploadContext context,
            String fileMd5,
            String fileSha256,
            String filePath,
            String fileUrl
    ) {
        fileInfoMapper.updateFileContent(
                existFile.getFileId(),
                filePath,
                fileUrl,
                context.getFileSize(),
                fileMd5,
                fileSha256,
                LocalDateTime.now(),
                context.getTenantId()
        );
        String fingerprint = FileFingerprintUtils.buildFingerprint(
                fileMd5, context.getFileSize(), context.getStorageType(), context.getTenantId()
        );
        fileDeduplicationDao.updateByFileId(
                existFile.getFileId(),
                fingerprint,
                fileMd5,
                fileSha256,
                context.getFileSize(),
                context.getTenantId()
        );
    }

    private ExecuteResult handleAutoRename(FileUploadContext context, Path tempFile) {
        String newFileName = FileNameUtils.generateUniqueFileName(context.getFileName());
        context.setFileName(newFileName);
        context.setEnableDeduplication(EnableEnums.DISABLED.getCode());
        cleanupTempFile(tempFile);
        return ExecuteResult.builder()
                .fileName(newFileName)
                .message("文件名已自动重命名，请重新上传")
                .uploadStatus(UploadStatusEnums.UPLOADING.getCode())
                .build();
    }

    private InputStream getInputStreamSafely(Path tempFile) {
        try {
            return Files.newInputStream(tempFile);
        } catch (IOException e) {
            throw new FileStorageException("FILE_READ_FAILED", e, tempFile.toString());
        }
    }

    private void cleanupTempFile(Path tempFile) {
        if (tempFile != null && Files.exists(tempFile)) {
            try {
                Files.deleteIfExists(tempFile);
                log.debug("临时文件已删除: {}", tempFile);
            } catch (IOException e) {
                log.warn("删除临时文件失败: {}", tempFile, e);
            }
        }
    }
}