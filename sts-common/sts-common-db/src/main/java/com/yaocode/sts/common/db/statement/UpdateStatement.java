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
public class UpdateStatement extends AbstractSqlStatement {

    private static final String UPDATE_KEY_WORD_REGEX = "^UPDATE\\s+";

    private static final String SET_KEY_WORLD = SymbolConstants.SPACE_STR + SqlConstants.SET + SymbolConstants.SPACE_STR;

    public UpdateStatement() {
    }

    public UpdateStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, SymbolConstants.EMPTY_STR);
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst(UPDATE_KEY_WORD_REGEX, SymbolConstants.EMPTY_STR);
        // 处理 SET 子句之前的部分
        if (tablePart.contains(SET_KEY_WORLD)) {
            tablePart = tablePart.substring(0, tablePart.indexOf(SET_KEY_WORLD));
        }
        return extractFirstIdentifier(tablePart);
    }
}
