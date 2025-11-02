package com.yaocode.sts.common.web.validator;

import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * sql 注入 校验类
 * @author Mr.Jin-晋
 */
public class SqlInjectionValidator implements ConstraintValidator<CheckSqlInjection, String> {

    private static final Logger logger = LoggerFactory.getLogger(SqlInjectionValidator.class);

    @Override
    public void initialize(CheckSqlInjection constraintAnnotation) {
        logger.info("==> initialize sql injection validator");
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !containsSqlInjection(value);
    }

    /**
     * 正则表达式匹配常见的SQL注入攻击模式
     */
    private static final Pattern SQL_INJECTION_PATTERN =
            Pattern.compile(
                    "(?i)\\b(?:select|update|and|or|grant|alter|delete|chr|mid|" +
                            "insert|truncate|char|into|substr|ascii|declare|exec|" +
                            "count|master|drop|execute)\\b|(\\*|;|\\+|'|%)"
            );

    /**
     * 检查输入是否包含SQL注入攻击模式。
     * @param input 用户输入
     * @return true 如果输入包含可能的SQL注入攻击模式，否则返回false
     */
    public static boolean containsSqlInjection(String input) {
        if (Objects.isNull(input) || input.length() == 0) {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(input).matches();
    }

}
