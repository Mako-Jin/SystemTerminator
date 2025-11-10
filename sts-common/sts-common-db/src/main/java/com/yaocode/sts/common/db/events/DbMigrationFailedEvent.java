package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2025年11月03日 23:10
 * @Description:
 */
public class DbMigrationFailedEvent extends DbMigrationEvent {

    private final Exception error;

    public DbMigrationFailedEvent(DbMigrationEngine dbMigrationEngine, Exception error) {
        super(dbMigrationEngine, MigrationStateEnums.FAILED, "Database migration failed: " + error.getMessage());
        this.error = error;
    }

    public Exception getError() {
        return error;
    }
}
