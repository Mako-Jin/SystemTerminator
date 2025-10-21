package com.yaocode.sts.auth.infrastructure.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 国际化信息处理类
 * @author: Jin-LiangBo
 * @date: 2025年10月09日 19:16
 */
@Slf4j
public class MessageUtils {

    private final MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 不带占位符
     * @param key 消息key
     * @return 国际化消息
     */
    public String getMessage(String key){
        try {
            return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException noSuchMessageException) {
            log.debug("get message error:key is {}" , key);
            return key;
        }

    }

    /**
     * 带占位符的
     * @param key 消息key
     * @return 国际化消息
     */
    public String getMessage(String key, String defaultMessage){
        try {
            return messageSource.getMessage(key, null,defaultMessage, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.debug("get message error:key = {}; defaultMessage = {}" , key, defaultMessage);
            log.error("get message error:message is {} , cause is {}" , e.getMessage(), e.getCause());
            return defaultMessage;
        }
    }

    /**
     * 不带占位符
     * @param key 消息key
     * @return 国际化消息
     */
    public String getMessage(String key, Object[] args){
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException noSuchMessageException) {
            log.debug("get message error:key is {}" , key);
            return key;
        }

    }

    /**
     * 带占位符的
     * @param key 消息key
     * @return 国际化消息
     */
    public String getMessage(String key, Object[] args, String defaultMessage){
        try {
            return messageSource.getMessage(key, args, defaultMessage, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            log.debug("get message error:key = {}; defaultMessage = {}" , key, defaultMessage);
            log.error("get message error:message is {} , cause is {}" , e.getMessage(), e.getCause());
            return defaultMessage;
        }
    }

}
