package com.yaocode.sts.common.resources.annotation;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 联系人信息注解
 * @author: Jin-LiangBo
 * @date: 2026年01月20日 15:58
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ContactInfo {

    /**
     * 联系人名称
     */
    String name() default SymbolConstants.EMPTY_STR;
    /**
     * 文档地址
     */
    String docsUrl() default SymbolConstants.EMPTY_STR;
    /**
     * 联系人邮箱
     */
    String email() default SymbolConstants.EMPTY_STR;

}
