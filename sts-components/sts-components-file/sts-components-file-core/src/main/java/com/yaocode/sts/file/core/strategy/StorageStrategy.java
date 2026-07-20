package com.yaocode.sts.file.core.strategy;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;

/**
 * 存储选择策略接口
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface StorageStrategy {

    /**
     * 选择存储类型
     *
     * @param context 选择上下文
     * @return 存储类型
     */
    StorageTypeEnums selectStorage(StorageSelectionContext context);

    /**
     * 策略名称
     */
    StrategyTypeEnums getStrategy();

    /**
     * 策略优先级（数字越小优先级越高）
     */
    default int getPriority() {
        return 10;
    }
}
