package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.resources.constants.IConstants;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 15:33
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD,
        ElementType.PACKAGE, ElementType.TYPE_PARAMETER, ElementType.TYPE_USE,
        ElementType.ANNOTATION_TYPE
})
public @interface Resources {

    @AliasFor(annotation = Resources.class, attribute = "code")
    String value() default SymbolConstants.EMPTY_STR;

    @AliasFor(annotation = Resources.class, attribute = "value")
    String code() default SymbolConstants.EMPTY_STR;

    String name() default SymbolConstants.EMPTY_STR;

    String desc() default SymbolConstants.EMPTY_STR;

    ResourceTypeEnums type();

    String version() default IConstants.DEFAULT_RESOURCE_VERSION;

    String icon() default SymbolConstants.EMPTY_STR;

    boolean isEnabled() default true;

    boolean isDeprecated() default false;

    ContactInfo contactInfo() default @ContactInfo;

}
