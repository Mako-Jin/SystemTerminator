package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;
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
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@" +
                    "[A-Za-z0-9][A-Za-z0-9-]*(\\.[A-Za-z0-9][A-Za-z0-9-]*)*\\.[A-Za-z]{2,}$"
    );

    /**
     * 常见邮箱服务商域名白名单（可选）TODO 移动到可配置表里面
     */
    // private static final Set<String> COMMON_EMAIL_DOMAINS = Set.of(
    //         "gmail.com", "qq.com", "163.com", "126.com", "sina.com",
    //         "sohu.com", "yahoo.com", "hotmail.com", "outlook.com",
    //         "foxmail.com", "icloud.com", "live.com", "msn.com"
    // );
    //
    // /**
    //  * 临时邮箱域名黑名单（可选） TODO 移动到可配置表里面
    //  */
    // private static final Set<String> DISPOSABLE_EMAIL_DOMAINS = Set.of(
    //         "gmail.com", "qq.com", "163.com", "126.com", "sina.com",
    //         "sohu.com", "yahoo.com", "hotmail.com", "outlook.com",
    //         "foxmail.com", "icloud.com", "live.com", "msn.com"
    // );

    private static final Integer EMAIL_MAX_LENGTH = 254;

    protected Email(String value) {
        super(value);
    }

    public static Email of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("邮箱地址不能为空");
        }
        String trimmedEmail = value.trim();
        if (trimmedEmail.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException("邮箱地址过长");
        }

        if (!EMAIL_PATTERN.matcher(trimmedEmail).matches()) {
            throw new IllegalArgumentException("邮箱格式不正确: " + value);
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
