package com.yaocode.sts.auth.interfaces.model.params;

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
    @NotBlank(message = "组织编码不能为空")
    private String organizationCode;
    /**
     * 组织名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "组织名称不能为空")
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
    @NotBlank(message = "租户标识不能为空")
    private String tenantId;

}
