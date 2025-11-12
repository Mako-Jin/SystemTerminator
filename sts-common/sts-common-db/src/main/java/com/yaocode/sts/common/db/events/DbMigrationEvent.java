package com.yaocode.sts.common.db.events;

import com.yaocode.sts.common.db.DbMigrationEngine;
import com.yaocode.sts.common.db.enums.MigrationStateEnums;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 18:23
 */
public abstract class DbMigrationEvent extends ApplicationEvent {

    private final MigrationStateEnums status;
    private final String message;

    public DbMigrationEvent(DbMigrationEngine dbMigrationEngine, MigrationStateEnums status, String message) {
        super(dbMigrationEngine);
        this.status = status;
        this.message = message;
    }

    public MigrationStateEnums getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 事件源对象
     * @return DbMigrationEngine
     */
    @Override
    public DbMigrationEngine getSource() {
        return (DbMigrationEngine) super.getSource();
    }

}
