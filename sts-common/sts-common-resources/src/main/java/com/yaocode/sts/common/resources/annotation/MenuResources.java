package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 菜单资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月18日 22:52
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Resources(type = ResourceTypeEnums.MENUS)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface MenuResources {

    @AliasFor(annotation = Resources.class, attribute = "value")
    String value() default "";

    @AliasFor(annotation = Resources.class, attribute = "code")
    String code() default "";

    @AliasFor(annotation = Resources.class, attribute = "name")
    String name() default "";

    @AliasFor(annotation = Resources.class, attribute = "desc")
    String desc() default "";

    @AliasFor(annotation = Resources.class, attribute = "menuIcon")
    String menuIcon() default "";

    @AliasFor(annotation = Resources.class, attribute = "path")
    String path() default "";

    @AliasFor(annotation = Resources.class, attribute = "version")
    String version() default "0.0.0.0";

    @AliasFor(annotation = Resources.class, attribute = "isEnabled")
    boolean isEnabled() default true;

    @AliasFor(annotation = Resources.class, attribute = "isDeprecated")
    boolean isDeprecated() default false;

    @AliasFor(annotation = Resources.class, attribute = "isWhiteList")
    boolean isWhiteList() default false;

    SystemResources[] belongTo() default {};

}
