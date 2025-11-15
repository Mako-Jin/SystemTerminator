package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务资源注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:47
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Resources(type = ResourceTypeEnums.SERVICE)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface ServiceResources {
}
