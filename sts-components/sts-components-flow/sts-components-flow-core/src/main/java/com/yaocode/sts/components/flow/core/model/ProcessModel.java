package com.yaocode.sts.components.flow.core.model;

import com.yaocode.sts.components.flow.core.enums.NodeTypeEnums;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 流程模型
 *
 * <p>解析器输出的流程结构数据，不包含版本、租户等元数据。
 * 这是流程定义的"内容"部分，与 ProcessDefinition 是组合关系。</p>
 *
 * <p>使用场景：
 * <ul>
 *   <li>解析器将 XML/JSON 解析为 ProcessModel</li>
 *   <li>DeploymentBuilder 将 ProcessModel 包装为 ProcessDefinition 并持久化</li>
 *   <li>运行时引擎根据 ProcessModel 驱动流程执行</li>
 * </ul>
 * </p>
 *
 * @author yourname
 */
@Data
public class ProcessModel {

    // ==================== 基本信息 ====================

    /**
     * 流程 Key（唯一标识，如 "leave", "purchase"）
     */
    private String processKey;

    /**
     * 流程名称（如 "请假流程", "采购审批流程"）
     */
    private String name;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 版本标签（用户自定义，如 "v2.0", "紧急修复版"）
     */
    private String versionTag;

    /**
     * 流程分类（如 "OA", "ERP", "CRM"）
     */
    private String category;

    // ==================== 流程结构 ====================

    /**
     * 流程节点列表（开始事件、用户任务、网关、结束事件等）
     */
    private List<Node> nodes = new ArrayList<>();

    /**
     * 流程流转列表（节点之间的连线）
     */
    private List<Transition> transitions = new ArrayList<>();

    /**
     * 流程变量定义（流程级变量）
     */
    private List<VariableDefinition> variables = new ArrayList<>();

    /**
     * 流程监听器（流程级监听）
     */
    private List<ListenerDefinition> listeners = new ArrayList<>();

    // ==================== 配置信息 ====================

    /**
     * 历史记录保留天数（null 表示永久保留）
     */
    private Integer historyTimeToLive;

    /**
     * 是否允许并发执行（并行多实例等）
     */
    private boolean isParallel = false;

    /**
     * 是否可中断（用于事务型流程）
     */
    private boolean isInterruptible = true;

    // ==================== 扩展 ====================

    /**
     * 扩展属性（用于存储引擎特定的扩展信息）
     */
    private Map<String, Object> extensions = new HashMap<>();

    /**
     * 文档（用于存储流程相关的文档、附件等）
     */
    private Map<String, String> documentation = new HashMap<>();

    // ==================== 构造方法 ====================

    public ProcessModel() {
    }

    public ProcessModel(String processKey, String name) {
        this.processKey = processKey;
        this.name = name;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes != null ? nodes : new ArrayList<>();
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions != null ? transitions : new ArrayList<>();
    }

    public void setVariables(List<VariableDefinition> variables) {
        this.variables = variables != null ? variables : new ArrayList<>();
    }

    public void setListeners(List<ListenerDefinition> listeners) {
        this.listeners = listeners != null ? listeners : new ArrayList<>();
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions != null ? extensions : new HashMap<>();
    }

    public void setDocumentation(Map<String, String> documentation) {
        this.documentation = documentation != null ? documentation : new HashMap<>();
    }

    // ==================== 便捷方法 ====================

    /**
     * 添加节点
     */
    public ProcessModel addNode(Node node) {
        this.nodes.add(node);
        return this;
    }

    /**
     * 添加流转
     */
    public ProcessModel addTransition(Transition transition) {
        this.transitions.add(transition);
        return this;
    }

    /**
     * 添加变量定义
     */
    public ProcessModel addVariable(VariableDefinition variable) {
        this.variables.add(variable);
        return this;
    }

    /**
     * 添加监听器
     */
    public ProcessModel addListener(ListenerDefinition listener) {
        this.listeners.add(listener);
        return this;
    }

    /**
     * 根据 ID 获取节点
     */
    public Node getNodeById(String nodeId) {
        if (nodeId == null) {
            return null;
        }
        for (Node node : nodes) {
            if (nodeId.equals(node.getId())) {
                return node;
            }
        }
        return null;
    }

    /**
     * 获取开始节点
     */
    public Node getStartNode() {
        for (Node node : nodes) {
            if (node.getNodeType() == NodeTypeEnums.START_EVENT) {
                return node;
            }
        }
        return null;
    }

    /**
     * 获取结束节点列表
     */
    public List<Node> getEndNodes() {
        List<Node> endNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getNodeType() == NodeTypeEnums.END_EVENT) {
                endNodes.add(node);
            }
        }
        return endNodes;
    }

    /**
     * 获取用户任务节点列表
     */
    public List<Node> getUserTaskNodes() {
        List<Node> taskNodes = new ArrayList<>();
        for (Node node : nodes) {
            if (node.getNodeType() == NodeTypeEnums.USER_TASK) {
                taskNodes.add(node);
            }
        }
        return taskNodes;
    }

    /**
     * 获取从指定节点出发的流转
     */
    public List<Transition> getOutgoingTransitions(String sourceId) {
        List<Transition> result = new ArrayList<>();
        for (Transition transition : transitions) {
            if (sourceId.equals(transition.getSourceId())) {
                result.add(transition);
            }
        }
        return result;
    }

    /**
     * 获取到达指定节点的流转
     */
    public List<Transition> getIncomingTransitions(String targetId) {
        List<Transition> result = new ArrayList<>();
        for (Transition transition : transitions) {
            if (targetId.equals(transition.getTargetId())) {
                result.add(transition);
            }
        }
        return result;
    }

    // ==================== Object 方法 ====================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessModel that = (ProcessModel) o;
        return Objects.equals(processKey, that.processKey) &&
                Objects.equals(name, that.name) &&
                Objects.equals(nodes, that.nodes) &&
                Objects.equals(transitions, that.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(processKey, name, nodes, transitions);
    }

    @Override
    public String toString() {
        return "ProcessModel{" +
                "processKey='" + processKey + '\'' +
                ", name='" + name + '\'' +
                ", nodeCount=" + (nodes != null ? nodes.size() : 0) +
                ", transitionCount=" + (transitions != null ? transitions.size() : 0) +
                '}';
    }

    // ==================== 内部类：Node ====================

    /**
     * 流程节点
     */
    @Data
    public static class Node {

        private String id;
        private String name;
        private NodeTypeEnums nodeType;
        private String assignee;              // 办理人（用户任务）
        private List<String> candidateUsers;  // 候选用户（用户任务）
        private List<String> candidateGroups; // 候选组（用户任务）
        private String formKey;               // 表单 Key
        private String dueDate;               // 到期时间表达式
        private String priority;              // 优先级
        private Map<String, Object> extensions = new HashMap<>();

        public Node() {
        }

        public Node(String id, String name, NodeTypeEnums nodeType) {
            this.id = id;
            this.name = name;
            this.nodeType = nodeType;
        }

    }

    // ==================== 内部类：Transition ====================

    /**
     * 流转（节点之间的连线）
     */
    @Data
    public static class Transition {

        private String id;
        private String sourceId;      // 源节点 ID
        private String targetId;      // 目标节点 ID
        private String condition;     // 条件表达式（用于网关分支）
        private String name;          // 流转名称

        public Transition() {
        }

        public Transition(String id, String sourceId, String targetId) {
            this.id = id;
            this.sourceId = sourceId;
            this.targetId = targetId;
        }

    }

    // ==================== 内部类：VariableDefinition ====================

    /**
     * 变量定义
     */
    @Data
    public static class VariableDefinition {

        private String name;
        private String type;          // string, integer, boolean, date, etc.
        private Object defaultValue;
        private boolean required;
        private String description;

        public VariableDefinition() {
        }

        public VariableDefinition(String name, String type) {
            this.name = name;
            this.type = type;
        }

    }

    // ==================== 内部类：ListenerDefinition ====================

    /**
     * 监听器定义
     */
    @Data
    public static class ListenerDefinition{

        private String event;          // start, end, take, etc.
        private String className;      // 监听器全类名
        private String expression;     // 表达式（可选）
        private String delegateExpression; // 委托表达式（可选）
        private Map<String, String> fields = new HashMap<>();

        public ListenerDefinition() {
        }

        public ListenerDefinition(String event, String className) {
            this.event = event;
            this.className = className;
        }
    }

}
