package com.yaocode.sts.common.web.config;

import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.common.web.advice.GlobalExceptionHandler;
import com.yaocode.sts.common.web.advice.I18nResponseAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * web I18n ResponseAdvice 自动配置
 * @author: Jin-LiangBo
 * @date: 2026年07月23日
 */
@Slf4j
@Configuration
public class ResponseAdviceAutoConfiguration {

    @Bean
    public I18nResponseAdvice i18nResponseAdvice(MessageUtils messageUtils) {
        log.info("=== 通过 AutoConfiguration 创建 I18nResponseAdvice ===");
        return new I18nResponseAdvice(messageUtils);
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(MessageUtils messageUtils) {
        log.info("=== 通过 AutoConfiguration 创建 GlobalExceptionHandler ===");
        return new GlobalExceptionHandler(messageUtils);
    }

}
