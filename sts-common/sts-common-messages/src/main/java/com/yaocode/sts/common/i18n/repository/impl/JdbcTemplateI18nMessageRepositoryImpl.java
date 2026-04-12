package com.yaocode.sts.common.i18n.repository.impl;

import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBCTemplate实现
 * @author: Jin-LiangBo
 * @date: 2026年04月08日 16:47
 */
public class JdbcTemplateI18nMessageRepositoryImpl implements I18nMessageRepository {

    private static final String TABLE_NAME = "aux_i18n_resource";

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateI18nMessageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getMessage(String code, String locale) {
        return null;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
