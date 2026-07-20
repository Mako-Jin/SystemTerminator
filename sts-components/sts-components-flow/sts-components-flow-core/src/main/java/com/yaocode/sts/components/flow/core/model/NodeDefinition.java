package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 节点定义
 *
 * <p>表示流程中的一个节点，包括：
 * <ul>
 *   <li>开始事件 (startEvent)</li>
 *   <li>结束事件 (endEvent)</li>
 *   <li>用户任务 (userTask)</li>
 *   <li>服务任务 (serviceTask)</li>
 *   <li>网关 (gateway)</li>
 *   <li>子流程 (subProcess)</li>
 *   <li>调用活动 (callActivity)</li>
 *   <li>中间事件 (intermediateCatchEvent, intermediateThrowEvent)</li>
 *   <li>边界事件 (boundaryEvent)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class NodeDefinition {

    // ==================== 基本信息 ====================

    /**
     * 节点唯一标识
     */
    private String nodeId;

    /**
     * 节点名称
     */
    private String name;

    /**
     * 节点类型
     * <p>可选值：startEvent, endEvent, userTask, serviceTask,
     * exclusiveGateway, parallelGateway, inclusiveGateway,
     * subProcess, callActivity, intermediateCatchEvent,
     * intermediateThrowEvent, boundaryEvent
     */
    private String type;

    /**
     * 行为类型
     * <p>根据节点类型不同，行为类型也不同：
     * <ul>
     *   <li>开始事件: none, timer, message, signal, error, escalation, compensation, conditional, link</li>
     *   <li>结束事件: none, error, terminate, cancel, signal, message, compensation, escalation</li>
     *   <li>服务任务: javaDelegate, delegateExpression, expression, script, webService</li>
     *   <li>网关: exclusive, parallel, inclusive, eventBased</li>
     *   <li>子流程: embeddedSubProcess, eventSubProcess</li>
     * </ul>
     */
    private String behaviorType;

    /**
     * 节点文档/描述
     */
    private String documentation;

    /**
     * 是否为作用域（子流程、事务等）
     */
    @Builder.Default
    private boolean scope = false;

    // ==================== 属性配置 ====================

    /**
     * 节点配置属性
     * <p>存储特定节点类型的配置，如：
     * <ul>
     *   <li>用户任务: assignee, candidateUsers, candidateGroups, dueDate, priority, formKey</li>
     *   <li>服务任务: delegateClass, delegateExpression, expression, resultVariable</li>
     *   <li>网关: defaultSequenceFlow</li>
     *   <li>开始事件: timerExpression, messageRef, signalRef</li>
     *   <li>边界事件: attachedToRef, cancelActivity</li>
     * </ul>
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 扩展属性（用于存储未在 properties 中定义的自定义属性）
     */
    @Builder.Default
    private Map<String, Object> extensions = new HashMap<>();

    // ==================== 父子关系 ====================

    /**
     * 父节点ID（用于子流程中的节点）
     */
    private String parentNodeId;

    /**
     * 子节点列表（用于子流程）
     */
    @Builder.Default
    private List<NodeDefinition> children = new ArrayList<>();

    @Builder.Default
    private List<Outgoing> outgoings = new ArrayList<>();
    @Builder.Default
    private List<Incoming> incomings = new ArrayList<>();

    // ==================== 坐标信息（用于可视化） ====================

    /**
     * BPMN DI 信息 - X 坐标
     */
    private Double x;

    /**
     * BPMN DI 信息 - Y 坐标
     */
    private Double y;

    /**
     * BPMN DI 信息 - 宽度
     */
    private Double width;

    /**
     * BPMN DI 信息 - 高度
     */
    private Double height;

    // ==================== 执行信息 ====================

    /**
     * 是否可异步执行
     */
    @Builder.Default
    private boolean async = false;

    /**
     * 是否可异步执行（排除）
     */
    @Builder.Default
    private boolean asyncExclusive = true;

    /**
     * 是否可作业执行
     */
    @Builder.Default
    private boolean jobExecutor = true;

    // ==================== 扩展信息 ====================

    /**
     * 输入参数映射（用于服务任务）
     */
    @Builder.Default
    private Map<String, String> inputParameters = new HashMap<>();

    /**
     * 输出参数映射（用于服务任务）
     */
    @Builder.Default
    private Map<String, String> outputParameters = new HashMap<>();

    /**
     * 自定义数据（用于业务扩展）
     */
    @Builder.Default
    private Map<String, Object> data = new HashMap<>();

    // ==================== 辅助方法 ====================

    /**
     * 添加属性
     */
    public void addProperty(String key, Object value) {
        if (key != null && value != null) {
            this.properties.put(key, value);
        }
    }

    /**
     * 获取属性（带默认值）
     */
    @SuppressWarnings("unchecked")
    public <T> T getProperty(String key, T defaultValue) {
        Object value = properties.get(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return (T) value;
        } catch (ClassCastException e) {
            return defaultValue;
        }
    }

    /**
     * 获取字符串属性
     */
    public String getStringProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * 获取字符串属性（带默认值）
     */
    public String getStringProperty(String key, String defaultValue) {
        return getProperty(key, defaultValue);
    }

    /**
     * 获取布尔属性
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        Boolean value = getProperty(key, null);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 获取整数属性
     */
    public Integer getIntProperty(String key, Integer defaultValue) {
        Object value = properties.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void addOutgoing(Outgoing outgoing) {
        if (outgoing == null) {
            return;
        }
        if (this.outgoings == null) {
            this.outgoings = new ArrayList<>();
        }
        // 设置反向引用
        outgoing.setSourceNodeId(this.nodeId);
        // 设置顺序
        if (outgoing.getOrder() == null) {
            outgoing.setOrder(this.outgoings.size());
        }
        this.outgoings.add(outgoing);
    }

    public void addOutgoing(String sequenceFlowId) {
        if (sequenceFlowId == null || sequenceFlowId.isEmpty()) {
            return;
        }
        Outgoing outgoing = Outgoing.builder()
                .sequenceFlowId(sequenceFlowId)
                .sourceNodeId(this.nodeId)
                .order(this.outgoings.size())
                .build();
        this.outgoings.add(outgoing);
    }

    public void addIncoming(Incoming incoming) {
        if (incoming == null) {
            return;
        }
        if (this.incomings == null) {
            this.incomings = new ArrayList<>();
        }
        incoming.setTargetNodeId(this.nodeId);
        this.incomings.add(incoming);
    }

    public void addIncoming(String sequenceFlowId) {
        if (sequenceFlowId == null || sequenceFlowId.isEmpty()) {
            return;
        }
        Incoming incoming = Incoming.builder()
                .sequenceFlowId(sequenceFlowId)
                .targetNodeId(this.nodeId)
                .build();
        this.incomings.add(incoming);
    }

    /**
     * 获取所有出口序列流 ID（便捷方法）
     */
    public List<String> getOutgoingFlowIds() {
        List<String> flowIds = new ArrayList<>();
        if (outgoings != null) {
            for (Outgoing outgoing : outgoings) {
                if (outgoing.getSequenceFlowId() != null) {
                    flowIds.add(outgoing.getSequenceFlowId());
                }
            }
        }
        return flowIds;
    }

    /**
     * 获取所有入口序列流 ID（便捷方法）
     */
    public List<String> getIncomingFlowIds() {
        List<String> flowIds = new ArrayList<>();
        if (incomings != null) {
            for (Incoming incoming : incomings) {
                if (incoming.getSequenceFlowId() != null) {
                    flowIds.add(incoming.getSequenceFlowId());
                }
            }
        }
        return flowIds;
    }

    /**
     * 添加子节点
     */
    public void addChild(NodeDefinition child) {
        if (child != null) {
            child.setParentNodeId(this.nodeId);
            this.children.add(child);
        }
    }

    /**
     * 添加输入参数
     */
    public void addInputParameter(String name, String value) {
        if (name != null && value != null) {
            this.inputParameters.put(name, value);
        }
    }

    /**
     * 添加输出参数
     */
    public void addOutputParameter(String name, String value) {
        if (name != null && value != null) {
            this.outputParameters.put(name, value);
        }
    }

    /**
     * 判断是否为开始事件
     */
    public boolean isStartEvent() {
        return "startEvent".equals(type);
    }

    /**
     * 判断是否为结束事件
     */
    public boolean isEndEvent() {
        return "endEvent".equals(type);
    }

    /**
     * 判断是否为用户任务
     */
    public boolean isUserTask() {
        return "userTask".equals(type);
    }

    /**
     * 判断是否为服务任务
     */
    public boolean isServiceTask() {
        return "serviceTask".equals(type);
    }

    /**
     * 判断是否为网关
     */
    public boolean isGateway() {
        return type != null && type.endsWith("Gateway");
    }

    /**
     * 判断是否为子流程
     */
    public boolean isSubProcess() {
        return "subProcess".equals(type);
    }

    /**
     * 判断是否为调用活动
     */
    public boolean isCallActivity() {
        return "callActivity".equals(type);
    }

    /**
     * 判断是否为中间事件
     */
    public boolean isIntermediateEvent() {
        return type != null && type.startsWith("intermediate");
    }

    /**
     * 判断是否为边界事件
     */
    public boolean isBoundaryEvent() {
        return "boundaryEvent".equals(type);
    }

    /**
     * 获取节点类型的显示名称
     */
    public String getDisplayType() {
        if (type == null) {
            return "Unknown";
        }
        return switch (type) {
            case "startEvent" -> "开始事件";
            case "endEvent" -> "结束事件";
            case "userTask" -> "用户任务";
            case "serviceTask" -> "服务任务";
            case "exclusiveGateway" -> "排他网关";
            case "parallelGateway" -> "并行网关";
            case "inclusiveGateway" -> "包容网关";
            case "eventBasedGateway" -> "事件网关";
            case "subProcess" -> "子流程";
            case "callActivity" -> "调用活动";
            case "intermediateCatchEvent" -> "中间捕获事件";
            case "intermediateThrowEvent" -> "中间抛出事件";
            case "boundaryEvent" -> "边界事件";
            default -> type;
        };
    }

    /**
     * 获取行为类型的显示名称
     */
    public String getDisplayBehavior() {
        if (behaviorType == null) {
            return "默认";
        }
        return switch (behaviorType) {
            case "none" -> "无事件";
            case "timerStartEvent" -> "定时器开始";
            case "messageStartEvent" -> "消息开始";
            case "signalStartEvent" -> "信号开始";
            case "errorEndEvent" -> "错误结束";
            case "terminateEndEvent" -> "终止结束";
            case "cancelEndEvent" -> "取消结束";
            case "exclusive" -> "排他";
            case "parallel" -> "并行";
            case "inclusive" -> "包容";
            case "embeddedSubProcess" -> "嵌入式子流程";
            case "eventSubProcess" -> "事件子流程";
            case "javaDelegate" -> "Java委托";
            case "delegateExpression" -> "委托表达式";
            case "expression" -> "表达式";
            default -> behaviorType;
        };
    }

    @Override
    public String toString() {
        return String.format("NodeDefinition{id='%s', name='%s', type='%s', behavior='%s'}",
                nodeId, name, type, behaviorType);
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建开始事件
     */
    public static NodeDefinition createStartEvent(String id, String name) {
        return NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("startEvent")
                .behaviorType("none")
                .build();
    }

    /**
     * 创建结束事件
     */
    public static NodeDefinition createEndEvent(String id, String name) {
        return NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("endEvent")
                .behaviorType("none")
                .build();
    }

    /**
     * 创建用户任务
     */
    public static NodeDefinition createUserTask(String id, String name) {
        return NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("userTask")
                .behaviorType("userTask")
                .build();
    }

    /**
     * 创建服务任务
     */
    public static NodeDefinition createServiceTask(String id, String name, String delegateClass) {
        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("serviceTask")
                .behaviorType("javaDelegate")
                .build();
        nodeDefinition.addProperty("delegateClass", delegateClass);
        return nodeDefinition;
    }

    /**
     * 创建排他网关
     */
    public static NodeDefinition createExclusiveGateway(String id, String name) {
        return NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("exclusiveGateway")
                .behaviorType("exclusive")
                .build();
    }

    /**
     * 创建并行网关
     */
    public static NodeDefinition createParallelGateway(String id, String name) {
        return NodeDefinition.builder()
                .nodeId(id)
                .name(name)
                .type("parallelGateway")
                .behaviorType("parallel")
                .build();
    }
}
