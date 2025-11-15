package com.yaocode.sts.common.resources.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注解集合
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:58
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface ApiParameters {
    ApiParameter[] value() default {};
}
