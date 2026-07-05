package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;


@Data
@TableName("auth_tbl_login_history")
@EqualsAndHashCode(callSuper = true)
public class LoginHistoryPo extends BasePo {

    @TableId
    private String historyId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 所属租户
     */
    private String tenantId;
    /**
     * 用户名
     */
    private String username;

    /**
     * 登录来源：web、mobile、desktop、小程序、三方、其他
     */
    private Integer loginSource;

    /**
     * 认证方式：PASSWORD-密码、SMS-短信、TOTP-双因素、SOCIAL-社交
     */
    private Integer authMethod;

    private String loginIp;
    private String deviceId;
    private String clientId;
    private String userAgent;
    private LocalDateTime loginTime;
    private LocalDateTime logoutTime;

    /**
     * 主动退出/超时/被踢
     */
    private Integer logoutReason;
    /**
     * SUCCESS-成功、FAILED-失败
     */
    private Integer status;
    /**
     * 失败原因
     */
    private String failReason;
    /**
     * 会话ID（关联到当前会话）
     */
    private String sessionId;
}