package com.yaocode.sts.components.flow.core.engine.parser.enums;

/**
 * 解析状态枚举
 */
public enum ParseStatusEnums {
    /**
     * 初始状态
     */
    INITIAL,

    /**
     * 解析中
     */
    PARSING,

    /**
     * 验证中
     */
    VALIDATING,

    /**
     * 完成
     */
    COMPLETED,

    /**
     * 失败
     */
    FAILED,

    /**
     * 部分成功（有警告但可继续）
     */
    PARTIAL_SUCCESS
}




