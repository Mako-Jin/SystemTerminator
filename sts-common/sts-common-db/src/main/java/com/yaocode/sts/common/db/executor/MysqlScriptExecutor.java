package com.yaocode.sts.common.db.executor;

import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.statement.SqlStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月07日 22:32
 */
public class MysqlScriptExecutor extends AbstractSqlScriptExecutor {

    private static final Logger logger = LoggerFactory.getLogger(MysqlScriptExecutor.class);

    public MysqlScriptExecutor(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return null;
    }

    /**
     * 检查数据库是否存在
     */
    @Override
    public boolean checkDatabaseExists() {
        String checkSql = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
        try {
            if (Objects.isNull(getSystemConnection())) {
                logger.error("数据库系统连接为空");
                return false;
            }
            PreparedStatement stmt = getSystemConnection().prepareStatement(checkSql);
            stmt.setString(1, getDatabaseName());
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            logger.error("检查数据库存在性失败", e);
            return false;
        }
    }

    @Override
    public void createDatabase() {
        String createSql = String.format(
                "CREATE DATABASE IF NOT EXISTS `%s` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci",
                getDatabaseName()
        );
        try {
            if (Objects.isNull(getSystemConnection())) {
                logger.error("数据库系统连接为空");
                return;
            }
            Statement stmt = getSystemConnection().createStatement();
            stmt.execute(createSql);
        } catch (SQLException e) {
            logger.error("数据库创建失败", e);
        }
    }

    @Override
    public boolean checkTableExists(String tableName) {
        if (tableName == null || SqlConstants.UNKNOWN.equals(tableName)) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";
        try {
            if (Objects.isNull(getSystemConnection())) {
                logger.error("数据库系统连接为空");
                return false;
            }
            try (
                PreparedStatement prepareStatement = getSystemConnection().prepareStatement(sql)
            ) {
                prepareStatement.setString(1, tableName);
                try (ResultSet rs = prepareStatement.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    @Override
    public DataSource createDataSource() {
        return null;
    }

    @Override
    public boolean checkDbStatus() {
        return false;
    }

    @Override
    public boolean initVersionTables() {
        return false;
    }

    @Override
    public void loadScripts() {

    }

    @Override
    public boolean detectConflicts() {
        return false;
    }

    @Override
    public void executeMigration() {

    }

    @Override
    public void executeScript(SqlStatement sqlStatement) {
        if (Objects.isNull(getConnection())) {
            logger.warn("数据库连接为空");
            return;
        }
        int status = 0;
        String errorMessage = "";
        LocalDateTime startTime = LocalDateTime.now();
        try (Statement stmt = getConnection().createStatement()) {
            stmt.execute(sqlStatement.getSql());
            status = 1;
        } catch (SQLException e) {
            logger.error("初始化执行记录表失败: " + e.getMessage());
            errorMessage = e.getMessage();
        }
        LocalDateTime endTime = LocalDateTime.now();
        String insertSqlHistory = "insert into script_history (`file_name`, " +
                "`script_content`, `script_type`, `security_level`, `status`, `error_message`, " +
                "`execute_time`, " +
                "`create_time`) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(insertSqlHistory)) {
            prepareStatement.setString(1, sqlStatement.getResourceName());
            prepareStatement.setString(2, sqlStatement.getSql());
            prepareStatement.setInt(3, sqlStatement.getCategory().getType());
            prepareStatement.setInt(4, sqlStatement.getSecurityLevel().getLevel());
            prepareStatement.setInt(5, status);
            prepareStatement.setString(6, errorMessage);
            prepareStatement.setLong(7, Duration.between(startTime, endTime).toMillis());
            prepareStatement.setTimestamp(8, Timestamp.valueOf(endTime));

            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("执行记录数据插入失败: " + e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        this.setSystemConnection(null);
        this.setConnection(null);
    }

    // @Override
    // public void executeScript(DataSource dataSource) {
    //
    // }
}
