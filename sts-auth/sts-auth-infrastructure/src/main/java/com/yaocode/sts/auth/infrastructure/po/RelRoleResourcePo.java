package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 角色资源关联表
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 22:13
 */
@Data
@TableName("auth_tbl_rel_role_resource")
public class RelRoleResourcePo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 生效时间
     */
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    private LocalDateTime effectiveTo;

    /**
     * 权限效果：1-允许、0-拒绝
     */
    private Integer effect;

    /**
     * 优先级：数字越大优先级越高
     */
    private Integer priority;

    /**
     * 创建者id
     */
    private String createUserId;

    /**
     * 创建者名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
