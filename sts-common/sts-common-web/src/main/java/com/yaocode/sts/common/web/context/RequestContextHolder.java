package com.yaocode.sts.common.web.context;

import com.yaocode.sts.common.web.constants.ContextConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求上下文持有者 - 统一入口
 * 采用组合模式，聚合所有子Context
 */
@Slf4j
public class RequestContextHolder {

    // ========== 顶层上下文 ==========
    private static final ThreadLocal<RequestContext> CONTEXT_HOLDER = new ThreadLocal<>();

    // ========== 子Context实例（单例） ==========
    private static final ClientInfoContext CLIENT_CONTEXT = new ClientInfoContext();
    private static final DeviceInfoContext DEVICE_CONTEXT = new DeviceInfoContext();
    private static final UserInfoContext USER_CONTEXT = new UserInfoContext();
    private static final TenantInfoContext TENANT_CONTEXT = new TenantInfoContext();

    // ==========================================
    // 顶层上下文操作
    // ==========================================

    public static void setContext(RequestContext context) {
        CONTEXT_HOLDER.set(context);
        // 同步设置子Context
        if (context != null) {
            if (context.getClientInfo() != null) {
                CLIENT_CONTEXT.set(context.getClientInfo());
            }
            if (context.getDeviceInfo() != null) {
                DEVICE_CONTEXT.set(context.getDeviceInfo());
            }
            if (context.getUserInfo() != null) {
                USER_CONTEXT.set(context.getUserInfo());
            }
            if (context.getTenantInfo() != null) {
                TENANT_CONTEXT.set(context.getTenantInfo());
            }
        }
        log.debug("RequestContext set: {}", context != null ? context.getRequestId() : "null");
    }

    public static RequestContext getContext() {
        RequestContext context = CONTEXT_HOLDER.get();
        if (context == null) {
            context = RequestContext.builder()
                    .requestId(ContextConstants.DEFAULT_REQUEST_ID)
                    .build();
            CONTEXT_HOLDER.set(context);
        }
        return context;
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
        CLIENT_CONTEXT.clear();
        DEVICE_CONTEXT.clear();
        USER_CONTEXT.clear();
        TENANT_CONTEXT.clear();
        log.debug("All contexts cleared");
    }

    // ==========================================
    // 子Context便捷访问
    // ==========================================

    public static ClientInfoContext getClientInfo() {
        return CLIENT_CONTEXT.get();
    }

    public static DeviceInfoContext getDeviceInfo() {
        return DEVICE_CONTEXT.get();
    }

    public static UserInfoContext getUserInfo() {
        return USER_CONTEXT.get();
    }

    public static TenantInfoContext getTenantInfo() {
        return TENANT_CONTEXT.get();
    }

    // ==========================================
    // 链路追踪ID快速访问
    // ==========================================

    public static String getTraceId() {
        return getContext().getTraceId();
    }

    public static String getSpanId() {
        return getContext().getSpanId();
    }

    public static String getParentSpanId() {
        return getContext().getParentSpanId();
    }

    public static String getRequestId() {
        return getContext().getRequestId();
    }

    // ==========================================
    // 常用字段快速访问（避免深层调用）
    // ==========================================

    public static String getClientId() {
        ClientInfoContext client = getClientInfo();
        return client != null ? client.getClientId() : null;
    }

    public static String getClientType() {
        ClientInfoContext client = getClientInfo();
        return client != null ? client.getClientType() : null;
    }

    public static String getClientVersion() {
        ClientInfoContext client = getClientInfo();
        return client != null ? client.getClientVersion() : null;
    }

    public static String getDeviceId() {
        DeviceInfoContext device = getDeviceInfo();
        return device != null ? device.getDeviceId() : null;
    }

    public static String getDeviceType() {
        DeviceInfoContext device = getDeviceInfo();
        return device != null ? device.getDeviceType() : null;
    }

    public static String getLanguage() {
        DeviceInfoContext device = getDeviceInfo();
        return device != null ? device.getLanguage() : null;
    }

    public static String getUserId() {
        UserInfoContext user = getUserInfo();
        return user != null ? user.getUserId() : null;
    }

    public static String getUsername() {
        UserInfoContext user = getUserInfo();
        return user != null ? user.getUsername() : null;
    }

    public static String getTenantId() {
        TenantInfoContext tenant = getTenantInfo();
        return tenant != null ? tenant.getTenantId() : null;
    }

    public static String getTenantCode() {
        TenantInfoContext tenant = getTenantInfo();
        return tenant != null ? tenant.getTenantCode() : null;
    }

    public static String getIpAddress() {
        return getContext().getIpAddress();
    }

    public static String getSessionId() {
        return getContext().getSessionId();
    }

    public static String getCsrfToken() {
        return getContext().getCsrfToken();
    }

    public static String getUserAgent() {
        return getContext().getUserAgent();
    }

    public static boolean isAuthenticated() {
        UserInfoContext user = getUserInfo();
        return user != null && user.isAuthenticated();
    }

    // ==========================================
    // 子Context更新（保持一致性）
    // ==========================================

    /**
     * 更新用户信息（登录成功后调用）
     */
    public static void setUserInfo(UserInfoContext userInfo) {
        USER_CONTEXT.set(userInfo);
        RequestContext context = getContext();
        context.setUserInfo(userInfo);
        // 如果有租户信息，一并更新
        if (userInfo != null && userInfo.getTenantId() != null) {
            // 租户信息可能需要额外加载，这里只是设置ID
            if (context.getTenantInfo() == null) {
                TenantInfoContext tenantInfoContext = new TenantInfoContext();
                tenantInfoContext.setTenantId(userInfo.getTenantId());
                context.setTenantInfo(tenantInfoContext);
            }
        }
        log.debug("UserInfo updated: userId={}, username={}",
                userInfo != null ? userInfo.getUserId() : null,
                userInfo != null ? userInfo.getUsername() : null);
    }

    /**
     * 更新租户信息
     */
    public static void setTenantInfo(TenantInfoContext tenantInfo) {
        TENANT_CONTEXT.set(tenantInfo);
        RequestContext context = getContext();
        context.setTenantInfo(tenantInfo);
    }

}