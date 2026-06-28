package com.yaocode.sts.auth.domain.enums;

import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 登录方式枚举
 * @author: Jin-LiangBo
 * @date: 2026-06-28
 */
@Getter
public enum LoginMethodEnums {

    /**
     * 密码登录
     */
    PASSWORD("password", "密码登录"),

    /**
     * 短信验证码登录
     */
    SMS("sms", "短信验证码登录"),

    /**
     * 邮箱验证码登录
     */
    EMAIL("email", "邮箱验证码登录"),

    /**
     * 扫码登录
     */
    QRCODE("qrcode", "扫码登录"),

    /**
     * 社交登录（微信、钉钉等）
     */
    SOCIAL("social", "社交登录");

    private final String code;
    private final String description;

    LoginMethodEnums(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 获取支持的登录方式列表
     */
    public static List<LoginMethodEnums> getSupportedMethods(TenantConfigEntity config) {
        List<LoginMethodEnums> methods = new ArrayList<>();

        if (config == null) {
            methods.add(PASSWORD);
            return methods;
        }

        if (Objects.equals(config.getPasswordLoginEnabled(), OppositeEnums.YES)) {
            methods.add(PASSWORD);
        }
        if (Objects.equals(config.getSmsLoginEnabled(), OppositeEnums.YES)) {
            methods.add(SMS);
        }
        if (Objects.equals(config.getEmailLoginEnabled(), OppositeEnums.YES)) {
            methods.add(EMAIL);
        }
        if (Objects.equals(config.getQrCodeLoginEnabled(), OppositeEnums.YES)) {
            methods.add(QRCODE);
        }

        if (methods.isEmpty()) {
            methods.add(PASSWORD);
        }
        return methods;
    }

    /**
     * 获取默认登录方式
     */
    public static LoginMethodEnums getDefaultMethod(TenantConfigEntity config) {
        if (config == null) {
            return PASSWORD;
        }

        if (Objects.equals(config.getQrCodeLoginEnabled(), OppositeEnums.YES)) {
            return QRCODE;
        }
        if (Objects.equals(config.getSmsLoginEnabled(), OppositeEnums.YES)) {
            return SMS;
        }
        if (Objects.equals(config.getEmailLoginEnabled(), OppositeEnums.YES)) {
            return EMAIL;
        }
        return PASSWORD;
    }

    /**
     * 根据code获取枚举
     */
    public static LoginMethodEnums fromCode(String code) {
        for (LoginMethodEnums method : values()) {
            if (method.code.equals(code)) {
                return method;
            }
        }
        return null;
    }

}
