package com.yaocode.sts.auth.application.dto;

import lombok.Data;

/**
 * 用户注册Dto
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:07
 */
@Data
public class UserRegistrationDto {

    /**
     * 租户ID（必填）
     */
    private String tenantId;

    /**
     * 用户名（必填，4-20位字母数字下划线）
     */
    private String username;

    /**
     * 密码（必填，8-20位，包含字母和数字）
     */
    private String password;

    /**
     * 确认密码
     */
    private String confirmPassword;

    /**
     * 邮箱（可选）
     */
    private String email;

    /**
     * 手机号（可选）
     */
    private String phoneNum;

    /**
     * 昵称（可选）
     */
    private String nickname;

    /**
     * 验证码Key
     */
    private String captchaKey;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 是否同意用户协议
     */
    private Boolean agreeTerms = false;

    /**
     * 来源
     */
    private String source;

    /**
     * IP地址（服务端自动获取）
     */
    private String ipAddress;
}
