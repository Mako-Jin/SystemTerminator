package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 电话号码
     */
    private String phoneNum;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐
     */
    private String salt;
    /**
     * 是否激活：0：没有；1：有
     */
    private Integer isEnabled;
    /**
     * 是否被删：0：删了，1没有删
     */
    private Integer isDeleted;
    /**
     * 租户id
     */
    private String tenantId;

}
