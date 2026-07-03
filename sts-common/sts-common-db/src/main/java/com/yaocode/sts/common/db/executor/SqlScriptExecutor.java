package com.yaocode.sts.common.db.executor;

import com.yaocode.sts.common.db.statement.SqlStatement;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 15:29
 */
public interface SqlScriptExecutor {

    /**
     * 获取当前jdbcTemplate
     * @return JdbcTemplate
     */
    JdbcTemplate getJdbcTemplate();

    /**
     * 检查数据库存不存在
     * @return boolean
     */
    boolean checkDatabaseExists();

    /**
     * 创建数据库
     */
    void createDatabase();

    /**
     * 检查数据库表存不存在
     * @param tableName 表名
     * @return boolean
     */
    boolean checkTableExists(String tableName);

    /**
     * 执行sql脚本，无返回
     * @param sqlStatement sql语句
     */
    void executeScript(SqlStatement sqlStatement);

    /**
     * 查询表的数据量
     * @param tableName 表名
     * @return long
     */
    long getTableDataCount(String tableName);

    /**
     * 检查sql执行过没
     * @param sql 脚本
     * @return boolean
     */
    boolean checkSqlExecuteStatus(String sql);

}
