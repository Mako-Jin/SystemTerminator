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
 * 同一租户不同用户策略
 * 同一租户不同用户上传相同文件时，创建新版本
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(40)
public class SameTenantStrategy extends AbstractDuplicateStrategy {

    public SameTenantStrategy() {
        this.name = "同一租户版本策略";
        this.priority = 40;
        this.strategy = DuplicateFileStrategyEnums.NEW_VERSION;
    }

    @Override
    public boolean isSupport(FileUploadContext context, FileExistenceContext existFile) {
        if (existFile == null || !existFile.getExists()) {
            return false;
        }
        // 同一租户 + 不同用户
        return Objects.equals(context.getTenantId(), existFile.getTenantId()) &&
                !Objects.equals(context.getUserId(), existFile.getUserId());
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    ) {
        // 实际执行由上层调用方完成
        return null;
    }
}