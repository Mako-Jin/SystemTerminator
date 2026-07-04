package com.yaocode.sts.common.web.context;

import com.yaocode.sts.common.web.constants.ContextConstants;
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
        context.setClientType(ContextConstants.CLIENT_TYPE_WEB);
        context.setIsTrusted(ContextConstants.DEFAULT_IS_TRUSTED);
        return context;
    }

    @Override
    protected ClientInfoContext getDefault() {
        return createDefault();
    }

    @Override
    protected String getContextName() {
        return ContextConstants.CONTEXT_NAME_CLIENT_INFO;
    }

    // ========== 便捷方法 ==========
    public boolean isMobileApp() {
        return ContextConstants.CLIENT_TYPE_APP.equalsIgnoreCase(clientType);
    }

    public boolean isWeb() {
        return ContextConstants.CLIENT_TYPE_WEB.equalsIgnoreCase(clientType);
    }

    public boolean isMiniProgram() {
        return ContextConstants.CLIENT_TYPE_MINI_PROGRAM.equalsIgnoreCase(clientType);
    }

    public boolean isH5() {
        return ContextConstants.CLIENT_TYPE_H5.equalsIgnoreCase(clientType);
    }

}
