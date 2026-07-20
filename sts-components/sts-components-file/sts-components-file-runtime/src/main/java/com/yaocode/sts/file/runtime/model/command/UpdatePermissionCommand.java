package com.yaocode.sts.file.runtime.model.command;

import com.yaocode.sts.file.runtime.model.dto.PermissionItemDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 更新权限命令
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class UpdatePermissionCommand {
    /** 文件ID */
    private String fileId;

    /** 是否公开 */
    private Boolean isPublic;

    /** 允许的用户列表 */
    private List<PermissionItemDto> allowedUsers;

    /** 允许的角色列表 */
    private List<PermissionItemDto> allowedRoles;

    /** 允许的组列表 */
    private List<PermissionItemDto> allowedGroups;

    /** 权限级别（0-私有, 1-只读, 2-读写, 3-完全控制） */
    private Integer permissionLevel;

    /** 备注 */
    private String comment;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private String userId;

    /** 用户名 */
    private String userName;
}
