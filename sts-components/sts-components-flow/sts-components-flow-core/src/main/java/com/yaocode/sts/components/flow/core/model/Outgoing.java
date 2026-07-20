package com.yaocode.sts.components.flow.core.model;

import lombok.Data;

import java.util.Objects;

/**
 * 出口引用
 *
 * <p>表示一个节点到序列流的出口引用，对应 BPMN 中的 bpmn:outgoing 元素。
 *
 * <p>BPMN 语义：
 * <pre>
 * &lt;bpmn:startEvent id="StartEvent_1"&gt;
 *     &lt;bpmn:outgoing&gt;Flow_1yqx94j&lt;/bpmn:outgoing&gt;
 * &lt;/bpmn:startEvent&gt;
 * </pre>
 *
 * @author Process Engine Team
 */
@Data
public class Outgoing {

    /**
     * 引用的序列流 ID
     */
    private String sequenceFlowId;

    /**
     * 所属的源节点 ID（反向引用）
     */
    private String sourceNodeId;

    /**
     * 顺序（当有多个出口时）
     */
    private Integer order;

    /**
     * 条件表达式（用于有条件的分支）
     */
    private String conditionExpression;

    /**
     * 条件类型（如 "xpath", "juel", "groovy"）
     */
    private String conditionType;

    /**
     * 扩展属性
     */
    private java.util.Map<String, Object> extensions;

    // ==================== 构造方法 ====================

    public Outgoing() {
    }

    public Outgoing(String sequenceFlowId) {
        this.sequenceFlowId = sequenceFlowId;
    }

    public Outgoing(String sequenceFlowId, String sourceNodeId) {
        this.sequenceFlowId = sequenceFlowId;
        this.sourceNodeId = sourceNodeId;
    }

    // ==================== getters / setters ====================

    public void addExtension(String key, Object value) {
        if (this.extensions == null) {
            this.extensions = new java.util.HashMap<>();
        }
        this.extensions.put(key, value);
    }

    // ==================== 便捷方法 ====================

    /**
     * 是否有条件表达式
     */
    public boolean hasCondition() {
        return conditionExpression != null && !conditionExpression.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Outgoing outgoing = (Outgoing) o;
        return Objects.equals(sequenceFlowId, outgoing.sequenceFlowId) &&
                Objects.equals(sourceNodeId, outgoing.sourceNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceFlowId, sourceNodeId);
    }

    @Override
    public String toString() {
        return "Outgoing{" +
                "sequenceFlowId='" + sequenceFlowId + '\'' +
                ", sourceNodeId='" + sourceNodeId + '\'' +
                ", order=" + order +
                '}';
    }

    // ==================== Builder ====================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sequenceFlowId;
        private String sourceNodeId;
        private Integer order;
        private String conditionExpression;
        private String conditionType;
        private java.util.Map<String, Object> extensions;

        public Builder sequenceFlowId(String sequenceFlowId) {
            this.sequenceFlowId = sequenceFlowId;
            return this;
        }

        public Builder sourceNodeId(String sourceNodeId) {
            this.sourceNodeId = sourceNodeId;
            return this;
        }

        public Builder order(Integer order) {
            this.order = order;
            return this;
        }

        public Builder conditionExpression(String conditionExpression) {
            this.conditionExpression = conditionExpression;
            return this;
        }

        public Builder conditionType(String conditionType) {
            this.conditionType = conditionType;
            return this;
        }

        public Builder extensions(java.util.Map<String, Object> extensions) {
            this.extensions = extensions;
            return this;
        }

        public Builder addExtension(String key, Object value) {
            if (this.extensions == null) {
                this.extensions = new java.util.HashMap<>();
            }
            this.extensions.put(key, value);
            return this;
        }

        public Outgoing build() {
            Outgoing outgoing = new Outgoing();
            outgoing.setSequenceFlowId(sequenceFlowId);
            outgoing.setSourceNodeId(sourceNodeId);
            outgoing.setOrder(order);
            outgoing.setConditionExpression(conditionExpression);
            outgoing.setConditionType(conditionType);
            outgoing.setExtensions(extensions);
            return outgoing;
        }
    }
}