package com.yaocode.sts.common.db.properties;

import com.yaocode.sts.common.db.constants.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 17:05
 */
@Data
@ConfigurationProperties(prefix = DbMigrationProperties.DB_MIGRATION_PREFIX)
public class DbMigrationProperties {

    public static final String DB_MIGRATION_PREFIX = "yaocode.database.migrate";

    /**
     * 默认值
     */
    private static final String DEFAULT_SQL_SCRIPT_LOCATION = CommonConstants.DEFAULT_MIGRATION_PATH;

    private boolean enabled = true;

    private boolean ignoreErrors = false;

    private String[] sqlScriptPath = new String[]{};

    private String versionPrefix = CommonConstants.DEFAULT_VERSION_PREFIX;

    private String separator = CommonConstants.DEFAULT_VERSION_SEPARATOR;

    private boolean checkVersionConflict = true;

    private boolean autoRollback = true;

    public String getDefaultSqlScriptLocation() {
        return DEFAULT_SQL_SCRIPT_LOCATION;
    }
}
