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
@TableName("auth_tbl_role_info")
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
     * 继承的策略 枚举值
     * 完全继承：子角色完全继承父角色的所有权限
     * 增量继承：子角色在父角色权限基础上增加自己的权限
     * 覆盖继承：子角色权限覆盖父角色权限
     * 无继承：子角色不继承父角色任何权限
     */
    private Integer inheritStrategy;

    /**
     * 角色分类：dynamic-动态角色；static-静态角色；app-应用角色
     */
    private Integer category;

    /**
     * 过滤条件（动态角色使用）
     * 动态角色过滤条件（JSON格式），category=DYNAMIC时使用
     * 示例： {"deptId": "001", "position": "MANAGER"}
     */
    private String filters;

    /**
     * 状态：0-禁用；1-启用
     */
    private Integer status;
    /**
     * 角色层级
     */
    private Integer roleLevel;

}
