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

    /**
     * 默认值
     */
    private static final String DEFAULT_SQL_SCRIPT_LOCATION = "classpath*:/META-INF/db/migration/**/*.sql";

    private boolean enabled = true;

    private boolean ignoreErrors = false;

    private String[] sqlScriptPath = new String[]{};

    private String versionPrefix = "V";

    private String separator = "__";

    private boolean checkVersionConflict = true;

    private boolean autoRollback = true;

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

    public String getVersionPrefix() {
        return versionPrefix;
    }

    public void setVersionPrefix(String versionPrefix) {
        this.versionPrefix = versionPrefix;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public boolean isCheckVersionConflict() {
        return checkVersionConflict;
    }

    public void setCheckVersionConflict(boolean checkVersionConflict) {
        this.checkVersionConflict = checkVersionConflict;
    }

    public boolean isAutoRollback() {
        return autoRollback;
    }

    public void setAutoRollback(boolean autoRollback) {
        this.autoRollback = autoRollback;
    }

    public String getDefaultSqlScriptLocation() {
        return DEFAULT_SQL_SCRIPT_LOCATION;
    }
}
