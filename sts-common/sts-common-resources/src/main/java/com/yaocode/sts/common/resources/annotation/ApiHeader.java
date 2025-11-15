package com.yaocode.sts.common.resources.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Header 请求头，或者响应头，注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 18:30
 */
@Target({})
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHeader {

    String name();

    String description() default "";

    boolean required() default false;

}
