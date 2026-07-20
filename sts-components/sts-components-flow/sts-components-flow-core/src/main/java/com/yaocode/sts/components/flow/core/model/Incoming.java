package com.yaocode.sts.components.flow.core.model;

import lombok.Data;
import java.util.Objects;

/**
 * 入口引用
 *
 * <p>表示一个节点到序列流的入口引用，对应 BPMN 中的 bpmn:incoming 元素。
 *
 * <p>BPMN 语义：
 * <pre>
 * &lt;bpmn:endEvent id="EndEvent_1"&gt;
 *     &lt;bpmn:incoming&gt;Flow_1yqx94j&lt;/bpmn:incoming&gt;
 * &lt;/bpmn:endEvent&gt;
 * </pre>
 *
 * @author Process Engine Team
 */
@Data
public class Incoming {

    /**
     * 引用的序列流 ID
     */
    private String sequenceFlowId;

    /**
     * 所属的目标节点 ID（反向引用）
     */
    private String targetNodeId;

    /**
     * 扩展属性
     */
    private java.util.Map<String, Object> extensions;

    // ==================== 构造方法 ====================

    public Incoming() {
    }

    public Incoming(String sequenceFlowId) {
        this.sequenceFlowId = sequenceFlowId;
    }

    public Incoming(String sequenceFlowId, String targetNodeId) {
        this.sequenceFlowId = sequenceFlowId;
        this.targetNodeId = targetNodeId;
    }

    // ==================== getters / setters ====================

    public void addExtension(String key, Object value) {
        if (this.extensions == null) {
            this.extensions = new java.util.HashMap<>();
        }
        this.extensions.put(key, value);
    }

    // ==================== 便捷方法 ====================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Incoming incoming = (Incoming) o;
        return Objects.equals(sequenceFlowId, incoming.sequenceFlowId) &&
                Objects.equals(targetNodeId, incoming.targetNodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequenceFlowId, targetNodeId);
    }

    @Override
    public String toString() {
        return "Incoming{" +
                "sequenceFlowId='" + sequenceFlowId + '\'' +
                ", targetNodeId='" + targetNodeId + '\'' +
                '}';
    }

    // ==================== Builder ====================

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sequenceFlowId;
        private String targetNodeId;
        private java.util.Map<String, Object> extensions;

        public Builder sequenceFlowId(String sequenceFlowId) {
            this.sequenceFlowId = sequenceFlowId;
            return this;
        }

        public Builder targetNodeId(String targetNodeId) {
            this.targetNodeId = targetNodeId;
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

        public Incoming build() {
            Incoming incoming = new Incoming();
            incoming.setSequenceFlowId(sequenceFlowId);
            incoming.setTargetNodeId(targetNodeId);
            incoming.setExtensions(extensions);
            return incoming;
        }
    }
}