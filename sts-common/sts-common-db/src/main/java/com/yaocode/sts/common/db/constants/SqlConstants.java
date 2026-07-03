package com.yaocode.sts.common.db.constants;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

/**
 * sql 关键字
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 22:21
 */
public interface SqlConstants {

    // ==================== SQL 关键字 ====================
    String SELECT = "SELECT";
    String INSERT = "INSERT";
    String UPDATE = "UPDATE";
    String DELETE = "DELETE";
    String CREATE = "CREATE";
    String DROP = "DROP";
    String ALTER = "ALTER";
    String FROM = "FROM";
    String INTO = "INTO";
    String TABLE = "TABLE";
    String SET = "SET";
    String WHERE = "WHERE";
    String GROUP_BY = "GROUP BY";
    String ORDER_BY = "ORDER BY";
    String LIMIT = "LIMIT";
    String IF_NOT_EXISTS = "IF NOT EXISTS";
    String IF_EXISTS = "IF EXISTS";

    // ==================== 其他常量 ====================
    String UNKNOWN = "UNKNOWN";

    // ==================== SQL 脚本类型 ====================
    String DDL = "DDL";
    String DML = "DML";
    String DQL = "DQL";
    String DCL = "DCL";
    String TCL = "TCL";

    // ==================== MySQL 字符集 ====================
    String CHARSET_UTF8MB4 = "utf8mb4";
    String COLLATE_UTF8MB4_UNICODE_CI = "utf8mb4_unicode_ci";

    // ==================== 分隔符 ====================
    String SEMICOLON = String.valueOf(SymbolConstants.SEMICOLON);
}
