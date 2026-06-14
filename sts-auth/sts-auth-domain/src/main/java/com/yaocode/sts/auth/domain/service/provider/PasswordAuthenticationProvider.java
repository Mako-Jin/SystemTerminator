package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordAuthCredential;
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
    public boolean supports(AbstractAuthCredential credential) {
        return super.supports(credential);
    }

//    @Override
//    protected UserInfoEntity doAuthenticate(AuthenticationToken authentication) {
//        // 查询用户
//        UserInfoEntity user = userInfoRepository.findByUsername(TenantInfoContext.getTenantId(), credential.getUsername())
//                .orElseThrow(() -> new AuthenticationException("用户名或密码错误"));
//        return null;
//    }

    @Override
    protected Optional<UserInfoEntity> doAuthenticate(PasswordAuthCredential credential) {
        return Optional.empty();
    }
}
