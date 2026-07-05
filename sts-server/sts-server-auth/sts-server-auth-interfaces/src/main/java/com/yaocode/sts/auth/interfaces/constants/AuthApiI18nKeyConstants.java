package com.yaocode.sts.auth.interfaces.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

public interface AuthApiI18nKeyConstants extends BasicI18nKeyConstants {

    // ==================== 用户相关 ====================
    String AUTH_USER_NOT_FOUND = "{auth.user.not.found}";
    String AUTH_USERNAME_EXISTS = "{auth.username.exists}";
    String AUTH_USER_EMAIL_EXISTS = "{auth.user.email.exists}";
    String AUTH_USER_PHONE_EXISTS = "{auth.user.phone.exists}";
    String AUTH_USER_ALREADY_ACTIVATED = "{auth.user.already.activated}";
    String AUTH_USER_ALREADY_DEACTIVATED = "{auth.user.already.deactivated}";
    String AUTH_USER_ALREADY_LOCKED = "{auth.user.already.locked}";
    String AUTH_USER_NOT_LOCKED = "{auth.user.not.locked}";

    // ==================== 认证相关 ====================
    String AUTH_AUTHENTICATION_ERROR = "{auth.authentication.error}";
    String AUTH_PASSWORD_ERROR = "{auth.password.error}";
    String AUTH_ACCOUNT_LOCKED = "{auth.account.locked}";
    String AUTH_CAPTCHA_REQUIRED = "{auth.captcha.required}";
    String AUTH_CAPTCHA_ERROR = "{auth.captcha.error}";
    String AUTH_MFA_REQUIRED = "{auth.mfa.required}";
    String AUTH_MFA_CODE_ERROR = "{auth.mfa.code.error}";
    String AUTH_NO_PERMISSION = "{auth.no.permission}";
    String AUTH_SESSION_EXPIRED = "{auth.session.expired}";
    String AUTH_TOKEN_INVALID = "{auth.token.invalid}";
    String AUTH_TOKEN_EXPIRED = "{auth.token.expired}";
    String AUTH_IDENTIFIER_VALUE_NULL = "{auth.identifier.value.null}";
    String AUTH_PASSWORD_RULE_CHECK_ERROR = "{auth.password.rule.check.error}";
    String AUTH_USERNAME_RULE_CHECK_ERROR = "{auth.username.rule.check.error}";

    // ==================== 租户相关 ====================
    String AUTH_TENANT_NAME_CANNOT_BE_BLANK = "{auth.tenant.name.cannot.be.blank}";
    String AUTH_TENANT_CODE_CANNOT_BE_BLANK = "{auth.value.object.tenant.code.cannot.be.blank}";
    String AUTH_TENANT_NOT_EXIST = "{auth.tenant.not.exist}";
    String AUTH_TENANT_CODE_EXISTS = "{auth.tenant.code.exists}";
    String AUTH_TENANT_NAME_EXISTS = "{auth.tenant.name.exists}";
    String AUTH_TENANT_NOT_ALLOW_REGISTER = "{auth.tenant.not.allow.register}";
    String AUTH_TENANT_NOT_ALLOW_ADD_USER = "{auth.tenant.not.allow.add.user}";

    // ==================== 角色相关 ====================
    String AUTH_ROLE_NAME_CANNOT_BE_BLANK = "{auth.role.name.cannot.be.blank}";
    String AUTH_ROLE_CODE_CANNOT_BE_BLANK = "{auth.value.object.role.code.cannot.be.blank}";
    String AUTH_ROLE_NOT_EXIST = "{auth.role.not.exist}";
    String AUTH_ROLE_CODE_EXISTS = "{auth.role.code.exists}";
    String AUTH_ROLE_NAME_EXISTS = "{auth.role.name.exists}";

    // ==================== 组织相关 ====================
    String AUTH_ORGANIZATION_NAME_CANNOT_BE_BLANK = "{auth.organization.name.cannot.be.blank}";
    String AUTH_ORGANIZATION_CODE_CANNOT_BE_BLANK = "{auth.value.object.organization.code.cannot.be.blank}";
    String AUTH_ORGANIZATION_NOT_EXIST = "{auth.organization.not.exist}";
    String AUTH_ORGANIZATION_CODE_EXISTS = "{auth.organization.code.exists}";
    String AUTH_ORGANIZATION_NAME_EXISTS = "{auth.organization.name.exists}";

    // ==================== 用户组相关 ====================
    String AUTH_USER_GROUP_NAME_CANNOT_BE_BLANK = "{auth.user.group.name.cannot.be.blank}";
    String AUTH_USER_GROUP_CODE_CANNOT_BE_BLANK = "{auth.value.object.user.group.code.cannot.be.blank}";
    String AUTH_USER_GROUP_NOT_EXIST = "{auth.user.group.not.exist}";
    String AUTH_USER_GROUP_CODE_EXISTS = "{auth.user.group.code.exists}";
    String AUTH_USER_GROUP_NAME_EXISTS = "{auth.user.group.name.exists}";

    // ==================== 资源相关 ====================
    String AUTH_RESOURCE_NAME_CANNOT_BE_BLANK = "{auth.resource.name.cannot.be.blank}";
    String AUTH_RESOURCE_VALUE_CANNOT_BE_BLANK = "{auth.value.object.resource.value.cannot.be.blank}";
    String AUTH_RESOURCE_NOT_FOUND = "{auth.resource.not.found}";
    String AUTH_RESOURCE_EXISTS = "{auth.resource.exists}";

    // ==================== 参数校验相关 ====================
    String AUTH_PARAMS_DATA_NOT_EXISTS = "{auth.params.data.not.exists}";
    String AUTH_PARAMS_VALIDATION_FAILED = "{auth.params.validation.failed}";
    String AUTH_PARAMS_VALIDATION_BOOLEAN_VALUE = "{auth.params.validation.boolean.value}";
    String AUTH_PARAMS_VALIDATION_TENANT_ID_REQUIRED = "{auth.params.validation.tenant.id.required}";
    String AUTH_PARAMS_VALIDATION_RESOURCE_TYPE_REQUIRED = "{auth.params.validation.resource.type.required}";

    // ==================== DTO校验相关 ====================
    String AUTH_BRANDING_COLOR_FORMAT_INVALID = "{auth.branding.color.format.invalid}";
    String AUTH_MFA_ENABLED_REQUIRES_TYPE = "{auth.mfa.enabled.requires.type}";

    // ==================== 值对象相关 ====================
    String AUTH_VALUE_OBJECT_USERNAME_CANNOT_BE_BLANK = "{auth.value.object.username.cannot.be.blank}";
    String AUTH_VALUE_OBJECT_PHONE_NUMBER_CANNOT_BE_BLANK = "{auth.value.object.phone.number.cannot.be.blank}";
    String AUTH_VALUE_OBJECT_GRANT_TYPE_CANNOT_BE_BLANK = "{auth.value.object.grant.type.cannot.be.blank}";

    // ==================== RememberMe相关 ====================
    String AUTH_REMEMBER_ME_INVALID = "{auth.remember.me.invalid}";

    // ==================== 登录相关 ====================
    String AUTH_VALUE_OBJECT_PASSWORD_CANNOT_BE_BLANK = "{auth.value.object.password.cannot.be.blank}";
    String AUTH_VALUE_OBJECT_VERIFY_CODE_CANNOT_BE_BLANK = "{auth.value.object.verify.code.cannot.be.blank}";
}