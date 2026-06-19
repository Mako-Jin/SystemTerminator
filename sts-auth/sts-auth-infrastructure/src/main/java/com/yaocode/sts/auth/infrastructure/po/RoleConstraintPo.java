package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

/**
 * 角色约束表（职责分离）
 * 用于定义互斥角色组，防止权限滥用
 * @author: Jin-LiangBo
 */
@Data
@TableName("auth_tbl_role_constraint")
@EqualsAndHashCode(callSuper = true)
public class RoleConstraintPo extends BasePo {

    @TableId
    private String constraintId;

    /**
     * 约束名称
     */
    private String constraintName;

    /**
     * 约束描述
     */
    private String constraintDesc;

    /**
     * 约束类型：MUTEX-互斥约束、CARDINALITY-基数约束
     */
    private Integer constraintType;

    /**
     * 互斥角色列表（逗号分隔）
     */
    private String roleIds;

    /**
     * 最多可分配数量（基数约束使用）
     */
    private Integer maxAssign;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 状态：0-禁用、1-启用
     */
    private Integer status;

}