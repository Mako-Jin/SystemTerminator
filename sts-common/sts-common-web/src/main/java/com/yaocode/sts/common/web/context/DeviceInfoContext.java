package com.yaocode.sts.common.web.context;

import com.yaocode.sts.common.web.constants.ContextConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备信息上下文
 * 包含：设备ID、类型、名称、指纹、操作系统等
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceInfoContext extends BaseAbstractContext<DeviceInfoContext> {

    // ========== 设备基础信息 ==========
    private String deviceId;
    private String deviceType;      // IOS / ANDROID / WINDOWS / MAC / LINUX
    private String deviceName;      // iPhone 15 Pro / Xiaomi 14
    private String deviceModel;     // 设备型号

    // ========== 操作系统信息 ==========
    private String osName;          // iOS / Android / Windows
    private String osVersion;
    private String osBuild;         // 系统构建版本

    // ========== 屏幕信息 ==========
    private String screenResolution;
    private String screenSize;      // 6.1英寸
    private Integer screenDensity;  // 屏幕密度

    // ========== 设备指纹 ==========
    private String deviceFingerprint;   // 设备指纹（服务端生成或客户端上报）
    private String imei;               // IMEI（Android）
    private String idfa;               // IDFA（iOS）
    private String macAddress;         // MAC地址（需脱敏）

    // ========== 安全信息 ==========
    private Boolean isJailbroken;      // 是否越狱/Root
    private Boolean isEmulator;        // 是否模拟器
    private Boolean isTrusted;         // 是否信任设备

    // ========== 位置信息 ==========
    private String countryCode;        // 国家代码
    private String language;           // 语言设置
    private String timezone;           // 时区

    public static DeviceInfoContext create() {
        return new DeviceInfoContext();
    }

    public static DeviceInfoContext createDefault() {
        DeviceInfoContext context = new DeviceInfoContext();
        context.setDeviceType(ContextConstants.DEVICE_TYPE_UNKNOWN);
        context.setIsJailbroken(ContextConstants.DEFAULT_IS_JAIL_BROKEN);
        context.setIsEmulator(ContextConstants.DEFAULT_IS_EMULATOR);
        context.setIsTrusted(ContextConstants.DEFAULT_IS_TRUSTED);
        return context;
    }

    @Override
    protected DeviceInfoContext getDefault() {
        return createDefault();
    }

    @Override
    protected String getContextName() {
        return ContextConstants.CONTEXT_NAME_DEVICE_INFO;
    }

    // ========== 便捷方法 ==========
    public boolean isIOS() {
        return ContextConstants.DEVICE_TYPE_IOS.equalsIgnoreCase(deviceType);
    }



    public boolean isAndroid() {
        return ContextConstants.DEVICE_TYPE_ANDROID.equalsIgnoreCase(deviceType);
    }

    public boolean isMobile() {
        return isIOS() || isAndroid();
    }
}
