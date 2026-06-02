package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户用户关联表
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:28
 */
@Data
@TableName("auth_tbl_rel_tenant_users")
public class RelTenantUserPo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户在该租户中的身份类型
     * EMPLOYEE-员工、ADMIN-管理员、GUEST-访客等
     */
    private String userType;

    /**
     * 在该租户中的状态
     */
    private Integer status;
    /**
     * 加入日期
     */
    private LocalDateTime joinDate;
    /**
     * 离开日期
     */
    private LocalDateTime leaveDate;

    /**
     * 登录来源：
     * REGISTER-注册、
     * ASSIGN-管理员分配、
     * SOCIAL-社交登录、
     * SYNC-同步导入
     */
    private String userSource;

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

    private Integer isDeleted;

}
