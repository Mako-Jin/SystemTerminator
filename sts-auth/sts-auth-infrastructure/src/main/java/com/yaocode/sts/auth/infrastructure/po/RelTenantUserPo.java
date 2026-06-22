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
    private Integer userType;

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
     * 离开原因
     */
    private String leaveReason;
    /**
     * 登录来源：
     * REGISTER-注册、
     * ASSIGN-管理员分配、
     * SOCIAL-社交登录、
     * SYNC-同步导入
     */
    private Integer userSource;
    /**
     * 用户在该租户下的昵称（可覆盖全局昵称）
     */
    private String tenantNickname;

    /**
     * 用户在该租户下的头像（可覆盖全局头像）
     */
    private String tenantAvatar;
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

    /**
     * 密码
     */
    private String password;
    /**
     * 密码最后修改时间
     */
    private LocalDateTime passwordChangedAt;
    /**
     * 密码过期时间
     */
    private LocalDateTime passwordExpiredAt;
    /**
     * 可逆加密的密码  采用非对称加密，并且公钥加密，私钥不公开
     */
    private String decipherable;
    /**
     * 盐
     */
    private String salt;
    /**
     * 是否激活：0：未激活；1：已激活
     */
    private Integer isEnabled;
    /**
     * 是否锁定：0-未锁定、1-已锁定
     */
    private Integer isLocked;
    /**
     * 自动解锁时间
     */
    private LocalDateTime unlockAt;
    /**
     * 锁定时间
     */
    private LocalDateTime lockedAt;
    /**
     * 锁定原因
     */
    private String lockReason;
    /**
     * 解锁时间
     */
    private LocalDateTime unlockTime;
    /**
     * 是否被删：0：没有删，1：被删
     */
    private Integer isDeleted;

}
