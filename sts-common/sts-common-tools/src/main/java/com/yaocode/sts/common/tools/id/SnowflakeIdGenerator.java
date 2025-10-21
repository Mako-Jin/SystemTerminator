package com.yaocode.sts.common.tools.id;

import cn.hutool.core.util.IdUtil;

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
        return "Snowflake ID Generator";
    }

    @Override
    public IdGeneratorType getType() {
        return IdGeneratorType.SNOWFLAKE;
    }
}
