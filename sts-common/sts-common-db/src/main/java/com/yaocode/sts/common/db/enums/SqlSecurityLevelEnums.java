package com.yaocode.sts.common.db.enums;

import com.yaocode.sts.common.db.constants.DbMigrationI18nKeyConstants;
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
    HIGH(
            1,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_HIGH,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_HIGH_DESC
    ),
    /**
     * 中危
     */
    MEDIUM(
            2,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_MEDIUM,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_MEDIUM_DESC
    ),
    /**
     * 低危
     */
    LOW(
            3,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_LOW,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_LOW_DESC
    ),
    /**
     * 安全
     */
    SAFE(
            4,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_SAFE,
            DbMigrationI18nKeyConstants.SECURITY_LEVEL_SAFE_DESC
    ),
    UNKNOWN(
            5,
            SqlConstants.UNKNOWN,
            SqlConstants.UNKNOWN
    );



    private final int level;
    private final String name;
    private final String description;

    SqlSecurityLevelEnums(int level, String name, String description) {
        this.level = level;
        this.name = name;
        this.description = description;
    }
}
