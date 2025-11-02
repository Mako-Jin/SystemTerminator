package com.yaocode.sts.common.web.validator;

import com.yaocode.sts.common.web.annotation.CheckXss;
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
        Pattern.compile("<script>(.*?)</script>",Pattern.CASE_INSENSITIVE),
        // src='...'
        Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // lonely script tags
        Pattern.compile("</script>",Pattern.CASE_INSENSITIVE),
        Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // eval(...)
        Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // expression(...)
        Pattern.compile("expression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE| Pattern.DOTALL),
        // javascript:...
        Pattern.compile("javascript:",Pattern.CASE_INSENSITIVE),
        // vbscript:...
        Pattern.compile("vbscript:",Pattern.CASE_INSENSITIVE),
        // 空格英文单双引号
        // Pattern.compile("[\s'\"]+", Pattern.CASE_INSENSITIVE),
        // onload(...)=...
        Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        // alert
        Pattern.compile("alert(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile("<", Pattern.MULTILINE | Pattern.DOTALL),
        Pattern.compile(">", Pattern.MULTILINE | Pattern.DOTALL),
        //Checks any html tags i.e. <script, <embed, <object etc.
        Pattern.compile("(<(script|iframe|embed|frame|frameset|object|img|applet|body|html|style|layer|link|ilayer|meta|bgsound))")
    };

    private boolean checkIsXss (String value) {
        if (Objects.isNull(value) || value.length() == 0) {
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
