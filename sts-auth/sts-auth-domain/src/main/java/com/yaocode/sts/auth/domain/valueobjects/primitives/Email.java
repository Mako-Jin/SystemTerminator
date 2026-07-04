package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * 邮箱值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 20:58
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Email extends Identifier<String> {

    /**
     * 邮箱校验正则
     */
    private static final Pattern EMAIL_PATTERN = RegexConstants.EMAIL_PATTERN_COMPILED;

    private Email(String value) {
        super(value);
    }

    public static Email of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMAIL_CANNOT_BE_BLANK);
        }
        String trimmedEmail = value.trim();
        if (trimmedEmail.length() > CommonConstants.EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMAIL_TOO_LONG);
        }

        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.EMAIL_FORMAT_INVALID);
        }
        String localPart = value.substring(0, value.indexOf('@'));
        String domain = value.substring(value.indexOf('@') + 1);

        // 验证本地部分长度
        // if (localPart.length() > 64) {
        //     throw new IllegalArgumentException("邮箱用户名部分过长");
        // }
        //
        // // 验证不能以点号开头或结尾
        // if (localPart.startsWith(".") || localPart.endsWith(".")) {
        //     throw new IllegalArgumentException("邮箱用户名不能以点号开头或结尾");
        // }
        //
        // // 验证不能有连续的点号
        // if (localPart.contains("..")) {
        //     throw new IllegalArgumentException("邮箱用户名不能包含连续的点号");
        // }
        //
        // // 业务规则：可以添加对临时邮箱的过滤
        // if (DISPOSABLE_EMAIL_DOMAINS.contains(domain)) {
        //     throw new IllegalArgumentException("不支持临时邮箱服务");
        // }
        return new Email(value);
    }

}