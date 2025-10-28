package com.yaocode.sts.auth.interfaces.model.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 13:00
 */
@Data
public class CreateRoleParams {

    /**
     * 组织编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;
    /**
     * 组织名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    private String roleDesc;

    private String parentId;
    /**
     * 租户id
     */
    @NotBlank(message = "租户标识不能为空")
    private String tenantId;

}
