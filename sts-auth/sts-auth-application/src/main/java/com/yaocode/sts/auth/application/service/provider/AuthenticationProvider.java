package com.yaocode.sts.auth.application.service.provider;

import com.yaocode.sts.auth.application.dto.AuthenticationResultDto;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;

import java.util.Objects;

/**
 * 认证提供器接口
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:40
 */
public interface AuthenticationProvider<T extends AbstractAuthCredential> {

    /**
     * 获取当前provider支持的授权类型
     * @return com.yaocode.sts.auth.domain.enums.GrantTypeEnums
     */
    GrantTypeEnums getGrantType();

    /**
     * 认证
     * @param credential 认证参数
     * @return AuthenticationResultDto
     */
    AuthenticationResultDto authenticate(T credential);

    /**
     * 检查当前provider是否支持
     * @param credential 认证参数
     * @return boolean
     */
    default boolean supports(AbstractAuthCredential credential) {
        return Objects.equals(credential.getGrantType(), getGrantType());
    }

}
