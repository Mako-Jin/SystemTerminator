package com.yaocode.sts.common.db.enums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 21:58
 */
public enum StatementEnums {

    /**
     * select
     */
    SELECT("SELECT", SqlScriptTypeEnums.DQL, SqlSecurityLevelEnums.LOW),
    /**
     * INSERT
     */
    INSERT("INSERT", SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.LOW),
    /**
     * UPDATE
     */
    UPDATE("UPDATE", SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.HIGH),
    /**
     * DELETE
     */
    DELETE("DELETE", SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.HIGH),
    /**
     * CREATE
     */
    CREATE("CREATE", SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.LOW),
    /**
     * DROP
     */
    DROP("DROP", SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.HIGH),
    /**
     * ALTER
     */
    ALTER("ALTER", SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.HIGH),
    ;

    private final String keyword;
    private final SqlScriptTypeEnums category;
    private final SqlSecurityLevelEnums securityLevel;

    StatementEnums(String keyword, SqlScriptTypeEnums category, SqlSecurityLevelEnums securityLevel) {
        this.keyword = keyword;
        this.category = category;
        this.securityLevel = securityLevel;
    }

    public String getKeyword() { return keyword; }
    public SqlScriptTypeEnums getCategory() { return category; }
    public SqlSecurityLevelEnums getSecurityLevel() { return securityLevel; }

}
