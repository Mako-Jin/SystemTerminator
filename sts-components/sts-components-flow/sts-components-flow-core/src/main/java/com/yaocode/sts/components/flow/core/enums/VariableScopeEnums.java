package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 变量作用域
 */
@Getter
public enum VariableScopeEnums {

    PROCESS_INSTANCE(1, "流程实例"),
    EXECUTION(2, "执行路径"),
    TASK(3, "任务"),
    CONCURRENT_BRANCH(4, "并发分支");

    private final int code;
    private final String description;

    VariableScopeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static VariableScopeEnums fromCode(int code) {
        for (VariableScopeEnums scope : values()) {
            if (scope.code == code) return scope;
        }
        return null;
    }
}
