package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增用户参数
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:57
 */
@Data
public class CreateUserParams {

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_PARAMS_VALIDATION_TENANT_ID_REQUIRED)
    private String tenantId;

    @CheckXss
    @CheckSqlInjection
    private String orgId;

    @CheckXss
    @CheckSqlInjection
    private String userGroupId;

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_VALUE_OBJECT_USERNAME_CANNOT_BE_BLANK)
    private String username;

    @CheckXss
    @CheckSqlInjection
    private String email;

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_VALUE_OBJECT_PHONE_NUMBER_CANNOT_BE_BLANK)
    private String phoneNum;

    @CheckXss
    @CheckSqlInjection
    private String nickname;
}
