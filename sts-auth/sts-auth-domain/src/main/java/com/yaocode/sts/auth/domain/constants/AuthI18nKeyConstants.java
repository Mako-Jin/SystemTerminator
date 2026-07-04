package com.yaocode.sts.auth.domain.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 权限认证国际化消息key
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 15:14
 */
public interface AuthI18nKeyConstants extends BasicI18nKeyConstants {

    // ========== 资源相关 ==========
    String RESOURCE_NAME_CANNOT_BE_BLANK = "auth.resource.name.cannot.be.blank";
    String CLIENT_VERSION_CANNOT_BE_LOWER_THAN_CURRENT = "auth.resource.version.cannot.be.lower";
    String RESOURCE_ALREADY_ENABLED = "auth.resource.already.enabled";
    String RESOURCE_ALREADY_DISABLED = "auth.resource.already.disabled";
    String RESOURCE_ALREADY_DEPRECATED = "auth.resource.already.deprecated";
    String RESOURCE_NOT_DEPRECATED = "auth.resource.not.deprecated";
    String RESOURCE_ALREADY_IN_WHITE_LIST = "auth.resource.already.in.whitelist";
    String RESOURCE_NOT_IN_WHITE_LIST = "auth.resource.not.in.whitelist";
    String CONTACT_NOT_BELONG_TO_RESOURCE = "auth.resource.contact.not.belong";
    String CONTACT_NOT_EXIST = "auth.resource.contact.not.exist";

    // ========== 角色相关 ==========
    String ROLE_NAME_CANNOT_BE_BLANK = "auth.role.name.cannot.be.blank";
    String ROLE_CANNOT_INHERIT_SELF = "auth.role.cannot.inherit.self";
    String RESOURCE_ALREADY_ASSIGNED_TO_ROLE = "auth.role.resource.already.assigned";
    String RESOURCE_NOT_ASSIGNED_TO_ROLE = "auth.role.resource.not.assigned";
    String USER_ALREADY_IN_ROLE = "auth.role.user.already.in";
    String USER_NOT_IN_ROLE = "auth.role.user.not.in";
    String USER_GROUP_ALREADY_IN_ROLE = "auth.role.user.group.already.in";

    // ========== 用户组相关 ==========
    String USER_GROUP_NAME_CANNOT_BE_BLANK = "auth.user.group.name.cannot.be.blank";
    String USER_GROUP_CANNOT_INHERIT_SELF = "auth.user.group.cannot.inherit.self";
    String USER_GROUP_ALREADY_ENABLED = "auth.user.group.already.enabled";
    String USER_GROUP_ALREADY_DISABLED = "auth.user.group.already.disabled";
    String USER_GROUP_DISABLED_CANNOT_ADD_USER = "auth.user.group.disabled.cannot.add.user";
    String USER_ALREADY_IN_USER_GROUP = "auth.user.group.user.already.in";
    String USER_NOT_IN_USER_GROUP = "auth.user.group.user.not.in";
    String USER_GROUP_DISABLED_CANNOT_ASSIGN_ROLE = "auth.user.group.disabled.cannot.assign.role";
    String ROLE_ALREADY_BOUND_TO_USER_GROUP = "auth.user.group.role.already.bound";
    String ROLE_NOT_BOUND_TO_USER_GROUP = "auth.user.group.role.not.bound";

    // ========== 租户相关 ==========
    String TENANT_NAME_CANNOT_BE_BLANK = "auth.tenant.name.cannot.be.blank";
    String BRAND_CONFIG_NOT_EXIST = "auth.tenant.brand.config.not.exist";
    String COMPANY_NOT_BELONG_TO_TENANT = "auth.tenant.company.not.belong";
    String COMPANY_NOT_EXIST = "auth.tenant.company.not.exist";
    String INSTANCE_NOT_BELONG_TO_TENANT = "auth.tenant.instance.not.belong";
    String INSTANCE_NOT_EXIST = "auth.tenant.instance.not.exist";
    String USER_ALREADY_IN_TENANT = "auth.tenant.user.already.in";
    String USER_NOT_IN_TENANT = "auth.tenant.user.not.in";
    String ORGANIZATION_ALREADY_IN_TENANT = "auth.tenant.organization.already.in";
    String ROLE_ALREADY_IN_TENANT = "auth.tenant.role.already.in";
    String USER_GROUP_ALREADY_IN_TENANT = "auth.tenant.user.group.already.in";

    // ========== 用户相关 ==========
    String USER_ALREADY_ACTIVATED = "auth.user.already.activated";
    String USER_ALREADY_DEACTIVATED = "auth.user.already.deactivated";
    String USER_ALREADY_LOCKED = "auth.user.already.locked";
    String USER_NOT_LOCKED = "auth.user.not.locked";
    String CONTACT_NOT_BELONG_TO_USER = "auth.user.contact.not.belong";
    String CREDENTIAL_NOT_BELONG_TO_USER = "auth.user.credential.not.belong";
    String EMPLOYMENT_NOT_BELONG_TO_USER = "auth.user.employment.not.belong";
    String EDUCATION_NOT_BELONG_TO_USER = "auth.user.education.not.belong";
    String EMERGENCY_CONTACT_NOT_BELONG_TO_USER = "auth.user.emergency.contact.not.belong";
    String USER_ALREADY_ASSOCIATED_WITH_TENANT = "auth.user.already.associated.tenant";
    String USER_NOT_ASSOCIATED_WITH_TENANT = "auth.user.not.associated.tenant";
    String USER_ALREADY_ASSOCIATED_WITH_ORGANIZATION = "auth.user.already.associated.organization";
    String USER_ALREADY_HAS_ROLE = "auth.user.already.has.role";
    String USER_NOT_HAS_ROLE = "auth.user.not.has.role";
    String USER_ALREADY_IN_USER_GROUP_2 = "auth.user.already.in.user.group";
    String USER_ALREADY_ASSOCIATED_WITH_DEVICE = "auth.user.already.associated.device";
    String USER_NOT_ASSOCIATED_WITH_DEVICE = "auth.user.not.associated.device";

    // ========== 客户端相关 ==========
    String AT_LEAST_ONE_GRANT_TYPE_REQUIRED = "auth.client.at.least.one.grant.type";
    String AUTHORIZATION_CODE_REQUIRES_REDIRECT_URI = "auth.client.authorization.code.requires.redirect.uri";
    String CLIENT_NAME_CANNOT_BE_BLANK = "auth.client.name.cannot.be.blank";
    String CLIENT_SECRET_CANNOT_BE_BLANK = "auth.client.secret.cannot.be.blank";
    String GRANT_TYPE_ALREADY_EXISTS = "auth.client.grant.type.already.exists";
    String ADD_AUTH_CODE_BEFORE_CONFIG_REDIRECT_URI = "auth.client.add.auth.code.before.redirect.uri";
    String GRANT_TYPE_NOT_EXIST = "auth.client.grant.type.not.exist";
    String AT_LEAST_ONE_GRANT_TYPE_MUST_REMAIN = "auth.client.at.least.one.grant.type.must.remain";
    String REDIRECT_URI_CANNOT_BE_BLANK = "auth.client.redirect.uri.cannot.be.blank";
    String REDIRECT_URI_NOT_EXIST = "auth.client.redirect.uri.not.exist";
    String AUTHORIZATION_CODE_REQUIRES_AT_LEAST_ONE_REDIRECT_URI = "auth.client.authorization.code.requires.at.least.one.redirect.uri";
    String SCOPE_CANNOT_BE_BLANK = "auth.client.scope.cannot.be.blank";
    String CLIENT_ALREADY_ENABLED = "auth.client.already.enabled";
    String CLIENT_ALREADY_DISABLED = "auth.client.already.disabled";

    // ========== 组织相关 ==========
    String ORGANIZATION_NAME_CANNOT_BE_BLANK = "auth.organization.name.cannot.be.blank";
    String USER_ALREADY_IN_ORGANIZATION = "auth.organization.user.already.in";
    String USER_NOT_IN_ORGANIZATION = "auth.organization.user.not.in";
    String USER_GROUP_ALREADY_ASSOCIATED_WITH_ORGANIZATION = "auth.organization.user.group.already.associated";
    String ROLE_ALREADY_ASSOCIATED_WITH_ORGANIZATION = "auth.organization.role.already.associated";

    // ========== 登录尝试相关 ==========
    String PASSWORD_ERROR_EXCEEDED_LIMIT = "auth.login.attempt.password.error.exceeded.limit";

    // ========== RememberMe Token相关 ==========
    String NEW_EXPIRE_TIME_CANNOT_BE_EARLIER = "auth.remember.me.token.expire.time.cannot.be.earlier";

    // ========== 角色约束相关 ==========
    String MUTEX_CONSTRAINT_REQUIRES_AT_LEAST_TWO_ROLES = "auth.role.constraint.mutex.requires.at.least.two.roles";
    String CARDINALITY_CONSTRAINT_REQUIRES_AT_LEAST_ONE_ROLE = "auth.role.constraint.cardinality.requires.at.least.one.role";
    String MAX_ASSIGNMENT_COUNT_AT_LEAST_ONE = "auth.role.constraint.max.assignment.count.at.least.one";
    String ROLE_ALREADY_IN_CONSTRAINT = "auth.role.constraint.role.already.in";
    String MUTEX_CONSTRAINT_CANNOT_REMOVE_INSUFFICIENT_ROLES = "auth.role.constraint.mutex.cannot.remove.insufficient.roles";
    String CARDINALITY_CONSTRAINT_CANNOT_REMOVE_INSUFFICIENT_ROLES = "auth.role.constraint.cardinality.cannot.remove.insufficient.roles";

    // ========== 用户就业相关 ==========
    String EMPLOYMENT_DATE_CANNOT_BE_BLANK = "auth.user.employment.date.cannot.be.blank";
    String ONLY_PROBATION_EMPLOYEE_CAN_BE_REGULAR = "auth.user.employment.only.probation.can.be.regular";
    String RESIGN_DATE_CANNOT_BE_BLANK = "auth.user.employment.resign.date.cannot.be.blank";
    String RESIGN_DATE_CANNOT_BE_EARLIER_THAN_EMPLOYMENT_DATE = "auth.user.employment.resign.date.cannot.be.earlier";

    // ========== 实例相关 ==========
    String NEW_VERSION_MUST_BE_HIGHER = "auth.instance.version.must.be.higher";
    String UNKNOWN_INSTANCE_STATUS = "auth.instance.unknown.status";

    // ========== 公司相关 ==========
    String COMPANY_NAME_CANNOT_BE_BLANK = "auth.company.name.cannot.be.blank";

    // ========== 用户密保问题相关 ==========
    String QUESTION_TYPE_OR_CUSTOM_QUESTION_REQUIRED = "auth.user.secret.question.type.or.custom.required";
    String ANSWER_CANNOT_BE_BLANK = "auth.user.secret.question.answer.cannot.be.blank";

    // ========== 用户紧急联系人相关 ==========
    String EMERGENCY_CONTACT_NAME_CANNOT_BE_BLANK = "auth.user.emergency.contact.name.cannot.be.blank";
    String EMERGENCY_CONTACT_RELATIONSHIP_CANNOT_BE_BLANK = "auth.user.emergency.contact.relationship.cannot.be.blank";

    // ========== 用户教育相关 ==========
    String ENROLLMENT_DATE_CANNOT_BE_LATER_THAN_GRADUATION = "auth.user.education.enrollment.date.cannot.be.later";
    String GRADUATION_DATE_CANNOT_BE_EARLIER_THAN_ENROLLMENT = "auth.user.education.graduation.date.cannot.be.earlier";

    // ========== 用户档案相关 ==========
    String DISPLAY_NAME_CANNOT_BE_BLANK = "auth.user.profile.display.name.cannot.be.blank";
    String BIRTH_DATE_CANNOT_BE_LATER_THAN_NOW = "auth.user.profile.birth.date.cannot.be.later";

    // ========== 用户组实体相关 ==========
    String USER_GROUP_NOT_ACTIVATED_CANNOT_ASSIGN_USER = "auth.user.group.entity.not.activated.cannot.assign.user";
    String USER_GROUP_ALREADY_CONTAINS_USER = "auth.user.group.entity.already.contains.user";
    String USER_GROUP_NOT_ACTIVATED_CANNOT_BIND_ROLE = "auth.user.group.entity.not.activated.cannot.bind.role";
    String USER_GROUP_ALREADY_CONTAINS_ROLE = "auth.user.group.entity.already.contains.role";

    // ========== 值对象 - 密码相关 ==========
    String PASSWORD_CANNOT_BE_BLANK = "auth.value.object.password.cannot.be.blank";
    String PASSWORD_VALUE_CANNOT_BE_BLANK = "auth.value.object.password.value.cannot.be.blank";
    String PASSWORD_EXPIRY_DAYS_MINIMUM_ONE = "auth.value.object.password.policy.expiry.days.minimum.one";
    String PASSWORD_MIN_LENGTH_MINIMUM_SIX = "auth.value.object.password.policy.min.length.minimum.six";
    String PASSWORD_HISTORY_COUNT_CANNOT_BE_NEGATIVE = "auth.value.object.password.policy.history.count.cannot.be.negative";
    String PASSWORD_RULE_CHECK_ERROR = "auth.password.rule.check.error";

    // ========== 值对象 - 用户名相关 ==========
    String USERNAME_CANNOT_BE_BLANK = "auth.value.object.username.cannot.be.blank";
    String USERNAME_RULE_CHECK_ERROR = "auth.username.rule.check.error";

    // ========== 值对象 - 邮箱相关 ==========
    String EMAIL_CANNOT_BE_BLANK = "auth.value.object.email.cannot.be.blank";
    String EMAIL_TOO_LONG = "auth.value.object.email.too.long";
    String EMAIL_FORMAT_INVALID = "auth.value.object.email.format.invalid";

    // ========== 值对象 - 手机号相关 ==========
    String PHONE_NUMBER_CANNOT_BE_BLANK = "auth.value.object.phone.number.cannot.be.blank";
    String PHONE_NUMBER_FORMAT_INVALID = "auth.value.object.phone.number.format.invalid";

    // ========== 值对象 - 资源相关 ==========
    String RESOURCE_VALUE_CANNOT_BE_BLANK = "auth.value.object.resource.value.cannot.be.blank";

    // ========== 值对象 - 客户端相关 ==========
    String CLIENT_ID_CANNOT_BE_BLANK = "auth.value.object.client.id.cannot.be.blank";

    // ========== 值对象 - MFA配置相关 ==========
    String MFA_ENABLED_MUST_SPECIFY_TYPE = "auth.value.object.mfa.enabled.must.specify.type";

    // ========== 值对象 - 会话配置相关 ==========
    String SESSION_TIMEOUT_MINIMUM_60_SECONDS = "auth.value.object.session.timeout.minimum.60.seconds";
    String REMEMBER_ME_MAX_DAYS_MINIMUM_ONE = "auth.value.object.remember.me.max.days.minimum.one";

    // ========== 值对象 - 品牌配置相关 ==========
    String COLOR_FORMAT_INVALID = "auth.value.object.branding.color.format.invalid";

    // ========== 值对象 - 设备指纹相关 ==========
    String DEVICE_FINGERPRINT_CANNOT_BE_BLANK = "auth.value.object.device.fingerprint.cannot.be.blank";
    String DEVICE_FINGERPRINT_FORMAT_INVALID = "auth.value.object.device.fingerprint.format.invalid";

    // ========== 值对象 - 租户编码相关 ==========
    String TENANT_CODE_CANNOT_BE_BLANK = "auth.value.object.tenant.code.cannot.be.blank";

    // ========== 值对象 - 角色编码相关 ==========
    String ROLE_CODE_CANNOT_BE_BLANK = "auth.value.object.role.code.cannot.be.blank";

    // ========== 值对象 - 组织编码相关 ==========
    String ORGANIZATION_CODE_CANNOT_BE_BLANK = "auth.value.object.organization.code.cannot.be.blank";

    // ========== 值对象 - 用户组编码相关 ==========
    String USER_GROUP_CODE_CANNOT_BE_BLANK = "auth.value.object.user.group.code.cannot.be.blank";

    // ========== 值对象 - 编码格式相关 ==========
    String CODE_FORMAT_INVALID = "auth.value.object.code.format.invalid";

    // ========== 值对象 - IP地址相关 ==========
    String IP_ADDRESS_CANNOT_BE_BLANK = "auth.value.object.ip.address.cannot.be.blank";
    String IP_ADDRESS_FORMAT_INVALID = "auth.value.object.ip.address.format.invalid";

    // ========== 值对象 - 版本号相关 ==========
    String VERSION_CANNOT_BE_NEGATIVE = "auth.value.object.version.cannot.be.negative";
    String VERSION_FORMAT_INVALID = "auth.value.object.version.format.invalid";

    // ========== 值对象 - 时间范围相关 ==========
    String DATE_RANGE_FROM_CANNOT_BE_LATER_THAN_TO = "auth.value.object.date.range.from.cannot.be.later.than.to";

    // ========== 值对象 - 验证码相关 ==========
    String VERIFY_CODE_CANNOT_BE_BLANK = "auth.value.object.verify.code.cannot.be.blank";
    String VERIFY_CODE_FORMAT_INVALID = "auth.value.object.verify.code.format.invalid";

    // ========== 值对象 - 认证凭证相关 ==========
    String CLIENT_ID_CANNOT_BE_BLANK = "auth.value.object.client.id.cannot.be.blank";

    // ========== 标识符相关 ==========
    String IDENTIFIER_VALUE_CANNOT_BE_NULL = "auth.identifier.value.null";

    // ========== 值对象 - 用户就业相关 ==========
    String EMPLOYMENT_ENTRY_DATE_CANNOT_BE_BLANK = "auth.value.object.employment.entry.date.cannot.be.blank";
    String ONLY_PROBATION_EMPLOYEE_CAN_REGULARIZE = "auth.value.object.employment.only.probation.can.regularize";
    String EMPLOYMENT_QUIT_DATE_CANNOT_BE_BLANK = "auth.value.object.employment.quit.date.cannot.be.blank";
    String QUIT_DATE_CANNOT_BE_EARLIER_THAN_ENTRY_DATE = "auth.value.object.employment.quit.date.cannot.be.earlier.than.entry";

    // ========== 验证码规则相关 ==========
    String CAPTCHA_CODE_DESCRIPTION = "auth.verify.code.captcha.description";
    String EMAIL_CODE_DESCRIPTION = "auth.verify.code.email.description";
    String SMS_CODE_DESCRIPTION = "auth.verify.code.sms.description";

    // ========== Token相关 ==========
    String TOKEN_CANNOT_BE_BLANK = "auth.token.cannot.be.blank";
    String TOKEN_PARSE_FAILED = "auth.token.parse.failed";
    String TOKEN_TYPE_MISMATCH = "auth.token.type.mismatch";
    String TOKEN_EXPIRED = "auth.token.expired";

    // ========== 认证相关 ==========
    String AUTHENTICATION_FAILED_USER_NOT_FOUND = "auth.authentication.failed.user.not.found";
    String AUTH_CREDENTIAL_CANNOT_BE_BLANK = "auth.credential.cannot.be.blank";
    String USER_ALREADY_DISABLED = "auth.user.already.disabled";

    // ========== RememberMe认证相关 ==========
    String REMEMBER_ME_TOKEN_NULL_OR_BLANK = "auth.remember.me.token.null.or.blank";
    String REMEMBER_ME_AUTH_FAILED_INVALID_TOKEN = "auth.remember.me.auth.failed.invalid.token";
    String REMEMBER_ME_AUTH_FAILED_TOKEN_EXPIRED = "auth.remember.me.auth.failed.token.expired";
    String REMEMBER_ME_AUTH_FAILED_USER_ID_NULL = "auth.remember.me.auth.failed.user.id.null";
    String REMEMBER_ME_AUTH_FAILED_TOKEN_REVOKED = "auth.remember.me.auth.failed.token.revoked";
    String REMEMBER_ME_AUTH_FAILED_USER_NOT_FOUND = "auth.remember.me.auth.failed.user.not.found";

    // ========== 租户相关 ==========
    String TENANT_NOT_ALLOW_REGISTER = "auth.tenant.not.allow.register";
    String TENANT_NOT_ALLOW_ADD_USER = "auth.tenant.not.allow.add.user";
    String DEFAULT_TENANT_NOT_EXIST = "auth.tenant.default.not.exist";

    // ========== 数据校验相关 ==========
    String TENANT_NOT_EXIST = "auth.tenant.not.exist";
    String USER_GROUP_NOT_EXIST = "auth.user.group.not.exist";
    String ORGANIZATION_NOT_EXIST = "auth.organization.not.exist";
    String ROLE_NOT_EXIST = "auth.role.not.exist";

    // ========== 数据已存在相关 ==========
    String DATA_ALREADY_EXISTS = "auth.data.is.exists";
    String PARAMS_DATA_NOT_EXISTS = "auth.params.data.not.exists";

    // ========== 认证方式相关 ==========
    String UNSUPPORTED_GRANT_TYPE = "auth.grant.type.unsupported";

    // ========== 安全相关 ==========
    String BLACKLIST_BLOCKED = "auth.security.blacklist.blocked";
    String RULE_MATCHED = "auth.security.rule.matched";
}