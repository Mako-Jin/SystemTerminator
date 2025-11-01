package com.yaocode.sts.common.web.annotation;

import com.yaocode.sts.common.web.config.RestApiElapsedAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印接口耗时启动注解
 * @author: Jin-LiangBo
 * @date: 2025年10月29日 22:16
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({RestApiElapsedAutoConfiguration.class })
@Configuration
@EnableAspectJAutoProxy
public @interface EnableRestApiElapsed {

    /**
     * 是否启用接口耗时监控
     */
    @AliasFor("enabled")
    boolean value() default true;

    /**
     * 是否启用接口耗时监控
     */
    @AliasFor("value")
    boolean enabled() default true;

    /**
     * 耗时阈值（毫秒），超过此值的接口会打印WARN日志
     */
    long threshold() default 1000L;

}
