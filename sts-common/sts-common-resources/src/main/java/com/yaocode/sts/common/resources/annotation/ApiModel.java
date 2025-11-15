package com.yaocode.sts.common.resources.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模型注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 18:24
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE,
        ElementType.ANNOTATION_TYPE, ElementType.TYPE_USE
})
public @interface ApiModel {

    String name() default "";

    String title() default "";

    String maximum() default "";

    String minimum() default "";

    int maxLength() default 2147483647;

    int minLength() default 0;

    String pattern() default "";

    boolean nullable() default false;

    boolean deprecated() default false;

    String type() default "";

    String[] allowableValues() default {};

    String defaultValue() default "";

}
