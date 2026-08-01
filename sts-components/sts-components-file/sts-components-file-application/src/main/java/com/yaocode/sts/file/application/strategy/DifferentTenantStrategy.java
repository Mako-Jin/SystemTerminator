package com.yaocode.sts.file.application.strategy;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.strategy.AbstractDuplicateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 不同租户策略
 * 不同租户上传相同文件时，默认复用（秒传）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(50)
public class DifferentTenantStrategy extends AbstractDuplicateStrategy {

    public DifferentTenantStrategy() {
        this.name = "跨租户复用策略";
        this.priority = 50;
        this.strategy = DuplicateFileStrategyEnums.REUSE;
    }

    @Override
    public boolean isSupport(FileUploadContext context, FileExistenceContext existFile) {
        if (existFile == null || !existFile.getExists()) {
            return false;
        }
        // 不同租户
        return !Objects.equals(context.getTenantId(), existFile.getTenantId());
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    ) {
        log.info("执行跨租户复用策略: sourceFileId={}, targetTenant={}",
                existFile.getFileId(), context.getTenantId());

        return ExecuteResult.builder()
                .fileId(existFile.getFileId())
                .fileName(context.getFileName())
                .fileSize(existFile.getFileSize())
                .fileMd5(existFile.getFileMd5())
                .fileUrl(existFile.getFileUrl())
                .storageType(existFile.getStorageType())
                .tenantId(context.getTenantId())
                .uploadStatus(1)
                .isDuplicate(true)
                .sourceFileId(existFile.getFileId())
                .versionNumber(1)
                .message("跨租户秒传成功")
                .build();
    }
}
