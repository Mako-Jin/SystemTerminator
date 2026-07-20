package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 节点类型枚举
 * 采用三级编码结构: 主类型(万位) + 大类(千位) + 子类型(百位)
 * 编码规则: 10000-19999事件类, 20000-29999任务类, 30000-39999网关类, 40000-49999容器类
 */
@Getter
public enum NodeTypeEnums {

    // ==================== 事件类 (10000-19999) ====================

    /** 开始事件 */
    START_EVENT(10100, "startEvent", "开始事件"),
    /** 定时开始事件 */
    START_EVENT_TIMER(10101, "startEvent", "定时开始事件"),
    /** 消息开始事件 */
    START_EVENT_MESSAGE(10102, "startEvent", "消息开始事件"),
    /** 信号开始事件 */
    START_EVENT_SIGNAL(10103, "startEvent", "信号开始事件"),

    /** 中间捕获事件 */
    INTERMEDIATE_CATCH_EVENT(10200, "intermediateCatchEvent", "中间捕获事件"),
    /** 中间定时捕获 */
    INTERMEDIATE_CATCH_TIMER(10201, "intermediateCatchEvent", "中间定时捕获"),
    /** 中间消息捕获 */
    INTERMEDIATE_CATCH_MESSAGE(10202, "intermediateCatchEvent", "中间消息捕获"),
    /** 中间信号捕获 */
    INTERMEDIATE_CATCH_SIGNAL(10203, "intermediateCatchEvent", "中间信号捕获"),

    /** 中间抛出事件 */
    INTERMEDIATE_THROW_EVENT(10300, "intermediateThrowEvent", "中间抛出事件"),
    /** 中间消息抛出 */
    INTERMEDIATE_THROW_MESSAGE(10301, "intermediateThrowEvent", "中间消息抛出"),
    /** 中间信号抛出 */
    INTERMEDIATE_THROW_SIGNAL(10302, "intermediateThrowEvent", "中间信号抛出"),

    /** 边界事件 */
    BOUNDARY_EVENT(10400, "boundaryEvent", "边界事件"),
    /** 定时边界(超时) */
    BOUNDARY_TIMER(10401, "boundaryEvent", "定时边界"),
    /** 错误边界 */
    BOUNDARY_ERROR(10402, "boundaryEvent", "错误边界"),
    /** 消息边界 */
    BOUNDARY_MESSAGE(10403, "boundaryEvent", "消息边界"),
    /** 信号边界 */
    BOUNDARY_SIGNAL(10404, "boundaryEvent", "信号边界"),
    /** 补偿边界 */
    BOUNDARY_COMPENSATION(10406, "boundaryEvent", "补偿边界"),

    /** 结束事件 */
    END_EVENT(10500, "endEvent", "结束事件"),
    /** 终止结束事件 */
    END_TERMINATE(10501, "endEvent", "终止结束事件"),
    /** 错误结束事件 */
    END_ERROR(10502, "endEvent", "错误结束事件"),
    /** 消息结束事件 */
    END_MESSAGE(10503, "endEvent", "消息结束事件"),
    /** 信号结束事件 */
    END_SIGNAL(10504, "endEvent", "信号结束事件"),

    // ==================== 任务类 (20000-29999) ====================

    /** 用户任务 */
    USER_TASK(20100, "userTask", "用户任务"),
    /** 服务任务 */
    SERVICE_TASK(20200, "serviceTask", "服务任务"),
    /** 脚本任务 */
    SCRIPT_TASK(20300, "scriptTask", "脚本任务"),
    /** 业务规则任务 */
    BUSINESS_RULE_TASK(20400, "businessRuleTask", "业务规则任务"),
    /** 发送任务 */
    SEND_TASK(20500, "sendTask", "发送任务"),
    /** 接收任务 */
    RECEIVE_TASK(20600, "receiveTask", "接收任务"),
    /** 手工任务 */
    MANUAL_TASK(20700, "manualTask", "手工任务"),
    MAIL_TASK(20800, "mailTask", "邮件任务"),

    // ==================== 网关类 (30000-39999) ====================

    /** 排他网关 */
    EXCLUSIVE_GATEWAY(30100, "exclusiveGateway", "排他网关"),
    /** 包容网关 */
    INCLUSIVE_GATEWAY(30200, "inclusiveGateway", "包容网关"),
    /** 并行网关 */
    PARALLEL_GATEWAY(30300, "parallelGateway", "并行网关"),
    /** 事件网关 */
    EVENT_BASED_GATEWAY(30400, "eventBasedGateway", "事件网关"),

    // ==================== 容器类 (40000-49999) ====================

    /** 子流程 */
    SUB_PROCESS(40100, "subProcess", "子流程"),
    /** 事件子流程 */
    SUB_PROCESS_EVENT(40101, "subProcess", "事件子流程"),
    /** 调用活动 */
    CALL_ACTIVITY(40200, "callActivity", "调用活动"),

    // ==================== 其他 (50000-59999) ====================

    /** 顺序流(连线) - 不存储在节点表，仅用于解析 */
    SEQUENCE_FLOW(50100, "sequenceFlow", "顺序流"),
    /** 多实例配置 - 不独立存在，通过节点配置字段标识 */
    MULTI_INSTANCE_BODY(50200, "multiInstanceBody", "多实例配置"),
    /** 文本注解 - 无执行语义，仅用于展示 */
    TEXT_ANNOTATION(50300, "textAnnotation", "文本注解");

    /**
     * 编码（唯一标识）
     */
    private final int code;

    /**
     * BPMN 标准类型名称（如 startEvent, userTask）
     */
    private final String type;

    /**
     * 中文描述
     */
    private final String description;

    /**
     * 缓存：type -> 枚举列表（用于快速查找）
     */
    private static final Map<String, NodeTypeEnums> TYPE_CACHE = new ConcurrentHashMap<>();

    /**
     * 缓存：code -> 枚举（用于快速查找）
     */
    private static final Map<Integer, NodeTypeEnums> CODE_CACHE = new ConcurrentHashMap<>();

    static {
        for (NodeTypeEnums value : values()) {
            TYPE_CACHE.put(value.type, value);
            CODE_CACHE.put(value.code, value);
        }
    }

    NodeTypeEnums(int code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }

    // ==================== 查询方法 ====================

    /**
     * 根据编码获取枚举
     */
    public static NodeTypeEnums fromCode(int code) {
        return CODE_CACHE.get(code);
    }

    /**
     * 根据类型名称获取枚举
     * 注意：如果有多个同类型（如 START_EVENT 和 START_EVENT_TIMER 都是 startEvent），
     * 返回第一个匹配的（通常是基础类型）
     */
    public static NodeTypeEnums fromType(String type) {
        if (type == null) {
            return null;
        }
        return TYPE_CACHE.get(type);
    }

    /**
     * 根据类型名称获取所有匹配的枚举（包含子类型）
     */
    public static java.util.List<NodeTypeEnums> fromTypeWithSubtypes(String type) {
        if (type == null) {
            return java.util.Collections.emptyList();
        }
        return Arrays.stream(values())
                .filter(e -> e.type.equals(type))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 根据类型名称判断是否匹配（忽略具体子类型）
     */
    public boolean matchesType(String type) {
        return this.type.equals(type);
    }

    // ==================== 类型判断方法 ====================

    /**
     * 判断是否是事件类型
     */
    public boolean isEvent() {
        return code >= 10000 && code < 20000;
    }

    /**
     * 判断是否是开始事件
     */
    public boolean isStartEvent() {
        return code >= 10100 && code < 10200;
    }

    /**
     * 判断是否是结束事件
     */
    public boolean isEndEvent() {
        return code >= 10500 && code < 10600;
    }

    /**
     * 判断是否是中间事件
     */
    public boolean isIntermediateEvent() {
        return code >= 10200 && code < 10400;
    }

    /**
     * 判断是否是边界事件
     */
    public boolean isBoundaryEvent() {
        return code >= 10400 && code < 10500;
    }

    /**
     * 判断是否是任务类型
     */
    public boolean isTask() {
        return code >= 20000 && code < 30000;
    }

    /**
     * 判断是否是用户任务
     */
    public boolean isUserTask() {
        return this == USER_TASK;
    }

    /**
     * 判断是否是服务任务
     */
    public boolean isServiceTask() {
        return this == SERVICE_TASK;
    }

    /**
     * 判断是否是网关类型
     */
    public boolean isGateway() {
        return code >= 30000 && code < 40000;
    }

    /**
     * 判断是否是排他网关
     */
    public boolean isExclusiveGateway() {
        return this == EXCLUSIVE_GATEWAY;
    }

    /**
     * 判断是否是并行网关
     */
    public boolean isParallelGateway() {
        return this == PARALLEL_GATEWAY;
    }

    /**
     * 判断是否是包容网关
     */
    public boolean isInclusiveGateway() {
        return this == INCLUSIVE_GATEWAY;
    }

    /**
     * 判断是否是容器类型（子流程、调用活动等）
     */
    public boolean isContainer() {
        return code >= 40000 && code < 50000;
    }

    /**
     * 判断是否是子流程
     */
    public boolean isSubProcess() {
        return code >= 40100 && code < 40200;
    }

    /**
     * 判断是否是可执行的节点（排除顺序流、文本注解等）
     */
    public boolean isExecutable() {
        return !(this == SEQUENCE_FLOW || this == TEXT_ANNOTATION || this == MULTI_INSTANCE_BODY);
    }

    // ==================== 分类信息 ====================

    /**
     * 获取主类型编码（万位）
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

    /**
     * 获取主类型的中文名称
     */
    public String getMainTypeChineseName() {
        return switch (getMainTypeCode()) {
            case 10000 -> "事件";
            case 20000 -> "任务";
            case 30000 -> "网关";
            case 40000 -> "容器";
            default -> "其他";
        };
    }

    /**
     * 获取大类编码（千位）
     */
    public int getCategoryCode() {
        return (code / 1000) * 1000;
    }

    /**
     * 获取大类名称
     */
    public String getCategoryName() {
        return switch (getCategoryCode()) {
            // 事件类
            case 10000 -> "未分类事件";
            case 10100 -> "开始事件";
            case 10200 -> "中间捕获事件";
            case 10300 -> "中间抛出事件";
            case 10400 -> "边界事件";
            case 10500 -> "结束事件";
            // 任务类
            case 20000 -> "未分类任务";
            case 20100 -> "用户任务";
            case 20200 -> "服务任务";
            case 20300 -> "脚本任务";
            case 20400 -> "业务规则任务";
            case 20500 -> "发送任务";
            case 20600 -> "接收任务";
            case 20700 -> "手工任务";
            // 网关类
            case 30000 -> "未分类网关";
            case 30100 -> "排他网关";
            case 30200 -> "包容网关";
            case 30300 -> "并行网关";
            case 30400 -> "事件网关";
            // 容器类
            case 40000 -> "未分类容器";
            case 40100 -> "子流程";
            case 40200 -> "调用活动";
            default -> "其他";
        };
    }

    // ==================== 常用工具方法 ====================

    /**
     * 获取所有事件类型
     */
    public static java.util.List<NodeTypeEnums> getEventTypes() {
        return Arrays.stream(values())
                .filter(NodeTypeEnums::isEvent)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取所有任务类型
     */
    public static java.util.List<NodeTypeEnums> getTaskTypes() {
        return Arrays.stream(values())
                .filter(NodeTypeEnums::isTask)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取所有网关类型
     */
    public static java.util.List<NodeTypeEnums> getGatewayTypes() {
        return Arrays.stream(values())
                .filter(NodeTypeEnums::isGateway)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取所有容器类型
     */
    public static java.util.List<NodeTypeEnums> getContainerTypes() {
        return Arrays.stream(values())
                .filter(NodeTypeEnums::isContainer)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取所有可执行节点类型
     */
    public static java.util.List<NodeTypeEnums> getExecutableTypes() {
        return Arrays.stream(values())
                .filter(NodeTypeEnums::isExecutable)
                .collect(java.util.stream.Collectors.toList());
    }

    // ==================== 兼容性方法 ====================

    /**
     * 获取 BPMN 标准类型名称（用于 XML 序列化）
     * 与 getType() 相同，保留此方法便于语义理解
     */
    public String getBpmnType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s{code=%d, type='%s', desc='%s'}",
                name(), code, type, description);
    }
}