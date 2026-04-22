package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:40
 */
public class DeleteStatement extends AbstractSqlStatement {

    private static final String WHERE_KEY_WORLD = SymbolConstants.SPACE_STR + SqlConstants.WHERE + SymbolConstants.SPACE_STR;

    private static final String DELETE_KEY_WORD_REGEX = "DELETE\\s+FROM\\s+";

    public DeleteStatement() {
    }

    public DeleteStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, SymbolConstants.EMPTY_STR);
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst(DELETE_KEY_WORD_REGEX, SymbolConstants.EMPTY_STR);
        // 处理 WHERE 子句之前的部分
        if (tablePart.contains(WHERE_KEY_WORLD)) {
            tablePart = tablePart.substring(0, tablePart.indexOf(WHERE_KEY_WORLD));
        }
        return extractFirstIdentifier(tablePart);
    }
}
