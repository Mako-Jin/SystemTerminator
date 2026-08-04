package com.yaocode.sts.common.domain.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 领域消息key
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 18:54
 */
public interface DomainI18nKeyConstants extends BasicI18nKeyConstants {

    String TENANT_ID_NULL = "auth.tenant.id.null";
    String USER_ID_NULL = "auth.user.id.null";

    String TENANT_INFO_NOT_FOUND = "tenant.info.not.found";
    String COMMON_DOMAIN_IDENTIFIER_VALUE_NULL = "common.domain.identifier.value.null";

    // ========== 值对象 - IP地址相关 ==========
    String IP_ADDRESS_CANNOT_BE_BLANK = "auth.value.object.ip.address.cannot.be.blank";
    String IP_ADDRESS_FORMAT_INVALID = "auth.value.object.ip.address.format.invalid";

    // ========== 值对象 - 租户编码相关 ==========
    String TENANT_CODE_CANNOT_BE_BLANK = "auth.value.object.tenant.code.cannot.be.blank";
    // ========== 值对象 - 编码格式相关 ==========
    String CODE_FORMAT_INVALID = "auth.value.object.code.format.invalid";

    // ========== 值对象 - 用户名相关 ==========
    String USERNAME_CANNOT_BE_BLANK = "auth.value.object.username.cannot.be.blank";
    String USERNAME_RULE_CHECK_ERROR = "auth.username.rule.check.error";

}
