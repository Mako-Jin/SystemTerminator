package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:40
 */
public class AlterStatement extends AbstractSqlStatement {

    public AlterStatement() {
    }

    public AlterStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst("^ALTER\\s+TABLE\\s+", "");
        return extractFirstIdentifier(tablePart);
    }
}
