package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.constants.DbMigrationI18nKeyConstants;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;
import lombok.Getter;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月03日 23:10
 */
@Getter
public class DbMigrationFailedEvent extends DbMigrationEvent {

    private final Exception error;

    public DbMigrationFailedEvent(DbMigrationEngine dbMigrationEngine, Exception error) {
        super(dbMigrationEngine, MigrationStateEnums.FAILED, DbMigrationI18nKeyConstants.MIGRATION_STATE_FAILED);
        this.error = error;
    }

}
