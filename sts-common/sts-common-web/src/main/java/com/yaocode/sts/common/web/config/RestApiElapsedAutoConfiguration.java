package com.yaocode.sts.common.web.config;

import com.yaocode.sts.common.web.annotation.EnableRestApiElapsed;
import com.yaocode.sts.common.web.aspect.RestApiElapsedAspect;
import com.yaocode.sts.common.web.properties.RestApiElapsedProperties;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;
import java.util.Objects;

/**
 * 启动接口打印耗时日志
 * @author: Jin-LiangBo
 * @date: 2025年10月30日 20:31
 */
@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(RestApiElapsedProperties.class)
public class RestApiElapsedAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RestApiElapsedAutoConfiguration.class);

    @Resource
    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public RestApiElapsedAspect restApiElapsedAspect(RestApiElapsedProperties properties) {
        Map<String, Object> enableRestApiElapsedBean = applicationContext.getBeansWithAnnotation(EnableRestApiElapsed.class);
        AnnotationAttributes attributes = null;
        if (!enableRestApiElapsedBean.isEmpty()) {
            Object mainBean = enableRestApiElapsedBean.values().iterator().next();
            EnableRestApiElapsed annotation = mainBean.getClass().getAnnotation(EnableRestApiElapsed.class);
            attributes = AnnotationUtils.getAnnotationAttributes(annotation, true, true);
        }
        boolean defaultEnabled = Objects.nonNull(attributes) && attributes.getBoolean("value");
        if (!properties.isEnabled() || !defaultEnabled) {
            logger.info("rest api elapsed disabled");
        } else {
            logger.info("rest api elapsed enabled {} limit {}ms", properties.isEnabled(), properties.getThreshold());
        }
        return new RestApiElapsedAspect(properties, attributes);
    }

}
