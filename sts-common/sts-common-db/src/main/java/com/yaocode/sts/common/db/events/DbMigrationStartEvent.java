package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.constants.DbMigrationI18nKeyConstants;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:09
 */
public class DbMigrationStartEvent extends DbMigrationEvent {

    public DbMigrationStartEvent(DbMigrationEngine dbMigrationEngine) {
        super(dbMigrationEngine, MigrationStateEnums.RUNNING, DbMigrationI18nKeyConstants.MIGRATION_STATE_RUNNING);
    }

    @Override
    public DbMigrationEngine getSource() {
        return super.getSource();
    }

}
