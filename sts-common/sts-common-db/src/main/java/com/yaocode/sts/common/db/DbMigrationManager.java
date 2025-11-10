package com.yaocode.sts.common.db;

import com.yaocode.sts.common.db.enums.MigrationStateEnums;
import com.yaocode.sts.common.db.events.DbMigrationCompletedEvent;
import com.yaocode.sts.common.db.events.DbMigrationFailedEvent;
import com.yaocode.sts.common.db.events.DbMigrationInitEvent;
import com.yaocode.sts.common.db.events.DbMigrationStartEvent;
import com.yaocode.sts.common.db.exception.DbMigrationException;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 17:42
 */
public class DbMigrationManager implements InitializingBean, ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(DbMigrationManager.class);

    private final AtomicReference<MigrationStateEnums> migrationState = new AtomicReference<>(MigrationStateEnums.PENDING);

    private DataSource dataSource;
    private DbMigrationEngine dbMigrationEngine;
    private final DbMigrationProperties properties;
    private ApplicationContext applicationContext;

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

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!migrationState.compareAndSet(MigrationStateEnums.PENDING, MigrationStateEnums.PREPARED)) {
            logger.debug("Migration already in progress or completed, skipping");
            return;
        }

        logger.info("Starting database migration process...");
        try {
            applicationContext.publishEvent(new DbMigrationInitEvent(this.dbMigrationEngine));
            migrationState.set(MigrationStateEnums.RUNNING);
            applicationContext.publishEvent(new DbMigrationStartEvent(this.dbMigrationEngine));
            migrationState.set(MigrationStateEnums.COMPLETED);
            applicationContext.publishEvent(new DbMigrationCompletedEvent(this.dbMigrationEngine));
            logger.info("Database migration completed successfully");

        } catch (Exception e) {
            migrationState.set(MigrationStateEnums.FAILED);
            applicationContext.publishEvent(new DbMigrationFailedEvent(this.dbMigrationEngine, e));
            logger.error("Database migration failed", e);

            // 根据配置决定是否抛出异常
            if (!properties.isIgnoreErrors()) {
                throw new DbMigrationException("Database migration failed", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
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

        this.dbMigrationEngine = createDbMigrationEngine();

        logger.info("DbMigrationManager initialized successfully");
    }

    private DbMigrationEngine createDbMigrationEngine() {
        return new DbMigrationEngine(
                applicationContext,
                properties,
                dataSource
        );
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
