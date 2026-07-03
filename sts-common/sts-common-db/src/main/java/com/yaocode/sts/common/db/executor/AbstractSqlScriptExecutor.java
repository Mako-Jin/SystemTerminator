package com.yaocode.sts.common.db.executor;

import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.db.constants.CommonConstants;
import com.yaocode.sts.common.db.constants.DbMigrationI18nKeyConstants;
import com.yaocode.sts.common.db.enums.DBTypeEnums;
import com.yaocode.sts.common.db.exception.UnsupportedDbTypeException;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
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
@Setter
public abstract class AbstractSqlScriptExecutor implements SqlScriptExecutor, Closeable {

    private static final Logger logger = LoggerFactory.getLogger(AbstractSqlScriptExecutor.class);

    /**
     * 匹配JDBC URL中数据库名的正则表达式
     * 匹配模式: /数据库名(参数|结束)
     */
    private static final String REGEX_DB_NAME_IN_URL = CommonConstants.REGEX_DB_NAME_IN_URL;

    @Getter
    private DataSource dataSource;

    @Getter
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
//            return url.split(SymbolConstants.QUESTION_MARKS)[0];
            int index = url.indexOf(SymbolConstants.QUESTION_MARKS);
            return url.substring(0, index);
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
        String database = SymbolConstants.EMPTY_STR;
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
        for (DBTypeEnums dbType : DBTypeEnums.values()) {
            if (datasourceUrl.contains(dbType.getName())) {
                return addPublicKeyRetrieval(datasourceUrl.replaceFirst(REGEX_DB_NAME_IN_URL, dbType.getSystemDbPath()));
            }
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
        if (!originalUrl.contains(CommonConstants.URL_PARAM_ALLOW_PUBLIC_KEY_RETRIEVAL)) {
            modifiedUrl.append(hasParams ? SymbolConstants.AMPERSAND : SymbolConstants.QUESTION_MARKS)
                    .append(CommonConstants.URL_PARAM_ALLOW_PUBLIC_KEY_RETRIEVAL)
                    .append(SymbolConstants.EQUAL_SIGN)
                    .append(CommonConstants.URL_VALUE_TRUE);
            hasParams = true;
        }

        // 添加 useSSL
        if (!originalUrl.contains(CommonConstants.URL_PARAM_USE_SSL)) {
            modifiedUrl.append(hasParams ? SymbolConstants.AMPERSAND : SymbolConstants.QUESTION_MARKS)
                    .append(CommonConstants.URL_PARAM_USE_SSL)
                    .append(SymbolConstants.EQUAL_SIGN)
                    .append(CommonConstants.URL_VALUE_FALSE);
            hasParams = true;
        }

        // 添加 serverTimezone（对于MySQL 8.x很重要）
        if (!originalUrl.contains(CommonConstants.URL_PARAM_SERVER_TIMEZONE)) {
            modifiedUrl.append(hasParams ? SymbolConstants.AMPERSAND : SymbolConstants.QUESTION_MARKS)
                    .append(CommonConstants.URL_PARAM_SERVER_TIMEZONE)
                    .append(SymbolConstants.EQUAL_SIGN)
                    .append(CommonConstants.URL_VALUE_UTC);
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
        throw new UnsupportedDbTypeException(DbMigrationI18nKeyConstants.ERR_UNSUPPORTED_DATASOURCE_TYPE);
    }

    public Connection getConnection() {
        if (connection == null) {
            this.connection = createConnection();
        }
        return connection;
    }

}
