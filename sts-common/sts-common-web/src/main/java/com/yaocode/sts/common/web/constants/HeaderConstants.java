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

    // ==========================================
    // IP获取相关
    // ==========================================

    /**
     * X-Forwarded-For（代理转发IP）
     */
    String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * Proxy-Client-IP（代理客户端IP）
     */
    String PROXY_CLIENT_IP = "Proxy-Client-IP";

    /**
     * WL-Proxy-Client-IP（WebLogic代理IP）
     */
    String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    // ==========================================
    // 响应头名称
    // ==========================================

    /**
     * 缓存控制
     */
    String HEADER_CACHE_CONTROL = "Cache-Control";

    /**
     * Pragma（HTTP/1.0缓存控制）
     */
    String HEADER_PRAGMA = "Pragma";

    /**
     * 过期时间
     */
    String HEADER_EXPIRES = "Expires";

    /**
     * 内容类型选项
     */
    String HEADER_X_CONTENT_TYPE_OPTIONS = "X-Content-Type-Options";

    /**
     * 帧选项（防止点击劫持）
     */
    String HEADER_X_FRAME_OPTIONS = "X-Frame-Options";

    /**
     * XSS保护
     */
    String HEADER_X_XSS_PROTECTION = "X-XSS-Protection";

    // ==========================================
    // 响应头值
    // ==========================================

    /**
     * Cache-Control值：不缓存
     */
    String CACHE_CONTROL_NO_CACHE = "no-store, no-cache, must-revalidate";

    /**
     * Pragma值：不缓存
     */
    String PRAGMA_NO_CACHE = "no-cache";

    /**
     * Expires值：立即过期
     */
    String EXPIRES_IMMEDIATE = "0";

    /**
     * X-Content-Type-Options值：禁止MIME类型嗅探
     */
    String X_CONTENT_TYPE_OPTIONS_NOSNIFF = "nosniff";

    /**
     * X-Frame-Options值：禁止嵌入帧
     */
    String X_FRAME_OPTIONS_DENY = "DENY";

    /**
     * X-XSS-Protection值：启用XSS保护
     */
    String X_XSS_PROTECTION_ENABLED = "1; mode=block";

}
