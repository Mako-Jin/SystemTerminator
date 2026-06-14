package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 流程实例状态枚举
 */
@Getter
public enum ProcessStatusEnums {

    /**
     * 运行中
     * 流程实例正在执行
     */
    RUNNING(1, "运行中"),

    /**
     * 挂起
     * 流程实例被挂起，不会继续执行
     */
    SUSPENDED(2, "挂起"),

    /**
     * 已完成
     * 流程实例正常执行完成
     */
    COMPLETED(3, "已完成"),

    /**
     * 已终止
     * 流程实例被外部或内部终止
     */
    TERMINATED(4, "已终止"),

    /**
     * 异常
     * 流程实例因异常而停止（需人工介入）
     */
    ERROR(5, "异常"),

    /**
     * 已删除
     * 流程实例被物理删除（软删除标记）
     */
    DELETED(6, "已删除"),

    /**
     * 已迁移
     * 流程实例被迁移到新版本
     */
    MIGRATED(7, "已迁移");

    /**
     * 数字编码
     * 1: 运行中
     * 2: 挂起
     * 3: 已完成
     * 4: 已终止
     * 5: 异常
     * 6: 已删除
     * 7: 已迁移
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    ProcessStatusEnums(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 判断是否为结束状态
     */
    public boolean isEndState() {
        return this == COMPLETED || this == TERMINATED || this == DELETED;
    }

    /**
     * 判断是否可恢复
     */
    public boolean isResumable() {
        return this == SUSPENDED;
    }

    /**
     * 判断是否可终止
     */
    public boolean isTerminable() {
        return this == RUNNING || this == SUSPENDED;
    }

    /**
     * 判断是否可挂起
     */
    public boolean isSuspendable() {
        return this == RUNNING;
    }

    /**
     * 根据数字编码获取枚举值
     * @param code 数字编码
     * @return 枚举值，如果未找到返回null
     */
    public static ProcessStatusEnums fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (ProcessStatusEnums status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 根据数字编码获取枚举值，未找到时抛出异常
     * @param code 数字编码
     * @return 枚举值
     * @throws IllegalArgumentException 如果未找到匹配的枚举值
     */
    public static ProcessStatusEnums fromCodeOrThrow(Integer code) {
        ProcessStatusEnums result = fromCode(code);
        if (result == null) {
            throw new IllegalArgumentException("无效的流程状态编码: " + code);
        }
        return result;
    }

    /**
     * 判断数字编码是否有效
     * @param code 数字编码
     * @return 是否有效
     */
    public static boolean isValidCode(Integer code) {
        return fromCode(code) != null;
    }
}