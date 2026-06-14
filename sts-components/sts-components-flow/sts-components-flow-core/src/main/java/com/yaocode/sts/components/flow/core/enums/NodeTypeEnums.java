package com.yaocode.sts.components.flow.core.enums;


import lombok.Getter;

/**
 * 节点类型枚举
 * 采用三级编码结构: 主类型(万位) + 大类(千位) + 子类型(百位)
 * 编码规则: 10000-19999事件类, 20000-29999任务类, 30000-39999网关类, 40000-49999容器类
 */
@Getter
public enum NodeTypeEnums {

    // ==================== 事件类 (10000-19999) ====================

    /** 开始事件 */
    START_EVENT(10100, "开始事件"),
    /** 定时开始事件 */
    START_EVENT_TIMER(10101, "定时开始事件"),
    /** 消息开始事件 */
    START_EVENT_MESSAGE(10102, "消息开始事件"),
    /** 信号开始事件 */
    START_EVENT_SIGNAL(10103, "信号开始事件"),

    /** 中间捕获事件 */
    INTERMEDIATE_CATCH_EVENT(10200, "中间捕获事件"),
    /** 中间定时捕获 */
    INTERMEDIATE_CATCH_TIMER(10201, "中间定时捕获"),
    /** 中间消息捕获 */
    INTERMEDIATE_CATCH_MESSAGE(10202, "中间消息捕获"),
    /** 中间信号捕获 */
    INTERMEDIATE_CATCH_SIGNAL(10203, "中间信号捕获"),

    /** 中间抛出事件 */
    INTERMEDIATE_THROW_EVENT(10300, "中间抛出事件"),
    /** 中间消息抛出 */
    INTERMEDIATE_THROW_MESSAGE(10301, "中间消息抛出"),
    /** 中间信号抛出 */
    INTERMEDIATE_THROW_SIGNAL(10302, "中间信号抛出"),

    /** 边界事件 */
    BOUNDARY_EVENT(10400, "边界事件"),
    /** 定时边界(超时) */
    BOUNDARY_TIMER(10401, "定时边界"),
    /** 错误边界 */
    BOUNDARY_ERROR(10402, "错误边界"),
    /** 消息边界 */
    BOUNDARY_MESSAGE(10403, "消息边界"),
    /** 信号边界 */
    BOUNDARY_SIGNAL(10404, "信号边界"),
    /** 补偿边界 */
    BOUNDARY_COMPENSATION(10406, "补偿边界"),

    /** 结束事件 */
    END_EVENT(10500, "结束事件"),
    /** 终止结束事件 */
    END_TERMINATE(10501, "终止结束事件"),
    /** 错误结束事件 */
    END_ERROR(10502, "错误结束事件"),
    /** 消息结束事件 */
    END_MESSAGE(10503, "消息结束事件"),
    /** 信号结束事件 */
    END_SIGNAL(10504, "信号结束事件"),

    // ==================== 任务类 (20000-29999) ====================

    /** 用户任务 */
    USER_TASK(20100, "用户任务"),
    /** 服务任务 */
    SERVICE_TASK(20200, "服务任务"),
    /** 脚本任务 */
    SCRIPT_TASK(20300, "脚本任务"),
    /** 业务规则任务 */
    BUSINESS_RULE_TASK(20400, "业务规则任务"),
    /** 发送任务 */
    SEND_TASK(20500, "发送任务"),
    /** 接收任务 */
    RECEIVE_TASK(20600, "接收任务"),
    /** 手工任务 */
    MANUAL_TASK(20700, "手工任务"),

    // ==================== 网关类 (30000-39999) ====================

    /** 排他网关 */
    EXCLUSIVE_GATEWAY(30100, "排他网关"),
    /** 包容网关 */
    INCLUSIVE_GATEWAY(30200, "包容网关"),
    /** 并行网关 */
    PARALLEL_GATEWAY(30300, "并行网关"),
    /** 事件网关 */
    EVENT_BASED_GATEWAY(30400, "事件网关"),

    // ==================== 容器类 (40000-49999) ====================

    /** 子流程 */
    SUB_PROCESS(40100, "子流程"),
    /** 事件子流程 */
    SUB_PROCESS_EVENT(40101, "事件子流程"),
    /** 调用活动 */
    CALL_ACTIVITY(40200, "调用活动"),

    // ==================== 其他 (50000-59999) ====================

    /** 顺序流(连线) - 不存储在节点表，仅用于解析 */
    SEQUENCE_FLOW(50100, "顺序流"),
    /** 多实例配置 - 不独立存在，通过节点配置字段标识 */
    MULTI_INSTANCE_BODY(50200, "多实例配置"),
    /** 文本注解 - 无执行语义，仅用于展示 */
    TEXT_ANNOTATION(50300, "文本注解");

    private final int code;
    private final String description;

    NodeTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据编码获取枚举
     */
    public static NodeTypeEnums fromCode(int code) {
        for (NodeTypeEnums type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }

    /**
     * 判断是否是事件类型
     */
    public boolean isEvent() {
        return code >= 10000 && code < 20000;
    }

    /**
     * 判断是否是任务类型
     */
    public boolean isTask() {
        return code >= 20000 && code < 30000;
    }

    /**
     * 判断是否是网关类型
     */
    public boolean isGateway() {
        return code >= 30000 && code < 40000;
    }

    /**
     * 判断是否是容器类型
     */
    public boolean isContainer() {
        return code >= 40000 && code < 50000;
    }

    /**
     * 获取主类型编码(万位)
     */
    public int getMainTypeCode() {
        return (code / 10000) * 10000;
    }

    /**
     * 获取主类型名称
     */
    public String getMainTypeName() {
        return switch (getMainTypeCode()) {
            case 10000 -> "EVENT";
            case 20000 -> "TASK";
            case 30000 -> "GATEWAY";
            case 40000 -> "CONTAINER";
            default -> "OTHER";
        };
    }
}
