package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

import java.time.LocalDate;


@Data
@TableName("auth_tbl_user_employment")
@EqualsAndHashCode(callSuper = true)
public class UserEmploymentPo extends BasePo {

    @TableId
    private String employmentId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
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
    private String departmentName;

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
    private String managerName;
    /**
     * 助理用户ID
     */
    private String assistantId;

    /**
     * 助理姓名（冗余）
     */
    private String assistantName;
    /**
     * 入职日期
     */
    private LocalDate entryDate;
    /**
     * 离职日期
     */
    private LocalDate quitDate;
    /**
     * 试用期结束日期
     */
    private LocalDate probationEndDate;
    /**
     * 转正日期
     */
    private LocalDate regularizationDate;
    /**
     * 雇佣状态：ACTIVE（在职）, RESIGNED（已离职）, PROBATION（试用期）
     */
    private Integer employmentStatus;
}
