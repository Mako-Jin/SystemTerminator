package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@TableName("auth_tbl_user_employment")
@EqualsAndHashCode(callSuper = true)
public class UserEmploymentPo extends BasePo {

    @TableId
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 组织/公司
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
     * 员工编号
     */
    private String employeeNumber;
    /**
     * 职位
     */
    private String jobTitle;
    /**
     * 职级
     */
    private String jobLevel;
    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 上级ID
     */
    private String managerId;
    /**
     * 上级姓名
     */
    private String manager;

    /**
     * 入职日期
     */
    private String entryDate;
    /**
     * 离职日期
     */
    private String quitDate;
    /**
     * 试用期结束日期
     */
    private String probationEndDate;
}
