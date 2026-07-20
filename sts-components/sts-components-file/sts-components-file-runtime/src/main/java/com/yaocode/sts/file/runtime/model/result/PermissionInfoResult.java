package com.yaocode.sts.file.runtime.model.result;

import com.yaocode.sts.file.runtime.model.dto.PermissionItemDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 权限信息结果
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Builder
public class PermissionInfoResult {
    /** 文件ID */
    private String fileId;

    /** 文件名 */
    private String fileName;

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

    /** 权限设置时间 */
    private LocalDateTime permissionTime;
}
