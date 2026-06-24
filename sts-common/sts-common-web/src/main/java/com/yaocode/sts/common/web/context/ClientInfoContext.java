package com.yaocode.sts.common.web.context;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端信息上下文
 * 包含：客户端ID、类型、版本、应用信息等
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClientInfoContext extends BaseAbstractContext<ClientInfoContext> {

    // ========== 基础信息 ==========
    private String clientId;
    private String clientType;      // WEB / APP / MINI_PROGRAM / H5
    private String clientVersion;
    private String clientName;

    // ========== 应用信息 ==========
    private String appId;
    private String appVersion;
    private String appPackage;      // Android包名 / iOS Bundle ID

    // ========== OAuth2 相关信息 ==========
    private String grantType;
    private String scope;
    private String redirectUri;

    // ========== 安全信息 ==========
    private String clientSecret;     // 服务端使用，不对外暴露
    private Boolean isTrusted;       // 是否可信客户端

    public static ClientInfoContext createDefault() {
        ClientInfoContext context = new ClientInfoContext();
        context.setClientType("WEB");
        context.setIsTrusted(false);
        return context;
    }

    @Override
    protected ClientInfoContext getDefault() {
        return createDefault();
    }

    @Override
    protected String getContextName() {
        return "ClientInfo";
    }

    // ========== 便捷方法 ==========
    public boolean isMobileApp() {
        return "APP".equalsIgnoreCase(clientType);
    }

    public boolean isWeb() {
        return "WEB".equalsIgnoreCase(clientType);
    }

    public boolean isMiniProgram() {
        return "MINI_PROGRAM".equalsIgnoreCase(clientType);
    }

    public boolean isH5() {
        return "H5".equalsIgnoreCase(clientType);
    }

}
