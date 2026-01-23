package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 模块资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:49
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Resources(type = ResourceTypeEnums.MODULE)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ModuleResources {

    @AliasFor(annotation = Resources.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = Resources.class, attribute = "code")
    String code() default "";

    @AliasFor(annotation = Resources.class, attribute = "name")
    String name() default "";

    @AliasFor(annotation = Resources.class, attribute = "desc")
    String desc() default "";

    String[] path() default {""};

    @AliasFor(annotation = Resources.class, attribute = "icon")
    String icon() default "";

    @AliasFor(annotation = Resources.class, attribute = "version")
    String version() default "0.0.0.0";

    @AliasFor(annotation = Resources.class, attribute = "isEnabled")
    boolean isEnabled() default true;

    @AliasFor(annotation = Resources.class, attribute = "isDeprecated")
    boolean isDeprecated() default false;

    boolean isWhiteList() default false;

    /**
     * 父编码，填写service的资源编码-code
     * @return ModuleResources[]
     */
    String[] parent() default {};

    @AliasFor(annotation = Resources.class, attribute = "contactInfo")
    ContactInfo contact() default @ContactInfo();

}
