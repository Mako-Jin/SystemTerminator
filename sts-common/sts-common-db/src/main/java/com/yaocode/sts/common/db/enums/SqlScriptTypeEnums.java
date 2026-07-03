package com.yaocode.sts.common.db.enums;

import com.yaocode.sts.common.db.constants.DbMigrationI18nKeyConstants;
import com.yaocode.sts.common.db.constants.SqlConstants;
import lombok.Getter;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 15:25
 */
@Getter
public enum SqlScriptTypeEnums {

    /**
     * DDL
     */
    DDL(
            1,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DDL,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DDL_DESC
    ),
    /**
     * DML
     */
    DML(
            2,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DML,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DML_DESC
    ),
    /**
     * DQL
     */
    DQL(
            3,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DQL,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DQL_DESC
    ),
    /**
     * DCL
     */
    DCL(
            4,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DCL,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_DCL_DESC
    ),
    /**
     * TCL
     */
    TCL(
            5,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_TCL,
            DbMigrationI18nKeyConstants.SCRIPT_TYPE_TCL_DESC
    ),

    UNKNOWN(
            0,
            SqlConstants.UNKNOWN,
            SqlConstants.UNKNOWN
    ),
    ;

    private final Integer type;
    private final String name;
    private final String desc;

    SqlScriptTypeEnums(Integer type, String name, String desc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
    }
}
