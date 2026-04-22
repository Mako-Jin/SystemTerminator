package com.yaocode.sts.common.db.enums;

import lombok.Getter;

/**
 * 数据类型枚举类
 * @author: Jin-LiangBo
 * @date: 2026年04月22日 11:17
 */
@Getter
public enum DBTypeEnums {
    /**
     * mysql
     */
    MYSQL(0, "mysql"),
    /**
     * postgresql
     */
    POSTGRESQL(1, "postgresql"),
    ;

    private final Integer code;

    private final String name;

    DBTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
