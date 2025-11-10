package com.yaocode.sts.common.db.executor;

import com.yaocode.sts.common.db.statement.SqlStatement;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

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
     * @param dataSource 数据库信息
     * @return boolean
     */
    boolean checkDatabaseExists();

    /**
     * 创建数据库
     * @param dataSource 数据库信息
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
     * @param sqlStatement
     */
    void executeScript(SqlStatement sqlStatement);

    DataSource createDataSource();

    boolean checkDbStatus();

    boolean initVersionTables();

    void loadScripts();

    boolean detectConflicts();

    void executeMigration();


}
