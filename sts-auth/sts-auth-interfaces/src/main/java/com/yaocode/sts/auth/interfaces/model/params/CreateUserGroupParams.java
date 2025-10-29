package com.yaocode.sts.auth.interfaces.model.params;

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
    @NotBlank(message = "用户组编码不能为空")
    private String userGroupCode;
    /**
     * 用户组名称
     */
    @NotBlank(message = "用户组名称不能为空")
    private String userGroupName;

    private String userGroupDesc;

    private String parentId;
    /**
     * 租户id
     */
    @NotBlank(message = "租户标识不能为空")
    private String tenantId;

}
