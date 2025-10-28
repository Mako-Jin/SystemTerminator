package com.yaocode.sts.auth.application.dto;

import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 20:55
 */
@Data
public class RoleInfoDto {

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;
    /**
     * 父id
     */
    private String parentId;

    /**
     * 租户id
     */
    private String tenantId;

}
