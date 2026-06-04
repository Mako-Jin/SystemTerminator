package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;


@Data
@TableName("auth_tbl_user_profile")
@EqualsAndHashCode(callSuper = true)
public class UserProfilePo extends BasePo {

    /**
     * 与用户ID相同
     */
    @TableId
    private String userId;

    /**
     * 显示名称
     */
    private String displayName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private String birthDate;
    /**
     * 婚姻状态
     */
    private Integer married;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 语言
     */
    private String locale;
    /**
     * 时区
     */
    private String timeZone;
    /**
     * 主题
     */
    private String theme;

    /**
     * JSON格式
     */
    private String extraAttributes;
}