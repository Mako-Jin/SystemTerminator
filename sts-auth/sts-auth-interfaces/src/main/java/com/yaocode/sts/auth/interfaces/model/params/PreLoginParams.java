package com.yaocode.sts.auth.interfaces.model.params;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PreLoginParams {

    /**
     * 客户端ID
     */
    @NotBlank(message = "客户端ID不能为空")
    private String clientId;

    /**
     * 客户端类型
     * WEB / APP / MINI_PROGRAM / H5
     */
    private String clientType;

    /**
     * 客户端版本号
     * 用于版本兼容性处理
     */
    private String clientVersion;

    /**
     * 设备ID（用于设备指纹）
     */
    private String deviceId;

    /**
     * 设备类型
     * IOS / ANDROID / WINDOWS / MAC / LINUX
     */
    private String deviceType;

    /**
     * 设备名称
     * 如：iPhone 15 Pro / Xiaomi 14
     */
    private String deviceName;

    /**
     * 会话ID（如果已有）
     */
    private String sessionId;

    /**
     * 记住我令牌（自动登录场景）
     */
    private String rememberMe;

    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 应用版本
     */
    private String appVersion;

    /**
     * 屏幕分辨率
     * 用于行为分析/风控
     */
    private String screenResolution;

    /**
     * 语言设置
     * zh-CN / en-US
     */
    private String language;

    /**
     * 时区
     * Asia/Shanghai
     */
    private String timezone;

    /**
     * 用户代理（服务端可自动获取，也可由前端传递）
     */
    private String userAgent;

    /**
     * IP地址（服务端可自动获取）
     */
    private String ipAddress;

    /**
     * 来源页面URL
     * 用于登录成功后跳转回原页面
     */
    private String redirectUri;

    /**
     * 验证码key（如果需要预获取验证码）
     * 某些验证码方案需要先获取captchaKey
     */
    private String captchaKey;

}
