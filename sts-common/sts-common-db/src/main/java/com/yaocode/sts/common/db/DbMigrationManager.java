package com.yaocode.sts.common.db;

import com.yaocode.sts.common.db.enums.MigrationStateEnums;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2025年11月02日 17:42
 * @Description:
 */
public class DbMigrationManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DbMigrationManager.class);

    private final AtomicReference<MigrationStateEnums> migrationState = new AtomicReference<>(MigrationStateEnums.PENDING);

    private DataSource dataSource;
    private ApplicationContext applicationContext;
    private DbMigrationExecutor migrationExecutor;
    private DbMigrationProperties properties;

    public DbMigrationManager(DataSource dataSource, DbMigrationProperties properties) {
        this.setDataSource(dataSource);
        this.properties = properties;
    }

    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = ((TransactionAwareDataSourceProxy)dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }

    }

    public DataSource getDataSource() {
        return dataSource;
    }

    // @Override
    // public DbMigrationExecutor getObject() throws Exception {
    //     return null;
    // }

    // @Override
    // public Class<?> getObjectType() {
    //     return null;
    // }
    //
    // @Override
    // public boolean isSingleton() {
    //     return FactoryBean.super.isSingleton();
    // }
    //
    // @Override
    // public void onApplicationEvent(ApplicationReadyEvent event) {
    //     if (!migrationState.compareAndSet(MigrationStateEnums.PENDING, MigrationStateEnums.RUNNING)) {
    //         logger.debug("Migration already in progress or completed, skipping");
    //         return;
    //     }
    //
    //     logger.info("Starting database migration process...");
    //     eventPublisher.publishEvent(new DbMigrationStartedEvent(this));
    //
    //     try {
    //         startMigration();
    //         migrationState.set(MigrationStateEnums.COMPLETED);
    //         eventPublisher.publishEvent(new DbMigrationCompletedEvent(this));
    //         logger.info("Database migration completed successfully");
    //
    //     } catch (Exception e) {
    //         migrationState.set(MigrationStateEnums.FAILED);
    //         eventPublisher.publishEvent(new DbMigrationFailedEvent(this, e));
    //         logger.error("Database migration failed", e);
    //
    //         // 根据配置决定是否抛出异常
    //         if (!properties.isIgnoreErrors()) {
    //             throw new DbMigrationException("Database migration failed", e);
    //         }
    //     }
    // }

    // @Override
    // public boolean supportsAsyncExecution() {
    //     return ApplicationListener.super.supportsAsyncExecution();
    // }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Initializing DbMigrationManager...");

        if (!properties.isEnabled()) {
            logger.info("Database migration is disabled");
            return;
        }

        // 1. 获取DataSource
        this.dataSource = this.getDataSource();
        if (dataSource == null) {
            logger.warn("No DataSource found, database migration will be skipped");
            return;
        }

        // this.migrationExecutor = createMigrationExecutor();
        //
        // initializeVersionTables();

        logger.info("DbMigrationManager initialized successfully");
    }

    private void startMigration() {
        logger.info("startMigration");
        // MigrationResult result = migrationEngine.executeMigration(dbContexts);
        // 保存历史记录
    }

    // @Override
    // public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    //     this.applicationContext = applicationContext;
    // }
}
