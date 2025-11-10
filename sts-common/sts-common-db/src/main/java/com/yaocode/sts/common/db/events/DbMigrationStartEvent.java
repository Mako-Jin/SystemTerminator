package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;

/**
 * TODO
 *
 * @version 1.0
 * @Author: Jin-LiangBo
 * @Date: 2025年11月03日 23:09
 * @Description:
 */
public class DbMigrationStartEvent extends DbMigrationEvent {

    public DbMigrationStartEvent(DbMigrationEngine dbMigrationEngine) {
        super(dbMigrationEngine, MigrationStateEnums.RUNNING, "Database migration started");
    }

}
