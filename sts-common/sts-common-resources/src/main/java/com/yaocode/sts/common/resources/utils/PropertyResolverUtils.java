package com.yaocode.sts.common.resources.utils;

import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.NoSuchMessageException;

/**
 * 属性处理工具类
 * 解析占位符，处理国际化
 * @author: Jin-LiangBo
 * @date: 2026年01月07日 18:15
 */
@Slf4j
public class PropertyResolverUtils {

    /**
     * The Factory.
     */
    private final ConfigurableBeanFactory configurableBeanFactory;

    /**
     * The Message source.
     */
    private final MessageUtils messageUtils;

    /**
     * The Spring doc config properties.
     */
    private final ResourcesConfigProperties resourcesConfigProperties;

    public PropertyResolverUtils(
            ConfigurableBeanFactory configurableBeanFactory,
            MessageUtils messageUtils,
            ResourcesConfigProperties resourcesConfigProperties
    ) {
        this.configurableBeanFactory = configurableBeanFactory;
        this.messageUtils = messageUtils;
        this.resourcesConfigProperties = resourcesConfigProperties;
    }

    public String resolve(String parameterProperty) {
        String result = parameterProperty;
        if (parameterProperty != null) {
            try {
                result = messageUtils.getMessage(parameterProperty, parameterProperty);
            }
            catch (NoSuchMessageException ex) {
                log.trace(ex.getMessage());
            }
            if (parameterProperty.equals(result)) {
                try {
                    result = configurableBeanFactory.resolveEmbeddedValue(parameterProperty);
                }
                catch (IllegalArgumentException ex) {
                    log.warn(ex.getMessage());
                }
            }
        }
        return result;
    }

}
