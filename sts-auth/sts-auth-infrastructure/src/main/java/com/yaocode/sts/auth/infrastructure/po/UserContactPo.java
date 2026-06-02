package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("auth_tbl_user_contact")
@EqualsAndHashCode(callSuper = true)
public class UserContactPo extends BasePo {

    @TableId
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 联系方式类型：MOBILE-手机、EMAIL-邮箱、PHONE-座机
     */
    private String contactType;

    /**
     * 联系方式值（手机号/邮箱地址）
     */
    private String contactValue;

    /**
     * 是否验证：0-未验证、1-已验证
     */
    private Integer verified;

    /**
     * 是否主联系方式：0-否、1-是（用户可能有多个手机号，但只有一个主号）
     */
    private Integer primary;

    /**
     * 备注（如：工作手机、个人手机）
     */
    private String remark;
}