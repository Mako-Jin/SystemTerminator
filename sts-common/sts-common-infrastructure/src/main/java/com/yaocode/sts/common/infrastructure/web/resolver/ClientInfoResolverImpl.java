package com.yaocode.sts.common.infrastructure.web.resolver;

import com.yaocode.sts.common.domain.constants.HeaderConstants;
import com.yaocode.sts.common.domain.constants.RequestConstants;
import com.yaocode.sts.common.domain.context.ClientInfoContext;
import com.yaocode.sts.common.domain.context.spi.ClientInfoResolver;
import com.yaocode.sts.common.domain.valueobject.ClientId;
import com.yaocode.sts.common.domain.web.HttpRequestContext;

import java.util.Optional;

/**
 * 客户端信息解析器
 * <p>
 * 支持双通道解析：优先从 Query/Form Parameter 获取，Parameter 不存在时从 HTTP Header 获取
 * 典型场景：前端 SDK 通过 Header 注入客户端信息，第三方回调通过 Parameter 传递
 */
public class ClientInfoResolverImpl implements ClientInfoResolver {

    @Override
    public Optional<ClientInfoContext> resolve(HttpRequestContext context) {
        ClientInfoContext clientInfo = ClientInfoContext.createDefault();

        // ========== 基础信息 ==========
        Optional<String> clientIdOpt = context.getHeaderOrParameter(HeaderConstants.CLIENT_ID)
                .or(() -> context.getHeaderOrParameter(RequestConstants.CLIENT_ID));
        clientIdOpt.ifPresent(v -> clientInfo.setClientId(ClientId.of(v)));

        context.getHeaderOrParameter(HeaderConstants.CLIENT_TYPE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.CLIENT_TYPE))
                .ifPresent(clientInfo::setClientType);
        context.getHeaderOrParameter(HeaderConstants.CLIENT_VERSION)
                .or(() -> context.getHeaderOrParameter(RequestConstants.CLIENT_VERSION))
                .ifPresent(clientInfo::setClientVersion);
        context.getHeaderOrParameter(HeaderConstants.CLIENT_NAME)
                .or(() -> context.getHeaderOrParameter(RequestConstants.CLIENT_NAME))
                .ifPresent(clientInfo::setClientName);

        // ========== 应用信息 ==========
        context.getHeaderOrParameter(HeaderConstants.APP_ID)
                .or(() -> context.getHeaderOrParameter(RequestConstants.APP_ID))
                .ifPresent(clientInfo::setAppId);
        context.getHeaderOrParameter(HeaderConstants.APP_VERSION)
                .or(() -> context.getHeaderOrParameter(RequestConstants.APP_VERSION))
                .ifPresent(clientInfo::setAppVersion);
        context.getHeaderOrParameter(HeaderConstants.APP_PACKAGE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.APP_PACKAGE))
                .ifPresent(clientInfo::setAppPackage);

        // ========== OAuth2 相关 ==========
        context.getHeaderOrParameter(HeaderConstants.GRANT_TYPE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.GRANT_TYPE))
                .ifPresent(clientInfo::setGrantType);
        context.getHeaderOrParameter(HeaderConstants.SCOPE)
                .or(() -> context.getHeaderOrParameter(RequestConstants.SCOPE))
                .ifPresent(clientInfo::setScope);
        context.getHeaderOrParameter(HeaderConstants.REDIRECT_URI)
                .or(() -> context.getHeaderOrParameter(RequestConstants.REDIRECT_URI))
                .ifPresent(clientInfo::setRedirectUri);

        // ========== 安全信息 ==========
        context.getHeaderOrParameter(HeaderConstants.IS_TRUSTED)
                .or(() -> context.getHeaderOrParameter(RequestConstants.IS_TRUSTED))
                .ifPresent(v -> clientInfo.setIsTrusted(Boolean.parseBoolean(v)));

        return Optional.of(clientInfo);
    }
}