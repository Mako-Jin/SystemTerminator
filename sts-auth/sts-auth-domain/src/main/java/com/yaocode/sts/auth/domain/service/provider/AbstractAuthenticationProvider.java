package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.application.dto.AuthenticationResultDto;import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * provider抽象实现类
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:52
 */
public abstract class AbstractAuthenticationProvider<T extends AbstractAuthCredential> implements AuthenticationProvider<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAuthenticationProvider.class);

    @Override
    public AuthenticationResultDto authenticate(T credential) {
        // 1. 前置检查
        preAuthenticateCheck(credential);

        // 2. 执行具体认证逻辑
        UserInfoEntity user = doAuthenticate(credential);

        // 3. 后置验证
        postAuthenticateValidate(user);

        // 4. 构建认证成功的令牌
        AuthenticationResultDto result = buildSuccessToken(user, credential);

        // 5. 记录认证日志
        logAuthenticationSuccess(user, AuthenticationResultDto);

        return result;
    }

    /**
     * 前置检查
     */
    protected void preAuthenticateCheck(T credential) {
        if (credential == null) {
            throw new IllegalArgumentException("认证令牌不能为空");
        }
//        if (credential == null) {
//            throw new IllegalArgumentException("认证凭证不能为空");
//        }
    }

    /**
     * 执行具体认证逻辑（子类实现）
     */
    protected abstract UserInfoEntity doAuthenticate(T credential);

    /**
     * 后置验证
     */
    protected void postAuthenticateValidate(UserInfoEntity user) {
        if (user == null) {
            throw new IllegalStateException("认证失败：用户不存在");
        }
        validateUserStatus(user);
    }

    /**
     * 验证用户状态（子类可扩展）
     */
    protected void validateUserStatus(UserInfoEntity user) {
        // 默认实现，子类可以重写添加更多验证
        // 例如：账户激活状态、锁定状态、过期状态等
    }

    /**
     * 构建成功令牌
     */
    protected AuthenticationToken buildSuccessToken(UserInfoEntity user, AuthenticationToken original) {
        return AuthenticationToken.authenticated(user, original.getGrantType());
    }

    /**
     * 记录认证成功日志
     */
    protected void logAuthenticationSuccess(UserInfoEntity user, AuthenticationToken authentication) {
        logger.info("认证成功: userId={}, username={}, provider={}, ip={}",
                user.getId(),
                user.getUsername(),
                getName(),
                authentication.getClientIp());
    }

}
