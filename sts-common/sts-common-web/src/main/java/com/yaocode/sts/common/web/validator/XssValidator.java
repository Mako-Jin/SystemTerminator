package com.yaocode.sts.common.web.validator;

import com.yaocode.sts.common.web.annotation.CheckXss;
import com.yaocode.sts.common.web.constants.WebConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * xss 验证类
 * @author Mr.Jin-晋
 */
public class XssValidator implements ConstraintValidator<CheckXss, String> {

    private static final Logger logger = LoggerFactory.getLogger(XssValidator.class);

    @Override
    public void initialize(CheckXss constraintAnnotation) {
        logger.info("==> initialize xss validator");
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !checkIsXss(value);
    }

    private static final Pattern[] PATTERNS = {
        // Script fragments
        Pattern.compile(WebConstants.REGEX_XSS_SCRIPT_TAG, Pattern.CASE_INSENSITIVE),
        // src='...'
        Pattern.compile(WebConstants.REGEX_XSS_SRC_ATTR,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        Pattern.compile(WebConstants.REGEX_XSS_SRC_ATTR,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // lonely script tags
        Pattern.compile(WebConstants.REGEX_XSS_SCRIPT_TAG, Pattern.CASE_INSENSITIVE),
        Pattern.compile(WebConstants.REGEX_XSS_SCRIPT_TAG, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // eval(...)
        Pattern.compile(WebConstants.REGEX_XSS_EVAL,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // expression(...)
        Pattern.compile(WebConstants.REGEX_XSS_EXPRESSION,Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // javascript:...
        Pattern.compile(WebConstants.REGEX_XSS_JAVASCRIPT,Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile(WebConstants.REGEX_XSS_VBSCRIPT,Pattern.CASE_INSENSITIVE),
        // 空格英文单双引号
        // Pattern.compile("[\s'\"]+", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile(WebConstants.REGEX_XSS_ONLOAD, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // alert
        Pattern.compile(WebConstants.REGEX_XSS_ALERT, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(WebConstants.REGEX_XSS_TAG_OPEN, Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(WebConstants.REGEX_XSS_TAG_CLOSE, Pattern.MULTILINE | Pattern.DOTALL),
        //Checks any HTML tags i.e. <script, <embed, <object etc.
        Pattern.compile(WebConstants.REGEX_XSS_HTML_TAG)
    };

    private boolean checkIsXss (String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            return false;
        }
        for (Pattern pattern : PATTERNS) {
            if (pattern.matcher(value).find()) {
                logger.warn("xss check out ==> pattern:{}, value:{}", pattern, value);
                return true;
            }
        }
        return false;
    }

}
