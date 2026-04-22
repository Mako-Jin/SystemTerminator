package com.yaocode.sts.common.db.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:04
 */
public class DbMigrationException extends BusinessException {

    public DbMigrationException(String message) {
        super(message);
    }

    public DbMigrationException(String message, Exception e) {
        super(message, e);
    }
}
