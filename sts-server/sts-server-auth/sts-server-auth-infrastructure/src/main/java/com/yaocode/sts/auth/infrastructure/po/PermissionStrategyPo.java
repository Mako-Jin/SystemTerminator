package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 权限策略模板表（三元管理）
 * 用于定义可复用的权限分配模板
 * @author: Jin-LiangBo
 */
@Data
@TableName("auth_tbl_permission_strategy")
@EqualsAndHashCode(callSuper = true)
public class PermissionStrategyPo extends BasePo {

    @TableId
    private String strategyId;

    /**
     * 策略名称
     */
    private String strategyName;

    /**
     * 策略编码
     */
    private String strategyCode;

    /**
     * 策略描述
     */
    private String strategyDesc;

    /**
     * 策略类型：ROLE_BASED-角色型、GROUP_BASED-组型、USER_BASED-用户型
     */
    private Integer strategyType;

    /**
     * 关联的角色ID列表（逗号分隔）
     */
    private String roleIds;

    /**
     * 关联的资源ID列表（逗号分隔）
     */
    private String resourceIds;

    /**
     * 优先级（数字越小优先级越高）
     */
    private Integer priority;

    /**
     * 是否默认策略
     */
    private Integer isDefault;

    /**
     * 适用场景（JSON格式，如部门、职位等条件）
     */
    private String conditions;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 状态：0-禁用、1-启用
     */
    private Integer status;

}