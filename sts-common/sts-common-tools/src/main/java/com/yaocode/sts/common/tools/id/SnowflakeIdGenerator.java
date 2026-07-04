package com.yaocode.sts.common.tools.id;

import cn.hutool.core.util.IdUtil;
import com.yaocode.sts.common.tools.constants.ToolsConstants;

/**
 * 雪花id生成器
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 12:28
 */
public class SnowflakeIdGenerator implements IdGenerator<Long> {

    @Override
    public Long generate() {
        return IdUtil.getSnowflakeNextId();
    }

    @Override
    public String getName() {
        return ToolsConstants.SNOWFLAKE_GENERATOR_NAME;
    }

    @Override
    public IdGeneratorType getType() {
        return IdGeneratorType.SNOWFLAKE;
    }
}
