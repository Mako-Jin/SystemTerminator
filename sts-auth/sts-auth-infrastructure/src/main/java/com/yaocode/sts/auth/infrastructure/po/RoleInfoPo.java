package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 创建者id
     */
    private String parentId;

}
