package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import com.yaocode.sts.common.infrastructure.po.BasePo;

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
     * 在线状态：0-离线、1-在线
     */
    private Integer online;

    /**
     * 是否激活：0：未激活；1：已激活
     */
    private Integer isEnabled;
    /**
     * 是否锁定：0-未锁定、1-已锁定
     */
    private Integer isLocked;
    /**
     * 锁定时间
     */
    private LocalDateTime lockTime;
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

}
