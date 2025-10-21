package com.yaocode.sts.common.web.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义子路径映射
 * @author: Jin-LiangBo
 * @date: 2025年10月18日 10:45
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// @RequestMapping
// @ResponseBody
public @interface SubRequestMapping {

    // @AliasFor(annotation = RequestMapping.class, attribute = "value")
    @AliasFor("path")
    String[] value() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "path")
    @AliasFor("value")
    String[] path() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "method")
    RequestMethod[] method() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "params")
    String[] params() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "headers")
    String[] headers() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "consumes")
    String[] consumes() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "produces")
    String[] produces() default {};

    // @AliasFor(annotation = RequestMapping.class, attribute = "name")
    String name() default "";

}
