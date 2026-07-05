package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.composites.AuthenticationToken;

/**
 * 菜单权限服务
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:14
 */
public interface AuthDomainService {

    /**
     * 认证
     * @param credential 认证凭证
     * @return 认证令牌
     */
    AuthenticationToken authenticate(AbstractAuthCredential credential);

}
