package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月12日 20:36
 */
public class SelectStatement extends AbstractSqlStatement {

    private static final String FROM_KEY_WORLD = " " + SqlConstants.FROM + " ";

    public SelectStatement() {
    }

    public SelectStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category) {
        super(sql, securityLevel, category, "");
    }

    @Override
    public String extractTableName() {
        // 简单的FROM子句提取（实际可能需要更复杂的解析）
        if (getSql().contains(FROM_KEY_WORLD)) {
            String fromPart = getSql().substring(getSql().indexOf(FROM_KEY_WORLD) + 6);
            // 处理可能的WHERE、GROUP BY等子句
            String[] endMarkers = {" WHERE ", " GROUP BY ", " ORDER BY ", " LIMIT ", ";"};
            for (String marker : endMarkers) {
                if (fromPart.contains(marker)) {
                    fromPart = fromPart.substring(0, fromPart.indexOf(marker));
                }
            }
            return extractFirstIdentifier(fromPart.trim());
        }
        return "UNKNOWN";
    }
}
