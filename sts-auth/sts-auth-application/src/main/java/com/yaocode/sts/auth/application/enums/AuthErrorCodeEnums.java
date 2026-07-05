package com.yaocode.sts.auth.application.enums;

import lombok.Getter;

/**
 * 权限服务错误码枚举类
 * @author: Jin-LiangBo
 * @date: 2025年10月08日 17:53
 */
@Getter
public enum AuthErrorCodeEnums {

    // ==================== 用户相关错误码 ====================
    /**
     * 用户不存在
     */
    USER_NOT_FOUND("004000", "auth.user.not.found"),

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS("004001", "auth.username.exists"),

    /**
     * 用户邮箱已存在
     */
    USER_EMAIL_EXISTS("004002", "auth.user.email.exists"),

    /**
     * 用户手机号已存在
     */
    USER_PHONE_EXISTS("004003", "auth.user.phone.exists"),

    // ==================== 认证相关错误码 ====================
    /**
     * 认证异常
     */
    AUTHENTICATION_ERROR("004010", "auth.authentication.error"),

    /**
     * 密码错误
     */
    PASSWORD_ERROR("004011", "auth.password.error"),

    /**
     * 账号已锁定
     */
    ACCOUNT_LOCKED("004012", "auth.account.locked"),

    /**
     * 需要验证码
     */
    CAPTCHA_REQUIRED("004013", "auth.captcha.required"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR("004014", "auth.captcha.error"),

    /**
     * Remember Me令牌无效
     */
    REMEMBER_ME_INVALID("004015", "auth.remember.me.invalid"),

    /**
     * 需要多因素认证
     */
    MFA_REQUIRED("004016", "auth.mfa.required"),

    /**
     * MFA验证码错误
     */
    MFA_CODE_ERROR("004017", "auth.mfa.code.error"),

    // ==================== 权限相关错误码 ====================
    /**
     * 暂无权限
     */
    NO_PERMISSION_ERROR("004030", "auth.no.permission"),

    /**
     * 会话已过期
     */
    SESSION_EXPIRED("004031", "auth.session.expired"),

    /**
     * Token无效
     */
    TOKEN_INVALID("004032", "auth.token.invalid"),

    /**
     * Token已过期
     */
    TOKEN_EXPIRED("004033", "auth.token.expired"),

    // ==================== 租户相关错误码 ====================
    /**
     * 租户不存在
     */
    TENANT_NOT_FOUND("004040", "auth.tenant.not.found"),

    /**
     * 租户编码已存在
     */
    TENANT_CODE_EXISTS("004041", "auth.tenant.code.exists"),

    /**
     * 租户名称已存在
     */
    TENANT_NAME_EXISTS("004042", "auth.tenant.name.exists"),

    // ==================== 角色相关错误码 ====================
    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND("004050", "auth.role.not.found"),

    /**
     * 角色编码已存在
     */
    ROLE_CODE_EXISTS("004051", "auth.role.code.exists"),

    /**
     * 角色名称已存在
     */
    ROLE_NAME_EXISTS("004052", "auth.role.name.exists"),

    /**
     * 父角色不存在
     */
    ROLE_PARENT_NOT_FOUND("004053", "auth.role.parent.not.found"),

    // ==================== 组织相关错误码 ====================
    /**
     * 组织不存在
     */
    ORGANIZATION_NOT_FOUND("004060", "auth.organization.not.found"),

    /**
     * 组织编码已存在
     */
    ORGANIZATION_CODE_EXISTS("004061", "auth.organization.code.exists"),

    /**
     * 组织名称已存在
     */
    ORGANIZATION_NAME_EXISTS("004062", "auth.organization.name.exists"),

    /**
     * 父组织不存在
     */
    ORGANIZATION_PARENT_NOT_FOUND("004063", "auth.organization.parent.not.found"),

    // ==================== 用户组相关错误码 ====================
    /**
     * 用户组不存在
     */
    USER_GROUP_NOT_FOUND("004070", "auth.user.group.not.found"),

    /**
     * 用户组编码已存在
     */
    USER_GROUP_CODE_EXISTS("004071", "auth.user.group.code.exists"),

    /**
     * 用户组名称已存在
     */
    USER_GROUP_NAME_EXISTS("004072", "auth.user.group.name.exists"),

    // ==================== 资源相关错误码 ====================
    /**
     * 资源不存在
     */
    RESOURCE_NOT_FOUND("004080", "auth.resource.not.found"),

    /**
     * 资源数据已存在
     */
    RESOURCE_EXISTS("004081", "auth.resource.exists"),

    // ==================== 参数相关错误码 ====================
    /**
     * 参数数据不存在
     */
    PARAMS_DATA_NOT_EXISTS("004090", "auth.params.data.not.exists"),

    /**
     * 参数校验失败
     */
    PARAMS_VALIDATION_FAILED("004091", "auth.params.validation.failed"),

    /**
     * 颜色格式无效
     */
    BRANDING_COLOR_FORMAT_INVALID("004092", "auth.branding.color.format.invalid"),

    /**
     * MFA启用时需要指定类型
     */
    MFA_ENABLED_REQUIRES_TYPE("004093", "auth.mfa.enabled.requires.type"),
    ;

    private final String code;

    private final String msg;

    AuthErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
