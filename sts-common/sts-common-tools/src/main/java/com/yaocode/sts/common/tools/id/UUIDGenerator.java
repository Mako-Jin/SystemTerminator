package com.yaocode.sts.common.tools.id;

import cn.hutool.core.util.IdUtil;

/**
 * uuid生成器实现
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 12:19
 */
public class UUIDGenerator implements IdGenerator<String> {

    @Override
    public String generate() {
        return IdUtil.fastSimpleUUID();
    }

    @Override
    public String getName() {
        return "UUID Generator";
    }

    @Override
    public IdGeneratorType getType() {
        return IdGeneratorType.UUID;
    }

}
