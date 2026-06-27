package com.yaocode.sts.auth.interfaces.model.params;

import lombok.Data;

@Data
public class PreLoginParams {

    /**
     * 租户编码（从域名或参数中获取）
     */
    private String tenantCode;

    /**
     * 租户ID（如果已知）
     */
    private String tenantId;

    /**
     * 用户名/手机号/邮箱（用于识别用户关联的租户列表）
     */
    private String identifier;

    /**
     * 重定向URI（登录成功后跳转）
     */
    private String redirectUri;

    /**
     * 记住我令牌（自动登录场景）
     */
    private String rememberMeToken;

    /**
     * 语言偏好（zh-CN/en-US）
     */
    private String language;

    /**
     * 域名（前端可传，服务端也可从RequestContext获取）
     */
    private String domain;

    /**
     * 请求时间戳（防重放攻击）
     */
    private Long timestamp;

}
