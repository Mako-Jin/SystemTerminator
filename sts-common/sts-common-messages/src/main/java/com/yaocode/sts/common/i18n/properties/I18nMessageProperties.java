package com.yaocode.sts.common.i18n.properties;

import com.yaocode.sts.common.i18n.constants.I18nKeyConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 国际化配置属性
 * @author: Jin-LiangBo
 * @date: 2026年04月08日 11:14
 */
@Setter
@Getter
@ConfigurationProperties(prefix = I18nKeyConstants.CONFIG_PREFIX)
@ConditionalOnProperty(name = I18nKeyConstants.CONFIG_ENABLED, havingValue = "true")
public class I18nMessageProperties {

    /**
     * 是否启用数据库国际化
     */
    private boolean enabled = false;

    /**
     * 降级配置
     */
    @NestedConfigurationProperty
    private Fallback fallback = new Fallback();

    /**
     * 降级配置
     */
    @Setter
    @Getter
    public static class Fallback {
        /**
         * 是否启用降级（数据库失败时降级到properties文件）
         */
        private boolean enabled = true;

        /**
         * 是否使用代码作为默认消息（当找不到任何翻译时）
         * true: 返回code本身，如 "user.login.title"
         * false: 抛出 NoSuchMessageException
         */
        private boolean useCodeAsDefaultMessage = true;

        /**
         * 全局降级消息文件基础名称（classpath根目录）
         */
        private String basename = I18nKeyConstants.DEFAULT_BASENAME;

        /**
         * 降级文件的编码
         */
        private String encoding = I18nKeyConstants.DEFAULT_ENCODING;

    }

}
