package com.yaocode.sts.common.db.constants;

/**
 * SQL模板常量类
 * 按数据库类型分组存储SQL语句
 * @author: Jin-LiangBo
 * @date: 2026年07月03日
 */
public interface SqlTemplateConstants {

    // ==================== MySQL SQL模板 ====================
    interface MySql {
        /**
         * 检查数据库是否存在
         */
        String CHECK_DATABASE_EXISTS = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";

        /**
         * 创建数据库模板
         * 参数: %s - 数据库名
         */
        String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS `%s` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

        /**
         * 检查表是否存在
         */
        String CHECK_TABLE_EXISTS = "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = ?";

        /**
         * 统计表数据行数模板
         * 参数: %s - 表名
         */
        String COUNT_TABLE_DATA = "SELECT COUNT(*) FROM %s";

        /**
         * 插入脚本执行历史记录模板
         * 参数: %s - 表名, 列名(8个)
         */
        String INSERT_SCRIPT_HISTORY = "INSERT INTO %s (`%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`, `%s`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        /**
         * 检查脚本是否已执行模板
         * 参数: %s - 表名, %s - 内容列名
         */
        String CHECK_SCRIPT_EXECUTE_STATUS = "SELECT COUNT(*) FROM %s WHERE %s = ?";
    }

    // ==================== PostgreSQL SQL模板 ====================
    interface PostgreSql {
        /**
         * 检查数据库是否存在
         */
        String CHECK_DATABASE_EXISTS = "SELECT datname FROM pg_database WHERE datname = ?";

        /**
         * 创建数据库模板
         * 参数: %s - 数据库名
         */
        String CREATE_DATABASE = "CREATE DATABASE %s WITH ENCODING 'UTF8'";

        /**
         * 检查表是否存在
         */
        String CHECK_TABLE_EXISTS = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = current_schema() AND table_name = ?";

        /**
         * 统计表数据行数模板
         * 参数: %s - 表名
         */
        String COUNT_TABLE_DATA = "SELECT COUNT(*) FROM %s";

        /**
         * 插入脚本执行历史记录模板
         * 参数: %s - 表名, 列名(8个)
         */
        String INSERT_SCRIPT_HISTORY = "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        /**
         * 检查脚本是否已执行模板
         * 参数: %s - 表名, %s - 内容列名
         */
        String CHECK_SCRIPT_EXECUTE_STATUS = "SELECT COUNT(*) FROM %s WHERE %s = ?";
    }
}
