package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 任务状态枚举
 * 分为瞬时状态(Transient)和稳定状态(Stable)
 */
@Getter
public enum TaskStatusEnums {

    // ==================== 瞬时状态 (Transient) ====================
    // 瞬时状态表示任务正在执行某个操作，是短暂的中间状态

    /**
     * 正在创建
     * 瞬时状态：任务正在被创建，尚未可用
     */
    CREATING(101, "正在创建", true),

    /**
     * 正在分配
     * 瞬时状态：正在为任务分配处理人
     */
    ASSIGNING(102, "正在分配", true),

    /**
     * 正在更新
     * 瞬时状态：任务信息（如处理人、截止时间）正在更新
     */
    UPDATING(103, "正在更新", true),

    /**
     * 正在完成
     * 瞬时状态：任务正在被完成，尚未结束
     */
    COMPLETING(104, "正在完成", true),

    /**
     * 正在取消
     * 瞬时状态：任务正在被取消
     */
    CANCELING(105, "正在取消", true),

    // ==================== 稳定状态 (Stable) ====================
    // 稳定状态表示任务处于某个稳定的状态，适合用于业务查询

    /**
     * 已创建（待处理）
     * 稳定状态：任务已创建，等待被认领或处理。这是最常见的"待办"状态
     */
    CREATED(201, "已创建", false),

    /**
     * 已认领（处理中）
     * 稳定状态：任务已被用户认领，正在处理中
     */
    CLAIMED(202, "已认领", false),

    /**
     * 已委派
     * 稳定状态：任务已被委派给其他人处理
     */
    DELEGATED(203, "已委派", false),

    /**
     * 已转交
     * 稳定状态：任务已被转交给其他人处理
     */
    TRANSFERRED(204, "已转交", false),

    /**
     * 已驳回
     * 稳定状态：任务被驳回，需要重新处理
     */
    REJECTED(205, "已驳回", false),

    /**
     * 已完成
     * 稳定状态：任务已成功完成
     */
    COMPLETED(206, "已完成", false),

    /**
     * 已取消
     * 稳定状态：任务被取消（如流程被终止）
     */
    CANCELED(207, "已取消", false),

    /**
     * 执行失败
     * 稳定状态：任务执行失败（如服务调用失败、脚本执行异常等）
     */
    FAILED(208, "执行失败", false),

    /**
     * 已过期
     * 稳定状态：任务超过截止时间未处理
     */
    EXPIRED(209, "已过期", false);

    /**
     * 数字编码
     * 1xx: 瞬时状态
     * 2xx: 稳定状态
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String description;

    /**
     * 是否为瞬时状态
     */
    private final boolean transientState;

    TaskStatusEnums(Integer code, String description, boolean transientState) {
        this.code = code;
        this.description = description;
        this.transientState = transientState;
    }

    /**
     * 判断是否为稳定状态
     */
    public boolean isStableState() {
        return !transientState;
    }

    /**
     * 判断是否为终态（不可再变更）
     */
    public boolean isFinalState() {
        return this == COMPLETED || this == CANCELED || this == FAILED;
    }

    /**
     * 判断任务是否可被认领
     */
    public boolean isClaimable() {
        return this == CREATED;
    }

    /**
     * 判断任务是否可被完成
     */
    public boolean isCompletable() {
        return this == CLAIMED || this == DELEGATED;
    }

    /**
     * 根据数字编码获取枚举值
     * @param code 数字编码
     * @return 枚举值，如果未找到返回null
     */
    public static TaskStatusEnums fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TaskStatusEnums status : values()) {
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
    public static TaskStatusEnums fromCodeOrThrow(Integer code) {
        TaskStatusEnums result = fromCode(code);
        if (result == null) {
            throw new IllegalArgumentException("无效的任务状态编码: " + code);
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