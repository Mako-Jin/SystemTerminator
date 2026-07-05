package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 20:57
 */
@Data
@Builder
public class RoleInfoVo {
    private String roleId;
    private String roleCode;
    private String roleName;
    private String roleDesc;
    private String parentId;
    private String tenantId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
