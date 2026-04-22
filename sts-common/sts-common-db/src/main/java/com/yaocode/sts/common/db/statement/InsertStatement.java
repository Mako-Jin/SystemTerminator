package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 21:52
 */
public class InsertStatement extends AbstractSqlStatement {

    private static final String INSERT_INTO_KEY_WORD_REGEX = "^INSERT\\s+INTO\\s+";

    public InsertStatement() {
    }

    public InsertStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, SymbolConstants.EMPTY_STR);
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst(INSERT_INTO_KEY_WORD_REGEX, SymbolConstants.EMPTY_STR);
        return extractFirstIdentifier(tablePart);
    }
}
