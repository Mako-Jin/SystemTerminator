package com.yaocode.sts.common.web.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 上下文基类 - 统一管理 ThreadLocal
 * @param <T> 具体的上下文类型
 */
public abstract class BaseAbstractContext<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseAbstractContext.class);

    private final ThreadLocal<T> contextHolder = new ThreadLocal<>();

    /**
     * 设置上下文
     */
    public void set(T context) {
        if (context != null) {
            contextHolder.set(context);
            logger.debug("{} context set: {}", getContextName(), context);
        }
    }

    /**
     * 获取上下文
     */
    public T get() {
        T context = contextHolder.get();
        if (context == null) {
            context = getDefault();
            if (context != null) {
                contextHolder.set(context);
            }
        }
        return context;
    }

    /**
     * 清除上下文
     */
    public void clear() {
        contextHolder.remove();
        logger.debug("{} context cleared", getContextName());
    }

    /**
     * 检查是否存在上下文
     */
    public boolean exists() {
        return contextHolder.get() != null;
    }

    /**
     * 创建默认上下文（子类可覆盖）
     */
    protected T getDefault() {
        return null;
    }

    /**
     * 获取上下文名称（用于日志）
     */
    protected abstract String getContextName();

}
