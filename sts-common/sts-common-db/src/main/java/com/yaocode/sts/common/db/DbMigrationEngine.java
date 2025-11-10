package com.yaocode.sts.common.db;

import com.yaocode.sts.common.db.executor.MysqlScriptExecutor;
import com.yaocode.sts.common.db.executor.SqlScriptExecutor;
import com.yaocode.sts.common.db.parser.MysqlParse;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;

/**
 * 数据库迁移脚本引擎
 * @author: Jin-LiangBo
 * @date: 2025年11月05日 22:34
 */
public class DbMigrationEngine {

    private ApplicationContext applicationContext;
    private DbMigrationProperties properties;
    private DataSource dataSource;

    private final ScriptLoader scriptLoader = new ScriptLoader(new MysqlParse());
    private final SqlScriptExecutor scriptExecutor;

    // private final ScriptValidator scriptValidator = new ScriptValidator();
    // private final ConflictDetector conflictDetector = new ConflictDetector();
    // private final BackupService backupService = new BackupService();
    // private final HealthChecker healthChecker = new HealthChecker();


    public DbMigrationEngine(ApplicationContext applicationContext, DbMigrationProperties properties, DataSource dataSource) {
        this.applicationContext = applicationContext;
        this.properties = properties;
        this.dataSource = dataSource;
        scriptExecutor = new MysqlScriptExecutor(dataSource);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setProperties(DbMigrationProperties properties) {
        this.properties = properties;
    }

    public DbMigrationProperties getProperties() {
        return properties;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public ScriptLoader getScriptLoader() {
        return scriptLoader;
    }

    public void setPrimaryDataSource(DataSource primaryDataSource) {
        this.dataSource = primaryDataSource;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlScriptExecutor getScriptExecutor() {
        return scriptExecutor;
    }
}
