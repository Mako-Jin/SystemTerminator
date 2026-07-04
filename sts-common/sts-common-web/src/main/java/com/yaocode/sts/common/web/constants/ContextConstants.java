package com.yaocode.sts.common.web.constants;

/**
 * 上下文常量类
 * @author: Jin-LiangBo
 * @date: 2026年07月04日
 * TODO sts-auth-domain 里面有DeviceTypeEnums和ClientTypeEnums, 怎样和这块整合起来
 */
public interface ContextConstants {

    // ==========================================
    // 上下文名称常量
    // ==========================================

    /**
     * 用户信息上下文名称
     */
    String CONTEXT_NAME_USER_INFO = "UserInfo";

    /**
     * 客户端信息上下文名称
     */
    String CONTEXT_NAME_CLIENT_INFO = "ClientInfo";

    /**
     * 设备信息上下文名称
     */
    String CONTEXT_NAME_DEVICE_INFO = "DeviceInfo";

    /**
     * 请求上下文名称
     */
    String CONTEXT_NAME_REQUEST = "Request";

    /**
     * 租户信息上下文名称
     */
    String CONTEXT_NAME_TENANT_INFO = "TenantInfo";

    // ==========================================
    // 客户端类型常量
    // ==========================================

    /**
     * 客户端类型：WEB
     */
    String CLIENT_TYPE_WEB = "WEB";

    /**
     * 客户端类型：APP
     */
    String CLIENT_TYPE_APP = "APP";

    /**
     * 客户端类型：小程序
     */
    String CLIENT_TYPE_MINI_PROGRAM = "MINI_PROGRAM";

    /**
     * 客户端类型：H5
     */
    String CLIENT_TYPE_H5 = "H5";

    // ==========================================
    // 设备类型常量
    // ==========================================

    /**
     * 设备类型：未知
     */
    String DEVICE_TYPE_UNKNOWN = "UNKNOWN";

    /**
     * 设备类型：iOS
     */
    String DEVICE_TYPE_IOS = "IOS";

    /**
     * 设备类型：Android
     */
    String DEVICE_TYPE_ANDROID = "ANDROID";

    /**
     * 设备类型：Windows
     */
    String DEVICE_TYPE_WINDOWS = "WINDOWS";

    /**
     * 设备类型：Mac
     */
    String DEVICE_TYPE_MAC = "MAC";

    /**
     * 设备类型：Linux
     */
    String DEVICE_TYPE_LINUX = "LINUX";

    // ==========================================
    // 默认值常量
    // ==========================================

    /**
     * 默认是否可信
     */
    boolean DEFAULT_IS_TRUSTED = false;

    /**
     * 默认是否越狱/Root
     */
    boolean DEFAULT_IS_JAIL_BROKEN = false;

    /**
     * 默认是否模拟器
     */
    boolean DEFAULT_IS_EMULATOR = false;

    /**
     * 默认RequestId
     */
    String DEFAULT_REQUEST_ID = "default";

}
