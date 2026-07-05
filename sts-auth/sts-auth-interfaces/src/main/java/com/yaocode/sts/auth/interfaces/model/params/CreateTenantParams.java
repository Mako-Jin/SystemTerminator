package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 新增租户参数类
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 22:41
 */
@Data
public class CreateTenantParams {
    /**
     * 租户名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_TENANT_NAME_CANNOT_BE_BLANK)
    private String tenantName;
    /**
     * 租户编码
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_TENANT_CODE_CANNOT_BE_BLANK)
    private String tenantCode;
    /**
     * 租户描述
     */
    @CheckXss
    @CheckSqlInjection
    private String tenantDesc;
    /**
     * 是否允许注册新用户
     */
    @Range(min = 0, max = 1, message = AuthApiI18nKeyConstants.AUTH_PARAMS_VALIDATION_BOOLEAN_VALUE)
    private Integer allowRegister = YesNoEnums.NO.getCode();
    /**
     * 是否允许新增新用户
     */
    @Range(min = 0, max = 1, message = AuthApiI18nKeyConstants.AUTH_PARAMS_VALIDATION_BOOLEAN_VALUE)
    private Integer allowAdd = YesNoEnums.YES.getCode();
    /**
     * 父id
     */
    @CheckXss
    @CheckSqlInjection
    private String parentId;

}
