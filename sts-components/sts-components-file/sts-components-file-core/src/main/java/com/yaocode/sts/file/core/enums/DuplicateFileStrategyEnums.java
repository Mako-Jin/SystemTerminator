package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 重复文件处理策略枚举
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum DuplicateFileStrategyEnums {
    /**
     * 复用文件（秒传）
     */
    REUSE("复用"),

    /**
     * 创建新版本
     */
    NEW_VERSION("创建新版本"),

    /**
     * 覆盖文件
     */
    OVERWRITE("覆盖"),

    /**
     * 自动重命名
     */
    AUTO_RENAME("自动重命名"),

    /**
     * 抛出异常，由用户决定
     */
    THROW_EXCEPTION("抛出异常");

    private final String desc;

    DuplicateFileStrategyEnums(String desc) {
        this.desc = desc;
    }

}
