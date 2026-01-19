package com.yaocode.sts.common.db.exception;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:04
 */
public class DbMigrationException extends RuntimeException {
    public DbMigrationException(String message, Exception e) {
        super(message, e);
    }
}
