package com.yaocode.sts.components.flow.core.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 连线定义（序列流）
 *
 * <p>表示流程中两个节点之间的连接，包括：
 * <ul>
 *   <li>普通连线 - 无条件</li>
 *   <li>条件连线 - 包含条件表达式</li>
 *   <li>默认连线 - 网关的默认出口</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class SequenceDefinition {

    // ==================== 基本信息 ====================

    /**
     * 连线唯一标识
     */
    private String sequenceId;

    /**
     * 连线名称
     */
    private String name;

    /**
     * 源节点ID
     */
    private String sourceRef;

    /**
     * 目标节点ID
     */
    private String targetRef;

    // ==================== 条件信息 ====================

    /**
     * 条件表达式（用于条件流）
     * <p>例如：${amount > 1000}
     */
    private String conditionExpression;

    /**
     * 条件类型
     * <p>例如：EL, Groovy, JavaScript
     */
    private String conditionType;

    /**
     * 条件语言
     * <p>例如：el, groovy, javascript
     */
    private String conditionLanguage;

    /**
     * 是否为默认连线
     */
    @Builder.Default
    private boolean isDefault = false;

    // ==================== 扩展属性 ====================

    /**
     * 扩展属性
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 自定义数据
     */
    @Builder.Default
    private Map<String, Object> data = new HashMap<>();

    // ==================== 坐标信息（用于可视化） ====================

    /**
     * BPMN DI 信息 - 路径点列表
     * <p>格式：{x1,y1,x2,y2,...}
     */
    private String waypoints;

    /**
     * 标签位置 - X 坐标
     */
    private Double labelX;

    /**
     * 标签位置 - Y 坐标
     */
    private Double labelY;

    // ==================== 执行信息 ====================

    /**
     * 是否跳过表达式计算（始终执行）
     */
    @Builder.Default
    private boolean skipExpression = false;

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
     * 是否有条件
     */
    public boolean hasCondition() {
        return conditionExpression != null && !conditionExpression.isEmpty();
    }

    /**
     * 是否是普通连线（无条件）
     */
    public boolean isUnconditional() {
        return !hasCondition() && !isDefault;
    }

    /**
     * 获取连线类型的显示名称
     */
    public String getDisplayType() {
        if (isDefault) {
            return "默认连线";
        }
        if (hasCondition()) {
            return "条件连线";
        }
        return "普通连线";
    }

    /**
     * 获取起点和终点的显示名称
     */
    public String getSourceTargetDisplay() {
        return String.format("%s -> %s", sourceRef, targetRef);
    }

    @Override
    public String toString() {
        return String.format("SequenceDefinition{id='%s', source='%s', target='%s', condition=%s, default=%s}",
                sequenceId, sourceRef, targetRef, hasCondition(), isDefault);
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建普通连线
     */
    public static SequenceDefinition create(String id, String source, String target) {
        return SequenceDefinition.builder()
                .sequenceId(id)
                .sourceRef(source)
                .targetRef(target)
                .isDefault(false)
                .build();
    }

    /**
     * 创建条件连线
     */
    public static SequenceDefinition createConditional(String id, String source, String target,
                                                       String condition) {
        return SequenceDefinition.builder()
                .sequenceId(id)
                .sourceRef(source)
                .targetRef(target)
                .conditionExpression(condition)
                .conditionType("EL")
                .conditionLanguage("el")
                .isDefault(false)
                .build();
    }

    /**
     * 创建默认连线
     */
    public static SequenceDefinition createDefault(String id, String source, String target) {
        return SequenceDefinition.builder()
                .sequenceId(id)
                .sourceRef(source)
                .targetRef(target)
                .isDefault(true)
                .build();
    }

    /**
     * 创建 Groovy 条件连线
     */
    public static SequenceDefinition createGroovyConditional(String id, String source, String target,
                                                             String condition) {
        return SequenceDefinition.builder()
                .sequenceId(id)
                .sourceRef(source)
                .targetRef(target)
                .conditionExpression(condition)
                .conditionType("Groovy")
                .conditionLanguage("groovy")
                .isDefault(false)
                .build();
    }
}