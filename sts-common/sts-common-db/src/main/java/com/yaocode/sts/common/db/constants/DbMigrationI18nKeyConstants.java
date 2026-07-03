package com.yaocode.sts.common.db.constants;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;

/**
 * 国际化消息key常量
 * @author: Jin-LiangBo
 * @date: 2026年04月22日 9:53
 */
public interface DbMigrationI18nKeyConstants extends BasicI18nKeyConstants {

    // ==================== SQL安全等级枚举国际化key ====================
    String SECURITY_LEVEL_HIGH = "db.security_level.high";
    String SECURITY_LEVEL_HIGH_DESC = "db.security_level.high.desc";
    String SECURITY_LEVEL_MEDIUM = "db.security_level.medium";
    String SECURITY_LEVEL_MEDIUM_DESC = "db.security_level.medium.desc";
    String SECURITY_LEVEL_LOW = "db.security_level.low";
    String SECURITY_LEVEL_LOW_DESC = "db.security_level.low.desc";
    String SECURITY_LEVEL_SAFE = "db.security_level.safe";
    String SECURITY_LEVEL_SAFE_DESC = "db.security_level.safe.desc";

    // ==================== SQL脚本类型枚举国际化key ====================
    String SCRIPT_TYPE_DDL = "db.script_type.ddl";
    String SCRIPT_TYPE_DDL_DESC = "db.script_type.ddl.desc";
    String SCRIPT_TYPE_DML = "db.script_type.dml";
    String SCRIPT_TYPE_DML_DESC = "db.script_type.dml.desc";
    String SCRIPT_TYPE_DQL = "db.script_type.dql";
    String SCRIPT_TYPE_DQL_DESC = "db.script_type.dql.desc";
    String SCRIPT_TYPE_DCL = "db.script_type.dcl";
    String SCRIPT_TYPE_DCL_DESC = "db.script_type.dcl.desc";
    String SCRIPT_TYPE_TCL = "db.script_type.tcl";
    String SCRIPT_TYPE_TCL_DESC = "db.script_type.tcl.desc";

    // ==================== 数据库类型枚举国际化key ====================
    String DB_TYPE_MYSQL = "db.type.mysql";
    String DB_TYPE_POSTGRESQL = "db.type.postgresql";

    // ==================== 迁移状态枚举国际化key ====================
    String MIGRATION_STATE_INIT = "db.migration_state.init";
    String MIGRATION_STATE_RUNNING = "db.migration_state.running";
    String MIGRATION_STATE_COMPLETED = "db.migration_state.completed";
    String MIGRATION_STATE_FAILED = "db.migration_state.failed";

    // ==================== 异常消息国际化key ====================
    String ERR_UNSUPPORTED_DB_TYPE = "db.error.unsupported_db_type";
    String ERR_DB_CONNECTION_EMPTY = "db.error.db_connection_empty";
    String ERR_DB_CONNECTION_FAILED = "db.error.db_connection_failed";
    String ERR_SCRIPT_EXECUTION_FAILED = "db.error.script_execution_failed";
    String ERR_TABLE_INIT_FAILED = "db.error.table_init_failed";
    String ERR_RECORD_INSERT_FAILED = "db.error.record_insert_failed";
    String ERR_DB_MIGRATION_FAILED = "db.error.db_migration_failed";
    String ERR_UNSUPPORTED_DATASOURCE_TYPE = "db.error.unsupported_datasource_type";


}
