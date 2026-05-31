package com.yaocode.sts.auth.domain.valueobjects.primitives;

import lombok.Data;

import java.util.Map;

@Data
public class DeviceInfo {

    /**
     * 设备ID
     */
    private String deviceId;
    /**
     * IOS, ANDROID, WEB, WECHAT_MINI
     */
    private String deviceType;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 操作系统
     */
    private String osName;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 应用版本
     */
    private String appVersion;
    /**
     * IP地址
     */
    private String ipAddress;
    /**
     * User Agent
     */
    private String userAgent;
    /**
     * 设备指纹
     */
    private String deviceFingerprint;
    /**
     * 是否越狱/root
     */
    private Boolean jailbroken;
    /**
     * 扩展信息
     */
    private Map<String, Object> extras;

}
