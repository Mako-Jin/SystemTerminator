package com.yaocode.sts.common.db.enums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 18:20
 */
public enum MigrationStateEnums {
    /**
     * PENDING
     */
    PENDING,
    /**
     * RUNNING
     */
    RUNNING,
    /**
     * COMPLETED
     */
    COMPLETED,
    /**
     * FAILED
     */
    FAILED,
    /**
     * 冲突
     */
    CONFLICT,
    /**
     * SKIPPED
     */
    SKIPPED,
    /**
     * 回滚
     */
    ROLLED_BACK
}
