package com.yaocode.sts.file.core.strategy;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.spi.DuplicateStrategy;
import lombok.Getter;
import lombok.Setter;

/**
 * 重复文件策略抽象基类
 * 提供默认实现，子类只需实现 isSupport() 方法
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
@Setter
public abstract class AbstractDuplicateStrategy implements DuplicateStrategy {

    /** 策略名称 */
    protected String name;

    /** 优先级（数字越小优先级越高） */
    protected int priority = 100;

    /** 匹配的策略枚举 */
    protected DuplicateFileStrategyEnums strategy;

    /** 是否启用
     * -- GETTER --
     *  检查策略是否启用
     */
    protected boolean enabled = true;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getName() {
        return name != null ? name : getClass().getSimpleName();
    }

    @Override
    public DuplicateFileStrategyEnums getStrategy() {
        return strategy;
    }

}
