package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.exception.AuthenticationException;
import com.yaocode.sts.auth.domain.service.AuthDomainService;
import com.yaocode.sts.auth.domain.service.provider.AuthenticationProvider;
import com.yaocode.sts.auth.domain.service.provider.ProviderManager;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 菜单领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class AuthDomainServiceImpl implements AuthDomainService {

    private static final Logger logger = LoggerFactory.getLogger(AuthDomainServiceImpl.class);

    @Resource
    private ProviderManager providerManager;

    @Override
    public AuthenticationToken authenticate(AbstractAuthCredential credential) {
        GrantTypeEnums grantType = credential.getGrantType();

        AuthenticationProvider<AbstractAuthCredential> provider = providerManager.getProvider(grantType);

        if (provider == null) {
            throw new AuthenticationException(AuthI18nKeyConstants.UNSUPPORTED_GRANT_TYPE);
        }
        if (Objects.equals(grantType, GrantTypeEnums.REMEMBER_ME)) {
            return provider.authenticate(credential);
        }

//        // 1. 验证 State（CSRF 防护）
//        if (!stateService.consume(token.getState(), token.getSessionId())) {
//            logger.warn("State 验证失败: sessionId={}", token.getSessionId());
//            return AuthenticationResult.failure("请求已过期，请刷新页面重试");
//        }
//
//        // 2. 验证验证码（如果需要）
//        if (!validateCaptcha(token)) {
//            return AuthenticationResult.failure("验证码错误");
//        }

        // 4. 执行认证
        return provider.authenticate(credential);
    }

}