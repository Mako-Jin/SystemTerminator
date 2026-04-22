package com.yaocode.sts.common.db.executor;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.enums.DBTypeEnums;
import com.yaocode.sts.common.db.exception.UnsupportedDbTypeException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * TODO 还没考虑事务，万一执行失败了怎么办
 * @author: Jin-LiangBo
 * @date: 2025年11月08日 20:51
 */
public abstract class AbstractSqlScriptExecutor implements SqlScriptExecutor, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSqlScriptExecutor.class);

    private DataSource dataSource;

    private Connection systemConnection;

    private Connection connection;

    public AbstractSqlScriptExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
        this.systemConnection = createSystemConnection();
    }

    private Connection createSystemConnection() {
        DataSource dataSource = createSystemDataSource();
        try {
            return dataSource.getConnection();
        } catch (SQLException sqlException) {
            logger.error("Failed to obtain database connection => {}", sqlException.getMessage());
        }
        return null;
    }

    private Connection createConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException sqlException) {
            logger.error("Failed to obtain database connection => {}", sqlException.getMessage());
        }
        return null;
    }

    private String extractBaseUrl(String url) {
        if (url.contains(SymbolConstants.QUESTION_MARKS)) {
            return url.split(SymbolConstants.QUESTION_MARKS)[0];
        }
        return url;
    }

    private String extractDatabaseName(String url) {
        String baseUrl = extractBaseUrl(url);
        if (baseUrl.contains(SymbolConstants.FORWARD_SLASH)) {
            return baseUrl.substring(baseUrl.lastIndexOf(SymbolConstants.FORWARD_SLASH) + 1);
        }
        return SymbolConstants.EMPTY_STR;
    }

    protected String getDatabaseName() {
        // TODO 也不应该每次都计算一次吧
        String database = "";
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            database = extractDatabaseName(hikariDataSource.getJdbcUrl());
        }
        // TODO 种类比较多，后边在加，也许应该写个Default或者一个抽象类，因为其他类型数据库也会用这个
        return database;
    }

    protected String getJdbcUrl() {
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            return hikariDataSource.getJdbcUrl();
        }
        return SymbolConstants.EMPTY_STR;
    }

    private String createSystemDatabaseUrl(String datasourceUrl) {
        if (datasourceUrl.contains(DBTypeEnums.MYSQL.getName())) {
            // 替换数据库名为 mysql 系统数据库，或者移除数据库名
            return addPublicKeyRetrieval(datasourceUrl.replaceFirst("/[^/?]+([?]|$)", "/mysql$1"));
        } else if (datasourceUrl.contains(DBTypeEnums.POSTGRESQL.getName())) {
            // PostgreSQL 连接到 postgres 系统数据库
            return datasourceUrl.replaceFirst("/[^/?]+([?]|$)", "/postgres$1");
        }
        // 默认移除数据库名部分
        return removeDatabaseFromUrl(datasourceUrl);
    }

    public String addPublicKeyRetrieval(String originalUrl) {
        if (originalUrl == null) {
            return null;
        }

        StringBuilder modifiedUrl = new StringBuilder(originalUrl);

        // 检查是否已有参数
        boolean hasParams = originalUrl.contains(SymbolConstants.QUESTION_MARKS);

        // 添加 allowPublicKeyRetrieval
        if (!originalUrl.contains("allowPublicKeyRetrieval")) {
            modifiedUrl.append(hasParams ? "&" : "?").append("allowPublicKeyRetrieval=true");
            hasParams = true;
        }

        // 添加 useSSL
        if (!originalUrl.contains("useSSL")) {
            modifiedUrl.append(hasParams ? "&" : "?").append("useSSL=false");
            hasParams = true;
        }

        // 添加 serverTimezone（对于MySQL 8.x很重要）
        if (!originalUrl.contains("serverTimezone")) {
            modifiedUrl.append("&serverTimezone=UTC");
        }

        return modifiedUrl.toString();
    }

    /**
     * 从URL中移除数据库名
     */
    private String removeDatabaseFromUrl(String url) {
        // 处理 jdbc:mysql://host:port/dbname?params
        if (url.contains(SymbolConstants.FORWARD_SLASH)) {
            String base = url.substring(0, url.lastIndexOf(SymbolConstants.FORWARD_SLASH));
            if (url.contains(SymbolConstants.QUESTION_MARKS)) {
                String params = url.substring(url.indexOf(SymbolConstants.QUESTION_MARKS));
                return base + SymbolConstants.FORWARD_SLASH + params;
            }
            return base + SymbolConstants.FORWARD_SLASH;
        }
        return url;
    }

    protected DataSource createSystemDataSource() {
        // TODO 这块这个权限控制不太好解决，怎样使账号权限最小化，有待研究
        if (dataSource instanceof HikariDataSource hikariDataSource) {
            HikariDataSource copyDataSource = new HikariDataSource();
            copyDataSource.setJdbcUrl(createSystemDatabaseUrl(getJdbcUrl()));
            copyDataSource.setUsername(hikariDataSource.getUsername());
            copyDataSource.setPassword(hikariDataSource.getUsername());
            copyDataSource.setDriverClassName(hikariDataSource.getDriverClassName());
            return copyDataSource;
        }
        throw new UnsupportedDbTypeException("Currently, other types of DataSources are not supported.");
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getSystemConnection() {
        return systemConnection;
    }

    public void setSystemConnection(Connection systemConnection) {
        this.systemConnection = systemConnection;
    }

    public Connection getConnection() {
        if (connection == null) {
            this.connection = createConnection();
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
