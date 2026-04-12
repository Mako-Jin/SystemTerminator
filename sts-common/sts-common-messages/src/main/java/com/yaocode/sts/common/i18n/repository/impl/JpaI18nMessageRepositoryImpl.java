package com.yaocode.sts.common.i18n.repository.impl;

import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import jakarta.persistence.EntityManagerFactory;

/**
 * JPA实现
 * @author: Jin-LiangBo
 * @date: 2026年04月09日 19:01
 */
public class JpaI18nMessageRepositoryImpl implements I18nMessageRepository {

    private final EntityManagerFactory entityManagerFactory;

    public JpaI18nMessageRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public String getMessage(String code, String locale) {
        return null;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
