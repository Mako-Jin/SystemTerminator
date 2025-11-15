package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 页面资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:50
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Resources(type = ResourceTypeEnums.PAGES)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface PageResources {
}
