package com.yaocode.sts.common.db.enums;

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
    DDL(1, "数据定义语言", "CREATE, ALTER, DROP等"),
    /**
     * DML
     */
    DML(2, "数据操作语言", "INSERT, UPDATE, DELETE等"),
    /**
     * DQL
     */
    DQL(3, "数据查询语言", "SELECT等"),
    /**
     * DCL
     */
    DCL(4, "数据控制语言", "GRANT, REVOKE等"),
    /**
     * TCL
     */
    TCL(5, "事务控制语言", "COMMIT, ROLLBACK等"),

    UNKNOWN(0, "未知", "未知"),
    ;

    private final Integer type;
    private final String name;
    private final String examples;

    SqlScriptTypeEnums(Integer type, String name, String examples) {
        this.type = type;
        this.name = name;
        this.examples = examples;
    }
}
