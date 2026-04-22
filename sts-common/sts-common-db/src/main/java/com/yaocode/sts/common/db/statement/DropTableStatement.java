package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:26
 */
public class DropTableStatement extends AbstractSqlStatement {

    private static final String DROP_KEY_WORD_REGEX = "^DROP\\s+TABLE\\s+(IF\\s+EXISTS\\s+)?";

    public DropTableStatement() {
    }

    public DropTableStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst(DROP_KEY_WORD_REGEX, SymbolConstants.EMPTY_STR);
        return extractFirstIdentifier(tablePart);
    }
}
