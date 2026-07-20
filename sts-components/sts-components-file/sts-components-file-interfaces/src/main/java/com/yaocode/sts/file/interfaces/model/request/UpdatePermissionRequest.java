package com.yaocode.sts.file.interfaces.model.request;

import com.yaocode.sts.file.interfaces.model.response.PermissionItemResponse;
import lombok.Data;

import java.util.List;

/**
 * 更新权限请求
 */
@Data
public class UpdatePermissionRequest {
    /**
     * 是否公开
     */
    private Boolean isPublic;
    /**
     * 允许的用户列表
     */
    private List<PermissionItemResponse> allowedUsers;
    /**
     * 允许的角色列表
     */
    private List<PermissionItemResponse> allowedRoles;
    /**
     * 允许的用户组列表
     */
    private List<PermissionItemResponse> allowedGroups;
    /**
     * 权限级别
     */
    private Integer permissionLevel;
    /**
     * 备注说明
     */
    private String comment;
}