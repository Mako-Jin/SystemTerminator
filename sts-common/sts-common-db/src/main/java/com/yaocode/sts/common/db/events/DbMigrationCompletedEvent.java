package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:10
 */
public class DbMigrationCompletedEvent extends DbMigrationEvent {

    public DbMigrationCompletedEvent(DbMigrationEngine dbMigrationEngine) {
        super(dbMigrationEngine, MigrationStateEnums.COMPLETED, "Database migration completed");
    }
}
