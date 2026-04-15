package com.yaocode.sts.auth.application.service.provider;

import com.yaocode.sts.auth.application.dto.AuthenticationResultDto;
import com.yaocode.sts.auth.application.exception.AuthenticationException;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordAuthCredential;
import com.yaocode.sts.common.domain.context.TenantInfoContext;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 密码模式认证provider
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:52
 */
@Service
public class PasswordAuthenticationProvider extends AbstractAuthenticationProvider<PasswordAuthCredential> {

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public GrantTypeEnums getGrantType() {
        return GrantTypeEnums.PASSWORD;
    }

    @Override
    public AuthenticationResultDto authenticate(PasswordAuthCredential credential) {
        // 查询用户
        UserInfoEntity user = userInfoRepository.findByUsername(TenantInfoContext.getTenantId(), credential.getUsername())
                .orElseThrow(() -> new AuthenticationException("用户名或密码错误"));
        return null;
    }
}
