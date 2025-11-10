package com.yaocode.sts.common.db.enums;

import com.yaocode.sts.common.db.constants.SqlConstants;
import lombok.Getter;

/**
 * sql脚本中的安全等级枚举类
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 15:23
 */
@Getter
public enum SqlSecurityLevelEnums {

    /**
     * 高危
     */
    HIGH(1, "高危", "包含DROP、TRUNCATE等危险操作"),
    /**
     * 中危
     */
    MEDIUM(2, "中危", "包含ALTER、DELETE等操作"),
    /**
     * 低危
     */
    LOW(3, "低危", "包含CREATE、INSERT、UPDATE等操作"),
    /**
     * 安全
     */
    SAFE(4, "安全", "只读查询操作"),
    UNKNOWN(5, SqlConstants.UNKNOWN, "未知");

    private final int level;
    private final String name;
    private final String description;

    SqlSecurityLevelEnums(int level, String name, String description) {
        this.level = level;
        this.name = name;
        this.description = description;
    }
}
