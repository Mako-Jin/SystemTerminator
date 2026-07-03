package com.yaocode.sts.common.db.constants;

import com.yaocode.sts.common.basic.constants.PathConstants;

/**
 * 公共常量类
 * @author: Jin-LiangBo
 * @date: 2025年11月07日 21:54
 */
public interface CommonConstants {

    String JAR_PACKAGE_NAME = "sts-common-db";

    String SCRIPT_CONTROL_TABLE_NAME = "aux_tbl_script_history";

    // ==================== 数据库类型 ====================
    String DB_TYPE_MYSQL = "mysql";
    String DB_TYPE_POSTGRESQL = "postgresql";

    // ==================== JDBC URL 参数 ====================
    String URL_PARAM_ALLOW_PUBLIC_KEY_RETRIEVAL = "allowPublicKeyRetrieval";
    String URL_PARAM_USE_SSL = "useSSL";
    String URL_PARAM_SERVER_TIMEZONE = "serverTimezone";
    String URL_VALUE_UTC = "UTC";
    String URL_VALUE_TRUE = "true";
    String URL_VALUE_FALSE = "false";

    // ==================== 版本控制表字段 ====================
    String COLUMN_FILE_NAME = "file_name";
    String COLUMN_SCRIPT_CONTENT = "script_content";
    String COLUMN_SCRIPT_TYPE = "script_type";
    String COLUMN_SECURITY_LEVEL = "security_level";
    String COLUMN_STATUS = "status";
    String COLUMN_ERROR_MESSAGE = "error_message";
    String COLUMN_EXECUTE_TIME = "execute_time";
    String COLUMN_CREATE_TIME = "create_time";

    // ==================== 注释符号 ====================
    String COMMENT_MULTI_LINE_START = "/*";
    String COMMENT_MULTI_LINE_END = "*/";
    String COMMENT_SINGLE_LINE = "--";

    // ==================== 正则表达式常量 ====================
    /**
     * 匹配JDBC URL中数据库名的正则表达式
     * 匹配模式: /数据库名(参数|结束)
     */
    String REGEX_DB_NAME_IN_URL = "/[^/?]+([?]|$)";

    /**
     * MySQL 迁移脚本路径
     */
    String MYSQL_MIGRATION_PATH = PathConstants.META_INF_PREFIX + "db/migration/mysql/**/*.sql";

    /**
     * PostgreSQL 迁移脚本路径
     */
    String POSTGRESQL_MIGRATION_PATH = PathConstants.META_INF_PREFIX + "db/migration/postgresql/**/*.sql";

    /**
     * 默认迁移脚本路径（通用）
     */
    String DEFAULT_MIGRATION_PATH = PathConstants.META_INF_PREFIX + "db/migration/**/*.sql";

    /**
     * 默认版本前缀
     */
    String DEFAULT_VERSION_PREFIX = "V";

    /**
     * 默认版本分隔符
     */
    String DEFAULT_VERSION_SEPARATOR = "__";

    /**
     * 版本号与描述之间的分隔符
     */
    String VERSION_DESCRIPTION_SEPARATOR = "__";

}
