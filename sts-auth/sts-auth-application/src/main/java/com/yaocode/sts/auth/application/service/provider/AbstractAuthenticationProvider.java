package com.yaocode.sts.auth.application.service.provider;

import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;

/**
 * provider抽象实现类
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:52
 */
public abstract class AbstractAuthenticationProvider<T extends AbstractAuthCredential> implements AuthenticationProvider<T> {
}
