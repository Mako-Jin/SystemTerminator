package com.yaocode.sts.file.application.strategy;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.FileHashException;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.strategy.AbstractDuplicateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 安全校验策略
 * 当MD5匹配但SHA-256不匹配时，抛出异常（可能存在文件篡改）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Order(20)
@Component
public class SecurityValidationStrategy extends AbstractDuplicateStrategy {

    public SecurityValidationStrategy() {
        this.name = "安全校验策略";
        this.priority = 20;
        this.strategy = DuplicateFileStrategyEnums.THROW_EXCEPTION;
    }

    @Override
    public boolean isSupport(FileUploadContext context, FileExistenceContext existFile) {
        // 如果SHA-256不存在，跳过校验
        if (context.getFileSha256() == null || existFile.getFileSha256() == null) {
            return false;
        }

        // MD5匹配但SHA-256不匹配 → 可能存在安全风险
        boolean isMismatch = !context.getFileSha256().equals(existFile.getFileSha256());
        if (isMismatch) {
            log.warn("MD5匹配但SHA-256不匹配, fileId={}, md5={}", existFile.getFileId(), context.getFileMd5());
        }
        return isMismatch;
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    ) {
        // 抛出异常，由上层处理
        throw new FileHashException(FileErrorCodeEnums.FILE_HASH_MISMATCH, context.getFileMd5());
    }
}