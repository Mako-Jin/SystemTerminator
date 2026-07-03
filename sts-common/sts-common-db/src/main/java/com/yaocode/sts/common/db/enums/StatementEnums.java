package com.yaocode.sts.common.db.enums;

import com.yaocode.sts.common.db.constants.SqlConstants;
import lombok.Getter;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 21:58
 */
@Getter
public enum StatementEnums {

    /**
     * select
     */
    SELECT(SqlConstants.SELECT, SqlScriptTypeEnums.DQL, SqlSecurityLevelEnums.LOW),
    /**
     * INSERT
     */
    INSERT(SqlConstants.INSERT, SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.LOW),
    /**
     * UPDATE
     */
    UPDATE(SqlConstants.UPDATE, SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.HIGH),
    /**
     * DELETE
     */
    DELETE(SqlConstants.DELETE, SqlScriptTypeEnums.DML, SqlSecurityLevelEnums.HIGH),
    /**
     * CREATE
     */
    CREATE(SqlConstants.CREATE, SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.LOW),
    /**
     * DROP
     */
    DROP(SqlConstants.DROP, SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.HIGH),
    /**
     * ALTER
     */
    ALTER(SqlConstants.ALTER, SqlScriptTypeEnums.DDL, SqlSecurityLevelEnums.HIGH),
    ;

    private final String keyword;
    private final SqlScriptTypeEnums category;
    private final SqlSecurityLevelEnums securityLevel;

    StatementEnums(String keyword, SqlScriptTypeEnums category, SqlSecurityLevelEnums securityLevel) {
        this.keyword = keyword;
        this.category = category;
        this.securityLevel = securityLevel;
    }

}