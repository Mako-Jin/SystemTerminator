package com.yaocode.sts.file.application.strategy;

import com.yaocode.sts.file.core.strategy.AbstractDuplicateStrategy;
import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.ExecuteResult;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 用户指定策略
 * 优先级最高（10），直接使用用户指定的策略
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
@Order(10)
public class UserSpecifiedStrategy extends AbstractDuplicateStrategy {

    public UserSpecifiedStrategy() {
        this.name = "用户指定策略";
        this.priority = 10;
        this.strategy = null; // 由用户指定，在isSupport中动态返回
    }



    @Override
    public boolean isSupport(FileUploadContext context, FileExistenceContext existFile) {
        // 用户指定了策略才支持
        return context.getSpecifiedStrategy() != null;
    }

    @Override
    public DuplicateFileStrategyEnums getStrategy() {
        // 策略由上下文中的用户指定
        return null;
    }

    /**
     * 获取用户指定的策略
     */
    public DuplicateFileStrategyEnums getSpecifiedStrategy(FileUploadContext context) {
        return context.getSpecifiedStrategy();
    }

    @Override
    public ExecuteResult execute(
            FileUploadContext context,
            FileExistenceContext existFile,
            byte[] fileBytes
    ) {
        // 用户指定策略不在此执行，由选择器直接返回用户指定的策略
        return null;
    }
}