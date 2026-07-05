package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 13:00
 */
@Data
public class CreateUserGroupParams {

    /**
     * 用户组编码
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_USER_GROUP_CODE_CANNOT_BE_BLANK)
    private String userGroupCode;
    /**
     * 用户组名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_USER_GROUP_NAME_CANNOT_BE_BLANK)
    private String userGroupName;

    @CheckXss
    @CheckSqlInjection
    private String userGroupDesc;

    @CheckXss
    @CheckSqlInjection
    private String parentId;
    /**
     * 租户id
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_PARAMS_VALIDATION_TENANT_ID_REQUIRED)
    private String tenantId;

}