package com.yaocode.sts.common.i18n.repository.impl;

import com.yaocode.sts.common.i18n.constants.I18nKeyConstants;
import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBCTemplate实现
 * @author: Jin-LiangBo
 * @date: 2026年04月08日 16:47
 */
@Getter
public class JdbcTemplateI18nMessageRepositoryImpl implements I18nMessageRepository {

    private static final String TABLE_NAME = I18nKeyConstants.TABLE_I18N_RESOURCE;

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateI18nMessageRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getMessage(String code, String locale) {
        return null;
    }

}
