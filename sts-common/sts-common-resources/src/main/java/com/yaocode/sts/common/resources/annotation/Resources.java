package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.RequestMethodEnums;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;

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

    String code() default "";

    String name() default "";

    String desc() default "";

    ResourceTypeEnums type();

    String path() default "";

    RequestMethodEnums requestMethod() default RequestMethodEnums.GET;

    boolean isEnabled() default true;

    boolean isDeprecated() default false;

    boolean isWhiteList() default false;

    String menuIcon() default "";

    String version() default "0.0.0.0";

}
