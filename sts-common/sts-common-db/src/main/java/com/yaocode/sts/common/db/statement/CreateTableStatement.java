package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 21:53
 */
public class CreateTableStatement extends AbstractSqlStatement {

    public CreateTableStatement() {
    }

    public CreateTableStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        // 移除 CREATE TABLE IF NOT EXISTS 等前缀
        String tablePart = getSql().replaceFirst("^CREATE\\s+TABLE\\s+(IF\\s+NOT\\s+EXISTS\\s+)?", "");

        // 获取表名（可能是 `table_name` 或 table_name）
        return extractFirstIdentifier(tablePart);
    }


}
