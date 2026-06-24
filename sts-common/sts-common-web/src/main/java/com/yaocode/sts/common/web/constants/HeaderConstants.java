package com.yaocode.sts.common.web.constants;

public interface HeaderConstants {

    // ==========================================
    // 客户端信息（前端SDK自动注入）
    // ==========================================

    /**
     * 客户端ID
     */
    String CLIENT_ID = "X-Client-Id";

    /**
     * 客户端类型：WEB / APP / MINI_PROGRAM / H5
     */
    String CLIENT_TYPE = "X-Client-Type";

    /**
     * 客户端版本号
     */
    String CLIENT_VERSION = "X-Client-Version";

    /**
     * 客户端名称
     */
    String CLIENT_NAME = "X-Client-Name";

    // ==========================================
    // 设备信息（前端SDK自动注入）
    // ==========================================

    /**
     * 设备ID
     */
    String DEVICE_ID = "X-Device-Id";

    /**
     * 设备类型：IOS / ANDROID / WINDOWS / MAC / LINUX
     */
    String DEVICE_TYPE = "X-Device-Type";

    /**
     * 设备名称
     */
    String DEVICE_NAME = "X-Device-Name";

    /**
     * 设备型号
     */
    String DEVICE_MODEL = "X-Device-Model";

    /**
     * 操作系统名称
     */
    String OS_NAME = "X-OS-Name";

    /**
     * 操作系统版本
     */
    String OS_VERSION = "X-OS-Version";

    /**
     * 操作系统构建版本
     */
    String OS_BUILD = "X-OS-Build";
    /**
     * appId
     */
    String APP_ID = "X-App-Id";
    /**
     * 应用版本
     */
    String APP_VERSION = "X-App-Version";

    /**
     * 应用包名
     */
    String APP_PACKAGE = "X-App-Package";

    /**
     * 屏幕分辨率
     */
    String SCREEN_RESOLUTION = "X-Screen-Resolution";

    /**
     * 屏幕尺寸
     */
    String SCREEN_SIZE = "X-Screen-Size";

    /**
     * 屏幕密度
     */
    String SCREEN_DENSITY = "X-Screen-Density";

    /**
     * 设备指纹
     */
    String DEVICE_FINGERPRINT = "X-Device-Fingerprint";

    /**
     * IMEI（Android）
     */
    String IMEI = "X-IMEI";

    /**
     * IDFA（iOS）
     */
    String IDFA = "X-IDFA";

    /**
     * MAC地址（需脱敏）
     */
    String MAC_ADDRESS = "X-MAC-Address";

    /**
     * 是否越狱/Root
     */
    String IS_JAIL_BROKEN = "X-Is-Jail_Broken";

    /**
     * 是否模拟器
     */
    String IS_EMULATOR = "X-Is-Emulator";

    /**
     * 国家代码
     */
    String COUNTRY_CODE = "X-Country-Code";

    // ==========================================
    // 语言/时区（前端SDK自动注入）
    // ==========================================

    /**
     * 语言设置（优先使用 Accept-Language）
     */
    String LANGUAGE = "Accept-Language";

    /**
     * 时区
     */
    String TIMEZONE = "X-Timezone";

    // ==========================================
    // 链路追踪（网关/前端自动注入）
    // ==========================================

    /**
     * 全链路追踪ID
     */
    String TRACE_ID = "X-Trace-Id";

    /**
     * 当前调用段ID
     */
    String SPAN_ID = "X-Span-Id";

    /**
     * 父级调用段ID
     */
    String PARENT_SPAN_ID = "X-Parent-Span-Id";

    /**
     * 请求ID
     */
    String REQUEST_ID = "X-Request-Id";

    // ==========================================
    // 安全相关
    // ==========================================

    /**
     * CSRF Token
     */
    String CSRF_TOKEN = "X-CSRF-TOKEN";

    /**
     * 认证Token
     */
    String AUTHORIZATION = "Authorization";

    /**
     * Remember Me Token
     */
    String REMEMBER_ME_TOKEN = "X-Remember-Me-Token";

    // ==========================================
    // OAuth2 相关
    // ==========================================

    /**
     * 授权类型
     */
    String GRANT_TYPE = "X-Grant-Type";

    /**
     * 授权范围
     */
    String SCOPE = "X-Scope";

    /**
     * 重定向URI
     */
    String REDIRECT_URI = "X-Redirect-Uri";

    /**
     * 重定向URI
     */
    String USER_AGENT = "User-Agent";
    /**
     * 重定向URI
     */
    String REFERER = "Referer";
    /**
     * 重定向URI
     */
    String ORIGIN = "Origin";
    /**
     * 重定向URI
     */
    String REQUESTED_WITH = "X-Requested-With";

}
