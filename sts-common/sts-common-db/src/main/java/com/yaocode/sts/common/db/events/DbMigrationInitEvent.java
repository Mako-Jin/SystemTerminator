package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月07日 21:07
 */
public class DbMigrationInitEvent extends DbMigrationEvent {

    public DbMigrationInitEvent(DbMigrationEngine dbMigrationEngine) {
        super(dbMigrationEngine, MigrationStateEnums.RUNNING, "Database migration prepared");
    }

    @Override
    public DbMigrationEngine getSource() {
        return super.getSource();
    }
}
