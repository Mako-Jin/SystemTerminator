package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户持久化对象
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:41
 */
@Data
@TableName("auth_tbl_user_info")
@EqualsAndHashCode(callSuper = true)
public class UserInfoPo extends BasePo {

    /**
     * 用户id
     */
    @TableId
    private String userId;
    /**
     * 用户名
     */
    private String username;
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
     * 是否已绑定MFA
     */
    private Integer mfaBound;

    /**
     * MFA类型：TOTP, SMS, EMAIL
     */
    private Integer mfaType;
    /**
     * 注册来源：REGISTER, ADMIN, SOCIAL, IMPORT
     */
    private Integer registerSource;

    /**
     * 注册时间
     */
    private LocalDateTime registeredAt;
    /**
     * 是否被删：0：没有删，1：被删
     */
    private Integer isDeleted;

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
     * 密码最后修改人（用于审计）
     */
    private String passwordChangedBy;

    /**
     * 密码修改来源：SELF-用户自行修改, ADMIN-管理员重置,
     * SYSTEM-系统强制重置, RESET-忘记密码重置
     */
    private Integer passwordChangeSource;

}
