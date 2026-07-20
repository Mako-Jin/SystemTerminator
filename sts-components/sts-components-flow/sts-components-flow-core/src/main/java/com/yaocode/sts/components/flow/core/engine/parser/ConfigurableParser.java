package com.yaocode.sts.components.flow.core.engine.parser;

/**
 * 可配置解析器接口
 * <p>
 * 实现此接口的解析器支持动态更新配置
 *
 * @author Process Engine Team
 */
public interface ConfigurableParser {

    /**
     * 设置解析配置
     *
     * @param configuration 解析配置
     */
    void setConfiguration(ParserConfiguration configuration);

    /**
     * 获取当前配置
     *
     * @return 解析配置
     */
    ParserConfiguration getConfiguration();
}
