package com.yaocode.sts.common.db.enums;

import com.yaocode.sts.common.db.constants.CommonConstants;
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
    MYSQL(0, CommonConstants.DB_TYPE_MYSQL, "/mysql$1"),
    /**
     * postgresql
     */
    POSTGRESQL(1, CommonConstants.DB_TYPE_POSTGRESQL, "/postgres$1"),
    ;

    private final Integer code;

    private final String name;

    /**
     * 系统数据库路径（用于替换JDBC URL中的数据库名）
     */
    private final String systemDbPath;

    DBTypeEnums(Integer code, String name, String systemDbPath) {
        this.code = code;
        this.name = name;
        this.systemDbPath = systemDbPath;
    }
}
