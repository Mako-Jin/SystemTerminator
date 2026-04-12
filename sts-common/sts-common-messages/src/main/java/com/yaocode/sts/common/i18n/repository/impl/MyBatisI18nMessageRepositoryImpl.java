package com.yaocode.sts.common.i18n.repository.impl;

import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * mybatis实现
 * @author: Jin-LiangBo
 * @date: 2026年04月09日 18:53
 */
public class MyBatisI18nMessageRepositoryImpl implements I18nMessageRepository {

    private final SqlSessionFactory sqlSessionFactory;

    public MyBatisI18nMessageRepositoryImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public String getMessage(String code, String locale) {
        return null;
    }

    public SqlSessionFactory getSqlSessionFactory() {
        return sqlSessionFactory;
    }
}
