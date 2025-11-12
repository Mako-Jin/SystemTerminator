package com.yaocode.sts.common.db.listener;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.ScriptLoader;
import com.yaocode.sts.common.db.constants.CommonConstants;
import com.yaocode.sts.common.db.events.DbMigrationStartEvent;
import com.yaocode.sts.common.db.executor.SqlScriptExecutor;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import com.yaocode.sts.common.db.statement.CreateTableStatement;
import com.yaocode.sts.common.db.statement.DropTableStatement;
import com.yaocode.sts.common.db.statement.SqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月11日 22:19
 */
public class DbMigrationStartListener {

    private static final Logger logger = LoggerFactory.getLogger(DbMigrationStartListener.class);

    @Order(3)
    @EventListener
    public void handleDbMigrationLoadScriptEvent(DbMigrationStartEvent dbMigrationStartEvent) throws IOException, URISyntaxException {
        DbMigrationEngine dbMigrationEngine = dbMigrationStartEvent.getSource();
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
        ScriptLoader scriptLoader = dbMigrationEngine.getScriptLoader();
        List<String> sqlScriptPaths = new ArrayList<>(Arrays.asList(properties.getSqlScriptPath()));
        if (sqlScriptPaths.isEmpty()) {
            sqlScriptPaths.add(properties.getDefaultSqlScriptLocation());
        }
        List<Resource> allResources = new ArrayList<>();
        for (String sqlScriptPath : sqlScriptPaths) {
            List<Resource> resources = scriptLoader.loadScript(sqlScriptPath, this::defaultPredict);
            allResources.addAll(resources);
        }
        if (allResources.isEmpty()) {
            logger.info("sql脚本加载为空");
            return;
        }
        List<SqlStatement> statements = new ArrayList<>();
        for (Resource resource : allResources) {
            List<SqlStatement> currentStatements = scriptLoader.loadSql(resource);
            if (currentStatements.isEmpty()) {
                continue;
            }
            statements.addAll(currentStatements);
        }
        for (SqlStatement statement : statements) {
            if (sqlScriptExecutor.checkSqlExecuteStatus(statement.getSql())) {
                continue;
            }
            if (statement instanceof DropTableStatement) {
                // 需要检查表存在，数据是不是空
                if (sqlScriptExecutor.checkTableExists(statement.getTableName())) {
                    if (sqlScriptExecutor.getTableDataCount(statement.getTableName()) > 0) {
                        logger.warn("当前表={}的数据不为空，不执行删除", statement.getTableName());
                        continue;
                    }
                }
            } else if (statement instanceof CreateTableStatement) {
                if (sqlScriptExecutor.checkTableExists(statement.getTableName())) {
                    continue;
                }
            } else {
                if (!sqlScriptExecutor.checkTableExists(statement.getTableName())) {
                    continue;
                }
            }
            sqlScriptExecutor.executeScript(statement);
        }
    }

    private boolean defaultPredict(Resource resource) {
        try {
            return !resource.getURL().getPath().contains(CommonConstants.JAR_PACKAGE_NAME);
        } catch (IOException ioException) {
            logger.info("版本控制的脚本加载出错 => {}", ioException.getMessage());
            return false;
        }
    }

    @Order(4)
    @EventListener
    public void handleDbMigrationExecuteEvent(DbMigrationStartEvent dbMigrationStartEvent) {
        logger.info("DbMigrationStartListener 44444444444444");
    }

}
