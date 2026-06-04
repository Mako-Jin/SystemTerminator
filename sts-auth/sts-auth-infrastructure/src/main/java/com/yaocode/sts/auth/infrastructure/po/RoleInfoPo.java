package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

/**
 * 角色持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:08
 */
@Data
@TableName("auth_tbl_role")
@EqualsAndHashCode(callSuper = true)
public class RoleInfoPo extends BasePo {

    /**
     * 角色id
     */
    @TableId
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色描述
     */
    private String roleDesc;
    /**
     * 父id
     */
    private String parentId;

    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 是否是租户下的默认权限
     */
    private Integer isDefault;

    /**
     * 角色分类：dynamic-动态角色；static-静态角色；app-应用角色
     */
    private String category;

    /**
     * 过滤条件（动态角色使用）
     */
    private String filters;

    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;

}
