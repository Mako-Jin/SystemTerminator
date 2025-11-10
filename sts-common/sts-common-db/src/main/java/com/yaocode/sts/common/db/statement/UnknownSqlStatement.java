package com.yaocode.sts.common.db.statement;

import com.yaocode.sts.common.db.constants.SqlConstants;
import com.yaocode.sts.common.db.enums.SqlScriptTypeEnums;
import com.yaocode.sts.common.db.enums.SqlSecurityLevelEnums;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月10日 22:35
 */
public class UnknownSqlStatement extends AbstractSqlStatement {

    public UnknownSqlStatement() {
        super("", SqlSecurityLevelEnums.UNKNOWN, SqlScriptTypeEnums.UNKNOWN, SqlConstants.UNKNOWN);
    }

    @Override
    public String extractTableName() {
        return SqlConstants.UNKNOWN;
    }
}
