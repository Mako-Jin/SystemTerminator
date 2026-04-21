package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.resources.constants.IConstants;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Server资源
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:42
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Resources(type = ResourceTypeEnums.SERVER)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ServerResources {

    @AliasFor(annotation = Resources.class, attribute = "value")
    String value() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "code")
    String code() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "name")
    String name() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "desc")
    String desc() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "icon")
    String icon() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "version")
    String version() default IConstants.DEFAULT_RESOURCE_VERSION;

    @AliasFor(annotation = Resources.class, attribute = "isEnabled")
    boolean isEnabled() default true;

    @AliasFor(annotation = Resources.class, attribute = "isDeprecated")
    boolean isDeprecated() default false;

    String[] parent() default {};

    @AliasFor(annotation = Resources.class, attribute = "contactInfo")
    ContactInfo contact() default @ContactInfo();

}
