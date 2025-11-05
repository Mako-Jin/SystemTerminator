package com.yaocode.sts.common.db.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 17:05
 */
@ConfigurationProperties(prefix = DbMigrationProperties.DB_MIGRATION_PREFIX)
public class DbMigrationProperties {

    public static final String DB_MIGRATION_PREFIX = "yaocode.database.migrate";

    private boolean enabled = true;

    private boolean ignoreErrors = false;

    private String[] sqlScriptPath = new String[]{"classpath*:/db/migration/**/*.sql"};

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getSqlScriptPath() {
        return sqlScriptPath;
    }

    public void setSqlScriptPath(String[] sqlScriptPath) {
        this.sqlScriptPath = sqlScriptPath;
    }

    public boolean isIgnoreErrors() {
        return ignoreErrors;
    }

    public void setIgnoreErrors(boolean ignoreErrors) {
        this.ignoreErrors = ignoreErrors;
    }
}
