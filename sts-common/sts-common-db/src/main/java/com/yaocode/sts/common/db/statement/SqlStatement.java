package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月09日 20:20
 */
public interface SqlStatement {

    /**
     * 获取当前sql
     * @return java.lang.String
     */
    String getSql();
    /**
     * 获取当前sql安全等级
     * @return com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums
     */
    SqlSecurityLevelEnums getSecurityLevel();
    /**
     * 获取当前sql类型
     * @return com.yaocode.sts.common.db.enums.SqlScriptTypeEnums
     */
    SqlScriptTypeEnums getCategory();
    /**
     * 获取sql关联的表名
     * @return java.lang.String
     */
    String getTableName();
    /**
     * 设置表名
     * @param tableName 表名
     */
    void setTableName(String tableName);
    /**
     * sql解析表名
     * @return 表名
     */
    String extractTableName();
    /**
     * 获取资源名称
     * @return java.lang.String
     */
    String getResourceName();
    /**
     * 设置表名
     * @param resourceName 资源名称
     */
    void setResourceName(String resourceName);
}
