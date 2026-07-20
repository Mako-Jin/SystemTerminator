package com.yaocode.sts.components.flow.core.engine.parser.enums;

/**
 * 解析策略
 */
public enum ParseStrategyEnums {
    /**
     * DOM 解析（适合小文件）
     */
    DOM,

    /**
     * SAX 解析（适合大文件）
     */
    SAX,

    /**
     * 混合解析（自动选择）
     */
    HYBRID
}
