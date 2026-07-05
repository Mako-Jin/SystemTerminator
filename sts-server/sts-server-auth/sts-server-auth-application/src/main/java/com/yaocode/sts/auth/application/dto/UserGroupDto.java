package com.yaocode.sts.auth.application.dto;

import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:42
 */
@Data
public class UserGroupDto {

    /**
     * 用户组id
     */
    private String userGroupId;
    /**
     * 用户组编码
     */
    private String userGroupCode;

    /**
     * 用户组名
     */
    private String userGroupName;
    /**
     * 用户组描述
     */
    private String userGroupDesc;
    /**
     * 父id
     */
    private String parentId;

    /**
     * 租户id
     */
    private String tenantId;

}
