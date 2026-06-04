package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import com.yaocode.sts.common.infrastructure.po.BasePo;


@Data
@TableName("auth_tbl_login_history")
@EqualsAndHashCode(callSuper = true)
public class LoginHistoryPo extends BasePo {
    @TableId
    private String id;

    private String userId;
    private String username;

    /**
     * 登录来源：web、mobile、desktop、小程序、三方、其他
     */
    private String loginSource;

    /**
     * 认证方式：PASSWORD-密码、SMS-短信、TOTP-双因素、SOCIAL-社交
     */
    private String authMethod;

    private String clientIp;
    private String userAgent;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    /**
     * 主动退出/超时/被踢
     */
    private String logoutReason;
    /**
     * SUCCESS-成功、FAILED-失败
     */
    private String status;
    /**
     * 失败原因
     */
    private String failReason;
}