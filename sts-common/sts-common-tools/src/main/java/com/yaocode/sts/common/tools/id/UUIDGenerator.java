package com.yaocode.sts.common.tools.id;

import cn.hutool.core.util.IdUtil;
import com.yaocode.sts.common.tools.constants.ToolsConstants;

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
        return ToolsConstants.UUID_GENERATOR_NAME;
    }

    @Override
    public IdGeneratorType getType() {
        return IdGeneratorType.UUID;
    }

}
