package com.yaocode.sts.file.application.strategy;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.strategy.AbstractDuplicateStrategy;
import com.yaocode.sts.file.infrastructure.entity.FileInfoEntity;
import com.yaocode.sts.file.infrastructure.mapper.FileInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 同一用户策略
 * 同一用户上传相同文件时，默认覆盖
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(30)
public class SameUserStrategy extends AbstractDuplicateStrategy {

    @Resource
    private FileInfoMapper fileInfoMapper;

    public SameUserStrategy() {
        this.name = "同一用户覆盖策略";
        this.priority = 30;
        this.strategy = DuplicateFileStrategyEnums.OVERWRITE;
    }

    @Override
    public boolean isSupport(FileUploadContext context, FileExistenceContext existFile) {
        if (existFile == null || !existFile.getExists()) {
            return false;
        }
        // 同一租户 + 同一用户
        return Objects.equals(context.getTenantId(), existFile.getTenantId()) &&
                Objects.equals(context.getUserId(), existFile.getUserId());
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    ) {
        // 1. 更新文件信息
        FileInfoEntity entity = fileInfoMapper.selectByFileIdAndTenant(
                existFile.getFileId(), context.getTenantId()
        );

        if (entity == null) {
            log.warn("文件不存在，无法覆盖: fileId={}", existFile.getFileId());
            return null;
        }

        // 更新文件元数据
        entity.setFileName(context.getFileName());
        entity.setFileSize(context.getFileSize());
        entity.setFileMd5(context.getFileMd5());
        entity.setFileSha256(context.getFileSha256());
        entity.setUpdatedTime(LocalDateTime.now());
        entity.setVersion(entity.getVersion() + 1);

        fileInfoMapper.updateById(entity);

        return ExecuteResult.builder()
                .fileId(existFile.getFileId())
                .fileName(context.getFileName())
                .fileSize(context.getFileSize())
                .fileMd5(context.getFileMd5())
                .fileUrl(existFile.getFileUrl())
                .storageType(context.getStorageType())
                .tenantId(context.getTenantId())
                .uploadStatus(1)
                .isDuplicate(true)
                .sourceFileId(existFile.getFileId())
                .versionNumber(entity.getVersion())
                .message("文件已覆盖")
                .build();
    }
}
