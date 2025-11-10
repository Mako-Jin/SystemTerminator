package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 22:38
 */
public abstract class AbstractSqlStatement implements SqlStatement {

    private String resourceName;
    private String sql;
    private SqlSecurityLevelEnums securityLevel;
    private SqlScriptTypeEnums category;
    private String tableName;

    public AbstractSqlStatement() {
    }

    public AbstractSqlStatement(String sql, SqlSecurityLevelEnums securityLevel, SqlScriptTypeEnums category, String tableName) {
        this.sql = sql;
        this.securityLevel = securityLevel;
        this.category = category;
        this.tableName = tableName;
    }

    /**
     * 提取第一个标识符（表名），处理反引号、方括号等引用符号
     */
    protected static String extractFirstIdentifier(String sqlPart) {
        if (sqlPart == null || sqlPart.trim().isEmpty()) {
            return SqlConstants.UNKNOWN;
        }

        String trimmed = sqlPart.trim();

        // 处理反引号 `table_name`
        if (trimmed.startsWith("`")) {
            int endIndex = trimmed.indexOf("`", 1);
            if (endIndex != -1) {
                return trimmed.substring(1, endIndex);
            }
        }
        // 处理方括号 [table_name]
        else if (trimmed.startsWith("[")) {
            int endIndex = trimmed.indexOf("]", 1);
            if (endIndex != -1) {
                return trimmed.substring(1, endIndex);
            }
        }
        // 处理双引号 "table_name"
        else if (trimmed.startsWith("\"")) {
            int endIndex = trimmed.indexOf("\"", 1);
            if (endIndex != -1) {
                return trimmed.substring(1, endIndex);
            }
        }

        // 普通表名，取第一个单词（遇到空格、分号、括号等结束）
        StringBuilder tableName = new StringBuilder();
        for (char c : trimmed.toCharArray()) {
            if (Character.isWhitespace(c) || c == '(' || c == ';') {
                break;
            }
            tableName.append(c);
        }

        String result = tableName.toString().trim();
        return result.isEmpty() ? SqlConstants.UNKNOWN : result;
    }

    @Override
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public SqlSecurityLevelEnums getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(SqlSecurityLevelEnums securityLevel) {
        this.securityLevel = securityLevel;
    }

    @Override
    public SqlScriptTypeEnums getCategory() {
        return category;
    }

    public void setCategory(SqlScriptTypeEnums category) {
        this.category = category;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    @Override
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
