package com.yaocode.sts.common.i18n;

import com.yaocode.sts.common.i18n.properties.I18nMessageProperties;
import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 自定义DB的消息源实现类
 * @author: Jin-LiangBo
 * @date: 2026年04月09日 11:29
 */
public class DatabaseMessageSource extends AbstractMessageSource {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseMessageSource.class);

    /**
     * 降级消息源（支持多个）
     */
    private final List<MessageSource> fallbackMessageSources = new ArrayList<>();

    private final I18nMessageRepository repository;

    private final I18nMessageProperties properties;

    public DatabaseMessageSource(I18nMessageRepository repository, I18nMessageProperties properties) {
        this.repository = repository;
        this.properties = properties;
        this.setUseCodeAsDefaultMessage(properties.getFallback().isUseCodeAsDefaultMessage());

        // 初始化降级消息源
        this.initFallbackMessageSources();
    }

    /**
     * 初始化降级消息源（按优先级顺序）
     */
    private void initFallbackMessageSources() {
        // 1. 查找所有jar包下面的消息文件
        fallbackMessageSources.add(createModuleMessageSource());

        // 2. 查找主包下面的消息文件
        // fallbackMessageSources.add(createGlobalMessageSource());

        logger.info("Initialized {} fallback message sources", fallbackMessageSources.size());
    }

    /**
     * 创建模块级消息源
     * 可以加载多个jar包内的 messages.properties
     */
    private MessageSource createModuleMessageSource() {
        ResourceBundleMessageSource moduleSource = new ResourceBundleMessageSource();

        // 支持多个基础名称（可以加载不同模块的消息）
        String[] baseNames = new String[]{"i18n/messages", "META-INF/i18n/messages"};
        moduleSource.setBasenames(baseNames);
        moduleSource.setDefaultEncoding(properties.getFallback().getEncoding());
        moduleSource.setFallbackToSystemLocale(false);

        return moduleSource;
    }

    @Override
    @Nullable
    protected MessageFormat resolveCode(String code, Locale locale) {
        String message = repository.getMessage(code, locale.toString());
        if (StringUtils.hasText(message)) {
            return new MessageFormat(message, locale);
        }
        for (MessageSource fallback : fallbackMessageSources) {
            try {
                String fallbackMessage = fallback.getMessage(code, null, locale);
                if (StringUtils.hasText(fallbackMessage)) {
                    logger.debug("Fallback hit: code={}, locale={}, source={}",
                            code, locale, fallback.getClass().getSimpleName());
                    return new MessageFormat(fallbackMessage, locale);
                }
            } catch (NoSuchMessageException e) {
                // 继续下一个
            }
        }
        return null;
    }

    public I18nMessageRepository getRepository() {
        return repository;
    }

    public I18nMessageProperties getProperties() {
        return properties;
    }
}
