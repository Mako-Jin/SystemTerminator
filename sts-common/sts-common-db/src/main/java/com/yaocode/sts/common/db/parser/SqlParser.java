package com.yaocode.sts.common.db.parser;

import com.yaocode.sts.common.db.statement.SqlStatement;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 20:17
 */
public interface SqlParser {

    /**
     * 转换sql内容
     * @param sqlContent sql内容
     * @return List<SqlStatement>
     */
    List<SqlStatement> parseSqlContent (String sqlContent);

}
