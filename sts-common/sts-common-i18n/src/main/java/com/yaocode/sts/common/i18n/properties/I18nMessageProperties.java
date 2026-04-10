package com.yaocode.sts.common.i18n.properties;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * 国际化配置属性
 * @author: Jin-LiangBo
 * @date: 2026年04月08日 11:14
 */
@ConfigurationProperties(prefix = "yaocode.messages.i18n")
@ConditionalOnProperty(name = "yaocode.messages.i18n.enabled", havingValue = "true")
public class I18nMessageProperties {

    /**
     * 是否启用数据库国际化
     */
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 降级配置
     */
    @NestedConfigurationProperty
    private Fallback fallback = new Fallback();

    public Fallback getFallback() {
        return fallback;
    }

    public void setFallback(Fallback fallback) {
        this.fallback = fallback;
    }

    /**
     * 降级配置
     */
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
        private String basename = "messages";

        /**
         * 降级文件的编码
         */
        private String encoding = "UTF-8";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isUseCodeAsDefaultMessage() {
            return useCodeAsDefaultMessage;
        }

        public void setUseCodeAsDefaultMessage(boolean useCodeAsDefaultMessage) {
            this.useCodeAsDefaultMessage = useCodeAsDefaultMessage;
        }

        public String getBasename() {
            return basename;
        }

        public void setBasename(String basename) {
            this.basename = basename;
        }

        public String getEncoding() {
            return encoding;
        }

        public void setEncoding(String encoding) {
            this.encoding = encoding;
        }
    }

}
