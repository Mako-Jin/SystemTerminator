package com.yaocode.sts.common.db.listener;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.ScriptLoader;
import com.yaocode.sts.common.db.constants.CommonConstants;
import com.yaocode.sts.common.db.events.DbMigrationInitEvent;
import com.yaocode.sts.common.db.executor.SqlScriptExecutor;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import com.yaocode.sts.common.db.statement.CreateTableStatement;
import com.yaocode.sts.common.db.statement.SqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月07日 21:14
 */
public class DbMigrationInitListener {

    private static final Logger logger = LoggerFactory.getLogger(DbMigrationInitListener.class);

    @Order(1)
    @EventListener
    public void handleDbVersionTableInitEvent(DbMigrationInitEvent dbMigrationInitEvent) {
        DbMigrationEngine dbMigrationEngine = dbMigrationInitEvent.getSource();
        if (Objects.isNull(dbMigrationEngine)) {
            logger.warn("engine is null");
            return;
        }
        DbMigrationProperties properties = dbMigrationEngine.getProperties();
        if (!properties.isEnabled()) {
            logger.info("Database migration is disabled");
            return;
        }
        SqlScriptExecutor sqlScriptExecutor = dbMigrationEngine.getScriptExecutor();
        if (!sqlScriptExecutor.checkDatabaseExists()) {
            // TODO 得先有个有权限的账号，如果人家没配置root，你这就没办法，还是先建一个账号，然后删除
            sqlScriptExecutor.createDatabase();
        }
        ScriptLoader scriptLoader = dbMigrationEngine.getScriptLoader();
        String defaultSqlLocation = properties.getDefaultSqlScriptLocation();
        try {
            List<Resource> resources = scriptLoader.loadScript(defaultSqlLocation, this::defaultPredict);
            if (resources.isEmpty()) {
                logger.info("版本控制的脚本加载为空");
                return;
            }
            List<SqlStatement> statements = new ArrayList<>();
            for (Resource resource : resources) {
                List<SqlStatement> currentStatements = scriptLoader.loadSql(resource);
                if (currentStatements.isEmpty()) {
                    continue;
                }
                statements.addAll(currentStatements);
            }
            for (SqlStatement statement : statements) {
                if (!(statement instanceof CreateTableStatement)) {
                    if (sqlScriptExecutor.checkSqlExecuteStatus(statement.getSql())) {
                        continue;
                    }
                    if (!sqlScriptExecutor.checkTableExists(statement.getTableName())) {
                        sqlScriptExecutor.executeScript(statement);
                    }
                }
                if (!statement.getTableName().equalsIgnoreCase(CommonConstants.SCRIPT_CONTROL_TABLE_NAME)) {
                    continue;
                }
                if (sqlScriptExecutor.checkTableExists(statement.getTableName())) {
                    continue;
                }
                sqlScriptExecutor.executeScript(statement);
            }
        } catch (IOException | URISyntaxException ioException) {
            logger.info("版本控制的脚本加载出错 => {}", ioException.getMessage());
        }
    }

    private boolean defaultPredict(Resource resource) {
        try {
            return resource.getURL().getPath().contains(CommonConstants.JAR_PACKAGE_NAME);
        } catch (IOException ioException) {
            logger.info("版本控制的脚本加载出错 => {}", ioException.getMessage());
            return false;
        }
    }

    @Order(2)
    @EventListener
    public void prepareDbMigrationEnv(DbMigrationInitEvent dbMigrationInitEvent) {
    }

}
