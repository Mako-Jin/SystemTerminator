package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.resources.enums.MediaTypeEnums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 响应注解
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 17:59
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(ApiResponses.class)
public @interface ApiResponse {

    String description() default "";

    String responseCode() default "000000";

    MediaTypeEnums mediaType() default MediaTypeEnums.APPLICATION_JSON;

    ApiModel model() default @ApiModel;

    ApiHeader[] headers() default {};
}
