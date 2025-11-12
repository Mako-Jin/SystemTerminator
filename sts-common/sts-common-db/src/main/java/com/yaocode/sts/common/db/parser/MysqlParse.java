package com.yaocode.sts.common.db.parser;

import com.yaocode.sts.common.db.enums.StatementEnums;
import com.yaocode.sts.common.db.statement.AlterStatement;
import com.yaocode.sts.common.db.statement.CreateTableStatement;
import com.yaocode.sts.common.db.statement.DeleteStatement;
import com.yaocode.sts.common.db.statement.DropTableStatement;
import com.yaocode.sts.common.db.statement.InsertStatement;
import com.yaocode.sts.common.db.statement.SelectStatement;
import com.yaocode.sts.common.db.statement.SqlStatement;
import com.yaocode.sts.common.db.statement.UnknownSqlStatement;
import com.yaocode.sts.common.db.statement.UpdateStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * mysql sql 转换器
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 20:17
 */
public class MysqlParse extends AbstractSqlParser {

    @Override
    public List<SqlStatement> parseSqlContent(String sqlContent) {
        List<SqlStatement> statements = new ArrayList<>();
        StringBuilder currentStatement = new StringBuilder();
        String[] lines = sqlContent.split("\r\n");

        boolean inMultiLineComment = false;
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            // 看是不是注释
            if (inMultiLineComment) {
                if (line.contains("*/")) {
                    inMultiLineComment = false;
                    line = line.substring(line.indexOf("*/") + 2);
                } else {
                    continue;
                }
            }

            // 移除单行注释
            line = removeSingleLineComments(line);

            // 检查多行注释开始
            if (line.contains("/*")) {
                inMultiLineComment = true;
                line = line.substring(0, line.indexOf("/*"));
            }

            if (line.isEmpty()) {
                continue;
            }

            currentStatement.append(line).append(" ");

            // 检查语句结束
            if (line.endsWith(";")) {
                String sql = currentStatement.toString().trim();
                // 确保不是只有分号
                if (sql.length() > 1) {
                    // 移除末尾分号
                    sql = sql.substring(0, sql.length() - 1);
                    SqlStatement statement = analyzeSql(sql);
                    statements.add(statement);
                }
                // 清空当前语句
                currentStatement.setLength(0);
            }
        }
        return statements;
    }

    private String removeSingleLineComments(String line) {
        // 移除 -- 注释
        int commentIndex = line.indexOf("--");
        if (commentIndex != -1) {
            line = line.substring(0, commentIndex);
        }
        return line.trim();
    }

    private SqlStatement analyzeSql(String sql) {
        String upperSql = sql.toUpperCase().trim();
        SqlStatement sqlStatement;
        if (upperSql.startsWith(StatementEnums.CREATE.getKeyword())) {
            sqlStatement =  new CreateTableStatement(sql, StatementEnums.CREATE.getSecurityLevel(), StatementEnums.CREATE.getCategory());
        } else if (upperSql.startsWith(StatementEnums.DROP.getKeyword())) {
            sqlStatement =  new DropTableStatement(sql, StatementEnums.DROP.getSecurityLevel(), StatementEnums.DROP.getCategory());
        } else if (upperSql.startsWith(StatementEnums.INSERT.getKeyword())) {
            sqlStatement =  new InsertStatement(sql, StatementEnums.INSERT.getSecurityLevel(), StatementEnums.INSERT.getCategory());
        } else if (upperSql.startsWith(StatementEnums.SELECT.getKeyword())) {
            sqlStatement =  new SelectStatement(sql, StatementEnums.SELECT.getSecurityLevel(), StatementEnums.SELECT.getCategory());
        } else if (upperSql.startsWith(StatementEnums.UPDATE.getKeyword())) {
            sqlStatement =  new UpdateStatement(sql, StatementEnums.UPDATE.getSecurityLevel(), StatementEnums.UPDATE.getCategory());
        } else if (upperSql.startsWith(StatementEnums.DELETE.getKeyword())) {
            sqlStatement =  new DeleteStatement(sql, StatementEnums.DELETE.getSecurityLevel(), StatementEnums.DELETE.getCategory());
        } else if (upperSql.startsWith(StatementEnums.ALTER.getKeyword())) {
            sqlStatement =  new AlterStatement(sql, StatementEnums.ALTER.getSecurityLevel(), StatementEnums.ALTER.getCategory());
        } else {
            sqlStatement = new UnknownSqlStatement();
        }

        String tableName = sqlStatement.extractTableName();
        sqlStatement.setTableName(tableName);
        return sqlStatement;
    }

}
