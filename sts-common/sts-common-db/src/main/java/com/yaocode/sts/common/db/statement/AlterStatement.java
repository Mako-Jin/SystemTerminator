package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:40
 */
public class AlterStatement extends AbstractSqlStatement {

    private static final String ALTER_KEY_WORD_REGEX = "^ALTER\\s+TABLE\\s+";

    public AlterStatement() {
    }

    public AlterStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, SymbolConstants.EMPTY_STR);
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst(ALTER_KEY_WORD_REGEX, SymbolConstants.EMPTY_STR);
        return extractFirstIdentifier(tablePart);
    }
}
