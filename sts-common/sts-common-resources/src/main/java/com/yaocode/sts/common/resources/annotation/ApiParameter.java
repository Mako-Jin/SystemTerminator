package com.yaocode.sts.common.resources.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:57
 */
@Inherited
@Repeatable(ApiParameters.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ApiParameter {

    String name() default "";

    String description() default "";

    boolean required() default false;

    boolean deprecated() default false;

    boolean allowEmptyValue() default false;

    ApiModel model() default @ApiModel;

    ApiHeader[] headers() default {};

}
