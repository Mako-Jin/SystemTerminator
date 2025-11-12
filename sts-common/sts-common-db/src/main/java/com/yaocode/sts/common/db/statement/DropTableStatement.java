package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:26
 */
public class DropTableStatement extends AbstractSqlStatement {

    public DropTableStatement() {
    }

    public DropTableStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        String tablePart = getSql().replaceFirst("^DROP\\s+TABLE\\s+(IF\\s+EXISTS\\s+)?", "");
        return extractFirstIdentifier(tablePart);
    }
}
