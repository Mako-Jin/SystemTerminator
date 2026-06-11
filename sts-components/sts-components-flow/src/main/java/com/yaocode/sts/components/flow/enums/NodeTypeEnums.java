package com.yaocode.sts.components.flow.enums;

/**
 * 节点类型枚举
 */
public enum NodeTypeEnums {

    // ==================== 事件类 (Event) ====================

    /**
     * 开始事件
     * 流程实例的起点
     */
    START_EVENT,

    /**
     * 中间捕获事件
     * 等待特定事件触发后继续流程
     */
    INTERMEDIATE_CATCH_EVENT,

    /**
     * 中间抛出事件
     * 触发特定事件，可用于通知外部系统
     */
    INTERMEDIATE_THROW_EVENT,

    /**
     * 边界事件
     * 附加到任务或子流程边界，在特定条件下中断主流程
     */
    BOUNDARY_EVENT,

    /**
     * 结束事件
     * 流程实例的终点
     */
    END_EVENT,

    // ==================== 任务类 (Task) ====================

    /**
     * 服务任务
     * 自动执行的服务调用，如调用外部API或内部服务
     */
    SERVICE_TASK,

    /**
     * 用户任务
     * 需要人工处理的任务，会分配给特定用户或角色
     */
    USER_TASK,

    /**
     * 接收任务
     * 等待外部消息到达后继续流程
     */
    RECEIVE_TASK,

    /**
     * 手工任务
     * 需要人工执行但不在系统中完成的任务（如线下审批）
     */
    MANUAL_TASK,

    /**
     * 业务规则任务
     * 调用业务规则引擎执行决策
     */
    BUSINESS_RULE_TASK,

    /**
     * 脚本任务
     * 执行一段脚本代码（如JavaScript、Groovy等）
     */
    SCRIPT_TASK,

    /**
     * 发送任务
     * 向外部系统发送消息
     */
    SEND_TASK,

    // ==================== 网关类 (Gateway) ====================

    /**
     * 排他网关
     * 互斥分支，只能选择一条路径执行
     */
    EXCLUSIVE_GATEWAY,

    /**
     * 包容网关
     * 可多选分支，满足条件的路径都会执行
     */
    INCLUSIVE_GATEWAY,

    /**
     * 并行网关
     * 并行执行所有分支，需要汇聚
     */
    PARALLEL_GATEWAY,

    /**
     * 事件网关
     * 根据最先发生的事件决定执行路径
     */
    EVENT_BASED_GATEWAY,

    // ==================== 其他 (Other) ====================

    /**
     * 子流程
     * 内嵌在主流程中的独立流程
     */
    SUB_PROCESS,

    /**
     * 调用活动
     * 调用外部流程定义
     */
    CALL_ACTIVITY,

    /**
     * 顺序流
     * 节点之间的连线，定义流程流向
     */
    SEQUENCE_FLOW,

    /**
     * 多实例活动
     * 会签场景，需要多个参与者完成
     */
    MULTI_INSTANCE_BODY,

}