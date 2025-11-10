package com.yaocode.sts.common.db.enums;

/**
 * 冲突解决办法枚举类
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:28
 */
public enum ConflictResolutionEnums {

    /**
     * 人为介入
     */
    MANUAL_REVIEW,
    /**
     * 自动检查
     */
    AUTO_OVERWRITE,
    /**
     * 跳过
     */
    SKIP,
    /**
     * 回滚重试
     */
    ROLLBACK_AND_RETRY

}
