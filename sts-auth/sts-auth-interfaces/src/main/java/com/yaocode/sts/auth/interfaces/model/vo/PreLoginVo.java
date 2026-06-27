package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 预登录响应 VO
 */
@Data
@Builder
public class PreLoginVo {

    /**
     * 是否已认证（Remember Me自动登录成功）
     */
    private Boolean isAuthenticated;

    /**
     * 认证用户信息（如果已认证）
     */
    private UserInfoVo userInfo;

    /**
     * 登录成功信息
     */
    private LoginSuccessVo loginSuccessVo;

    /**
     * State Token（防CSRF，绑定登录态）
     */
    private String stateToken;

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 是否需要选择租户
     */
    private Boolean needSelectTenant;

    /**
     * 租户登录配置列表
     */
    private List<LoginConfigVo> tenantLoginConfigs;

    /**
     * 服务器时间戳
     */
    private Long serverTime;
}
