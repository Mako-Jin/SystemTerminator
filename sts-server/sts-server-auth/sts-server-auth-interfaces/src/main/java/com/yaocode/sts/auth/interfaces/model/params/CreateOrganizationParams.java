package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 12:57
 */
@Data
public class CreateOrganizationParams {

    /**
     * 组织编码
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_ORGANIZATION_CODE_CANNOT_BE_BLANK)
    private String organizationCode;
    /**
     * 组织名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_ORGANIZATION_NAME_CANNOT_BE_BLANK)
    private String organizationName;

    @CheckXss
    @CheckSqlInjection
    private String organizationDesc;

    private Integer sort;

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
