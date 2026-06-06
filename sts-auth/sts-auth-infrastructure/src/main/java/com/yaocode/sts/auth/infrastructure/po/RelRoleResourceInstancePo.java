package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 对象级权限关联表
 * 支持更细粒度的资源实例级权限控制
 * @author: Jin-LiangBo
 */
@Data
@TableName("auth_tbl_rel_role_resource_instance")
public class RelRoleResourceInstancePo {

    @TableId
    private Long relId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 资源ID
     */
    private String resourceId;

    /**
     * 资源实例ID（如：特定文档ID、特定订单ID）
     */
    private String instanceId;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 操作权限（逗号分隔：READ,WRITE,DELETE,APPROVE等）
     */
    private String actions;

    /**
     * 数据过滤条件（JSON格式，如部门ID、时间范围等）
     */
    private String filters;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveTime;

    /**
     * 失效时间
     */
    private LocalDateTime expireTime;

    /**
     * 租户ID
     */
    private String tenantId;

    private String createUserId;
    private String createUserName;
    private LocalDateTime createTime;

}
