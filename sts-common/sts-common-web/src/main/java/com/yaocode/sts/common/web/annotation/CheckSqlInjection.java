package com.yaocode.sts.common.web.annotation;

import com.yaocode.sts.common.web.constants.WebI18nConstants;
import com.yaocode.sts.common.web.validator.SqlInjectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查sql 注入
 * @author Mr.Jin
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(CheckSqlInjection.List.class)
@Constraint(validatedBy = SqlInjectionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface CheckSqlInjection {

    String message() default WebI18nConstants.SQL_INJECTION_DEFAULT_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @interface List {
        CheckSqlInjection[] value();
    }

}
