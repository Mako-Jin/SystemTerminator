package com.yaocode.sts.components.flow.core.model;

import com.yaocode.sts.components.flow.core.engine.parser.ValidationResult;
import com.yaocode.sts.components.flow.core.enums.NodeTypeEnums;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程定义
 *
 * <p>包含流程的所有节点、连线和其他定义
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ProcessDefinition {

    // ==================== 基本信息 ====================

    /**
     * 流程唯一标识
     */
    private String processId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 流程分类
     */
    private String category;

    /**
     * 版本号（数值递增，如 1, 2, 3...）
     */
    @Builder.Default
    private Integer version = 1;

    /**
     * 版本标签（语义化版本，如 "v1.0.0"）
     */
    @Builder.Default
    private String versionTag = "1.0.0";

    /**
     * BPMN 资源文件名
     */
    private String resourceName;

    /**
     * 流程图资源文件名
     */
    private String diagramResourceName;

    /**
     * 挂起状态：0=激活，1=挂起
     */
    @Builder.Default
    private Integer suspensionState = 0;

    /**
     * 历史数据保留天数（null表示永久保留）
     */
    private Integer historyTimeToLive;

    /**
     * 是否可启动
     */
    @Builder.Default
    private boolean startable = true;

    /**
     * 是否可执行
     */
    @Builder.Default
    private boolean executable = true;

    // ==================== 节点和连线 ====================

    /**
     * 流程节点列表
     */
    @Builder.Default
    private List<NodeDefinition> nodes = new ArrayList<>();

    /**
     * 流程连线列表
     */
    @Builder.Default
    private List<SequenceDefinition> sequences = new ArrayList<>();

    // ==================== 扩展定义 ====================

    /**
     * 消息定义列表
     */
    @Builder.Default
    private List<MessageDefinition> messages = new ArrayList<>();

    /**
     * 信号定义列表
     */
    @Builder.Default
    private List<SignalDefinition> signals = new ArrayList<>();

    /**
     * 错误定义列表
     */
    @Builder.Default
    private List<ErrorDefinition> errors = new ArrayList<>();

    /**
     * 扩展属性
     */
    @Builder.Default
    private Map<String, Object> extensions = new HashMap<>();

    // ==================== 辅助方法 ====================

    /**
     * 添加节点
     */
    public void addNode(NodeDefinition node) {
        if (node != null) {
            if (this.nodes == null) {
                this.nodes = new ArrayList<>();
            }
            this.nodes.add(node);
        }
    }

    /**
     * 添加连线
     */
    public void addSequence(SequenceDefinition sequence) {
        if (sequence != null) {
            if (this.sequences == null) {
                this.sequences = new ArrayList<>();
            }
            this.sequences.add(sequence);
        }
    }

    /**
     * 添加消息
     */
    public void addMessage(MessageDefinition message) {
        if (message != null) {
            this.messages.add(message);
        }
    }

    /**
     * 添加信号
     */
    public void addSignal(SignalDefinition signal) {
        if (signal != null) {
            this.signals.add(signal);
        }
    }

    /**
     * 添加错误
     */
    public void addError(ErrorDefinition error) {
        if (error != null) {
            this.errors.add(error);
        }
    }

    /**
     * 获取开始节点
     */
    public NodeDefinition getStartNode() {
        if (nodes == null) {
            return null;
        }
        return nodes.stream()
                .filter(n -> NodeTypeEnums.START_EVENT.getType().equals(n.getType()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取所有开始节点
     */
    public List<NodeDefinition> getStartNodes() {
        if (nodes == null) {
            return new ArrayList<>();
        }
        return nodes.stream()
                .filter(n -> NodeTypeEnums.START_EVENT.getType().equals(n.getType()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取结束节点列表
     */
    public List<NodeDefinition> getEndNodes() {
        if (nodes == null) {
            return new ArrayList<>();
        }
        return nodes.stream()
                .filter(n -> NodeTypeEnums.END_EVENT.getType().equals(n.getType()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 根据ID获取节点
     */
    public NodeDefinition getNodeById(String nodeId) {
        if (nodes == null || nodeId == null) {
            return null;
        }
        return nodes.stream()
                .filter(n -> nodeId.equals(n.getNodeId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 根据类型获取节点列表
     */
    public List<NodeDefinition> getNodesByType(NodeTypeEnums typeEnums) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        return nodes.stream()
                .filter(n -> typeEnums.getType().equals(n.getType()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取用户任务节点列表
     */
    public List<NodeDefinition> getUserTasks() {
        return getNodesByType(NodeTypeEnums.USER_TASK);
    }

    /**
     * 获取服务任务节点列表
     */
    public List<NodeDefinition> getServiceTasks() {
        return getNodesByType(NodeTypeEnums.SERVICE_TASK);
    }

    /**
     * 获取网关节点列表
     */
    public List<NodeDefinition> getGateways() {
        if (nodes == null) {
            return new ArrayList<>();
        }
        return nodes.stream()
                .filter(NodeDefinition::isGateway)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取子流程节点列表
     */
    public List<NodeDefinition> getSubProcesses() {
        return getNodesByType(NodeTypeEnums.SUB_PROCESS);
    }

    /**
     * 获取指定节点的出边
     */
    public List<SequenceDefinition> getOutgoingSequences(String nodeId) {
        if (sequences == null || nodeId == null) {
            return new ArrayList<>();
        }
        return sequences.stream()
                .filter(s -> nodeId.equals(s.getSourceRef()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取指定节点的入边
     */
    public List<SequenceDefinition> getIncomingSequences(String nodeId) {
        if (sequences == null || nodeId == null) {
            return new ArrayList<>();
        }
        return sequences.stream()
                .filter(s -> nodeId.equals(s.getTargetRef()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 根据ID获取连线
     */
    public SequenceDefinition getSequenceById(String sequenceId) {
        if (sequences == null || sequenceId == null) {
            return null;
        }
        return sequences.stream()
                .filter(s -> sequenceId.equals(s.getSequenceId()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取所有条件连线
     */
    public List<SequenceDefinition> getConditionalSequences() {
        if (sequences == null) {
            return new ArrayList<>();
        }
        return sequences.stream()
                .filter(SequenceDefinition::hasCondition)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取所有默认连线
     */
    public List<SequenceDefinition> getDefaultSequences() {
        if (sequences == null) {
            return new ArrayList<>();
        }
        return sequences.stream()
                .filter(SequenceDefinition::isDefault)
                .collect(java.util.stream.Collectors.toList());
    }

    // ==================== 状态判断 ====================

    /**
     * 是否已挂起
     */
    public boolean isSuspended() {
        return suspensionState != null && suspensionState == 1;
    }

    /**
     * 是否激活
     */
    public boolean isActive() {
        return suspensionState != null && suspensionState == 0;
    }

    /**
     * 验证流程定义是否完整
     */
    public ValidationResult validate() {
        ValidationResult result = new ValidationResult("ProcessDefinitionValidator");

        // 1. 检查是否有开始节点
        if (getStartNode() == null) {
            result.addError("流程必须包含至少一个开始事件 (startEvent)");
        }

        // 2. 检查是否有结束节点
        if (getEndNodes().isEmpty()) {
            result.addWarning("流程建议包含至少一个结束事件 (endEvent)");
        }

        // 3. 检查节点ID唯一性
        if (nodes != null) {
            Map<String, NodeDefinition> nodeMap = new HashMap<>();
            for (NodeDefinition node : nodes) {
                if (nodeMap.containsKey(node.getNodeId())) {
                    result.addError("重复的节点ID: " + node.getNodeId());
                }
                nodeMap.put(node.getNodeId(), node);
            }
        }

        // 4. 检查连线引用是否有效
        if (sequences != null && nodes != null) {
            for (SequenceDefinition seq : sequences) {
                boolean sourceExists = nodes.stream()
                        .anyMatch(n -> seq.getSourceRef().equals(n.getNodeId()));
                boolean targetExists = nodes.stream()
                        .anyMatch(n -> seq.getTargetRef().equals(n.getNodeId()));

                if (!sourceExists) {
                    result.addError("连线 '" + seq.getSequenceId() + "' 的源节点不存在: " + seq.getSourceRef());
                }
                if (!targetExists) {
                    result.addError("连线 '" + seq.getSequenceId() + "' 的目标节点不存在: " + seq.getTargetRef());
                }
            }
        }

        // 5. 检查网关配置
        if (nodes != null) {
            for (NodeDefinition node : nodes) {
                if (node.isGateway()) {
                    // 检查网关是否有出边
                    List<SequenceDefinition> outgoing = getOutgoingSequences(node.getNodeId());
                    if (outgoing.isEmpty()) {
                        result.addWarning("网关 '" + node.getNodeId() + "' 没有出边");
                    }
                }
            }
        }

        // 6. 检查孤立节点
        if (nodes != null) {
            for (NodeDefinition node : nodes) {
                if (!node.isStartEvent() && !node.isEndEvent()) {
                    List<SequenceDefinition> incoming = getIncomingSequences(node.getNodeId());
                    List<SequenceDefinition> outgoing = getOutgoingSequences(node.getNodeId());
                    if (incoming.isEmpty() && outgoing.isEmpty()) {
                        result.addWarning("节点 '" + node.getNodeId() + "' 是孤立节点（无入边和出边）");
                    }
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return String.format("ProcessDefinition{id='%s', name='%s', nodes=%d, sequences=%d}",
                processId, processName, nodes != null ? nodes.size() : 0,
                sequences != null ? sequences.size() : 0);
    }

}