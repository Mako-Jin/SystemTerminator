package com.yaocode.sts.auth.infrastructure.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;

/**
 * 用户企业属性持久化对象
 * 记录员工在企业中的职位、部门、经理等信息
 */
@Data
@TableName("auth_tbl_user_enterprise")
@EqualsAndHashCode(callSuper = true)
public class UserEnterprisePo extends BasePo {

    @TableId
    private String id;

    /**
     * 关联用户ID
     */
    private String userId;

    /**
     * 员工编号
     */
    private String employeeNumber;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 组织
     */
    private String organization;

    /**
     * 事业部
     */
    private String division;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String department;

    /**
     * 职位
     */
    private String title;

    /**
     * 经理ID
     */
    private String managerId;

    /**
     * 经理姓名
     */
    private String manager;

    /**
     * 助理ID
     */
    private String assistantId;

    /**
     * 助理姓名
     */
    private String assistant;
}
