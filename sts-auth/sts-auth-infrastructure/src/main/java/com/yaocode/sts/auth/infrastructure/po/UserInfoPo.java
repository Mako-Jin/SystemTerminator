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
@TableName("auth_tbl_user")
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
     * 密码
     */
    private String password;
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
     * 解锁时间
     */
    private LocalDateTime unlockTime;
    /**
     * 是否被删：0：删了，1没有删
     */
    private Integer isDeleted;
    /**
     * 在线状态：0-离线、1-在线
     */
    private Integer online;

}
