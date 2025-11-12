package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:40
 */
public class UpdateStatement extends AbstractSqlStatement {

    private static final String SET_KEY_WORLD = " " + SqlConstants.SET + " ";

    public UpdateStatement() {
    }

    public UpdateStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst("^UPDATE\\s+", "");
        // 处理 SET 子句之前的部分
        if (tablePart.contains(SET_KEY_WORLD)) {
            tablePart = tablePart.substring(0, tablePart.indexOf(SET_KEY_WORLD));
        }
        return extractFirstIdentifier(tablePart);
    }
}
