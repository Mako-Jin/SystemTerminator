package com.yaocode.sts.common.web.constants;

public interface RequestConstants {

    /**
     * 默认客户端类型
     */
    String DEFAULT_CLIENT_TYPE = "WEB";

    /**
     * 默认设备类型
     */
    String DEFAULT_DEVICE_TYPE = "UNKNOWN";

    /**
     * 默认语言
     */
    String DEFAULT_LANGUAGE = "zh-CN";

    /**
     * 默认时区
     */
    String DEFAULT_TIMEZONE = "Asia/Shanghai";

    /**
     * 默认是否信任
     */
    Boolean DEFAULT_IS_TRUSTED = false;

    /**
     * 默认是否越狱
     */
    Boolean DEFAULT_IS_JAIL_BROKEN = false;

    /**
     * 默认是否模拟器
     */
    Boolean DEFAULT_IS_EMULATOR = false;

    // ==========================================
    // 客户端类型
    // ==========================================

    /**
     * 客户端类型 - Web
     */
    String CLIENT_TYPE_WEB = "WEB";

    /**
     * 客户端类型 - APP
     */
    String CLIENT_TYPE_APP = "APP";

    /**
     * appId
     */
    String APP_ID = "appId";
    /**
     * 应用版本
     */
    String APP_VERSION = "appVersion";

    /**
     * 应用包名
     */
    String APP_PACKAGE = "appPackage";

    /**
     * 客户端类型 - 小程序
     */
    String CLIENT_TYPE_MINI_PROGRAM = "MINI_PROGRAM";

    /**
     * 客户端类型 - H5
     */
    String CLIENT_TYPE_H5 = "H5";

    // ==========================================
    // 设备类型
    // ==========================================

    /**
     * 设备类型 - iOS
     */
    String DEVICE_TYPE_IOS = "IOS";

    /**
     * 设备类型 - Android
     */
    String DEVICE_TYPE_ANDROID = "ANDROID";

    /**
     * 设备类型 - Windows
     */
    String DEVICE_TYPE_WINDOWS = "WINDOWS";

    /**
     * 设备类型 - Mac
     */
    String DEVICE_TYPE_MAC = "MAC";

    /**
     * 设备类型 - Linux
     */
    String DEVICE_TYPE_LINUX = "LINUX";

    // ==========================================
    // Cookie 名称
    // ==========================================

    /**
     * Remember Me Cookie 名称
     */
    String COOKIE_REMEMBER_ME = "remember_me";

    /**
     * Session Cookie 名称
     */
    String COOKIE_SESSION = "JSESSIONID";

    // ==========================================
    // 参数名称
    // ==========================================

    /**
     * 参数 - traceId
     */
    String TRACE_ID = "traceId";

    /**
     * 参数 - spanId
     */
    String SPAN_ID = "spanId";

    /**
     * 参数 - parentSpanId
     */
    String PARENT_SPAN_ID = "parentSpanId";

    /**
     * 参数 - requestId
     */
    String REQUEST_ID = "requestId";

    /**
     * 参数 - clientId
     */
    String CLIENT_ID = "clientId";

    /**
     * 参数 - clientType
     */
    String CLIENT_TYPE = "clientType";

    /**
     * 参数 - clientVersion
     */
    String CLIENT_VERSION = "clientVersion";
    String CLIENT_NAME = "clientName";

    /**
     * 参数 - deviceId
     */
    String DEVICE_ID = "deviceId";

    /**
     * 参数 - deviceType
     */
    String DEVICE_TYPE = "deviceType";

    /**
     * 参数 - deviceName
     */
    String DEVICE_NAME = "deviceName";
    /**
     * 参数 - deviceModel
     */
    String DEVICE_MODEL = "deviceModel";
    /**
     * 操作系统名称
     */
    String OS_NAME = "osName";
    /**
     * 参数 - osVersion
     */
    String OS_VERSION = "osVersion";
    /**
     * 操作系统构建版本
     */
    String OS_BUILD = "osBuild";
    /**
     * 屏幕分辨率
     */
    String SCREEN_RESOLUTION = "screenResolution";
    /**
     * 屏幕尺寸
     */
    String SCREEN_SIZE = "screenSize";
    /**
     * 设备指纹
     */
    String DEVICE_FINGERPRINT = "deviceFingerprint";
    /**
     * 参数 - rememberMeToken
     */
    String REMEMBER_ME_TOKEN = "rememberMeToken";
    /**
     * IMEI（Android）
     */
    String IMEI = "IMEI";

    /**
     * IDFA（iOS）
     */
    String IDFA = "IDFA";
    /**
     * MAC地址（需脱敏）
     */
    String MAC_ADDRESS = "macAddress";
    /**
     * 国家代码
     */
    String COUNTRY_CODE = "countryCode";
    /**
     * 语言设置（优先使用 Accept-Language）
     */
    String LANGUAGE = "acceptLanguage";
    /**
     * 时区
     */
    String TIMEZONE = "timezone";
    /**
     * 是否越狱/Root
     */
    String IS_JAIL_BROKEN = "isJailBroken";
    /**
     * 是否模拟器
     */
    String IS_EMULATOR = "isEmulator";
    /**
     * 屏幕密度
     */
    String SCREEN_DENSITY = "screenDensity";
    /**
     * 参数 - rememberMe
     */
    String REMEMBER_ME = "rememberMe";

    // ==========================================
    // MDC 键名
    // ==========================================

    /**
     * MDC - traceId
     */
    String MDC_TRACE_ID = "traceId";

    /**
     * MDC - spanId
     */
    String MDC_SPAN_ID = "spanId";

    /**
     * MDC - parentSpanId
     */
    String MDC_PARENT_SPAN_ID = "parentSpanId";

    /**
     * MDC - requestId
     */
    String MDC_REQUEST_ID = "requestId";

    /**
     * MDC - clientId
     */
    String MDC_CLIENT_ID = "clientId";

    /**
     * MDC - deviceId
     */
    String MDC_DEVICE_ID = "deviceId";

    /**
     * MDC - ipAddress
     */
    String MDC_IP_ADDRESS = "ipAddress";

    /**
     * MDC - userId
     */
    String MDC_USER_ID = "userId";

    /**
     * MDC - tenantId
     */
    String MDC_TENANT_ID = "tenantId";

    /**
     * MDC - username
     */
    String MDC_USERNAME = "username";

    /**
     * 授权类型
     */
    String GRANT_TYPE = "grantType";

    /**
     * 授权范围
     */
    String SCOPE = "scope";

    /**
     * 重定向URI
     */
    String REDIRECT_URI = "redirectUri";

    // ==================== ID格式常量 ====================
    /**
     * TraceId格式
     */
    String TRACE_ID_FORMAT = "trace-%d-%s";

    /**
     * SpanId格式
     */
    String SPAN_ID_FORMAT = "span-%d-%s";

    // ==================== Remember Me 相关常量 ====================
    /**
     * Remember Me 参数名（驼峰式）
     */
    String PARAM_REMEMBER_ME_CAMEL = "rememberMe";

    /**
     * Remember Me Cookie名称（下划线式）
     */
    String COOKIE_REMEMBER_ME_UNDERSCORE = "remember_me";

    /**
     * Remember Me Cookie名称（连字符式）
     */
    String COOKIE_REMEMBER_ME_DASH = "remember-me";

    /**
     * Remember Me Header名称
     */
    String HEADER_COOKIE_REMEMBER_ME = "Cookie-Remember-Me";

}
