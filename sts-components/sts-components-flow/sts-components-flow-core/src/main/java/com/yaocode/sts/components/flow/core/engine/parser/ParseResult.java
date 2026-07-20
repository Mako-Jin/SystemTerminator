package com.yaocode.sts.components.flow.core.engine.parser;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.components.flow.core.enums.NodeTypeEnums;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.List;
import java.util.Map;

/**
 * 解析结果
 *
 * <p>包含从流程定义文件中提取的所有信息
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ParseResult {

    /**
     * 是否解析成功
     */
    private boolean success;

    /**
     * 解析状态
     */
    private ParseStatusEnums status;

    /**
     * 流程定义对象
     */
    private ProcessDefinition processDefinition;

    /**
     * 原始解析结果
     */
    private Object rawResult;

    /**
     * 解析耗时（毫秒）
     */
    private long parseTime;

    /**
     * 文件格式
     */
    private String format;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 解析错误列表
     */
    @Singular
    private List<ParseError> errors;

    /**
     * 解析警告列表
     */
    @Singular
    private List<ParseWarning> warnings;

    /**
     * 扩展信息
     */
    private Map<String, Object> extensions;

    /**
     * 是否有错误
     */
    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }

    /**
     * 是否有致命错误
     */
    public boolean hasFatalErrors() {
        if (errors == null) {
            return false;
        }
        return errors.stream().anyMatch(e -> e.getSeverity() == ErrorSeverityEnums.FATAL);
    }

    /**
     * 获取错误消息列表
     */
    public List<String> getErrorMessages() {
        if (errors == null) {
            return List.of();
        }
        return errors.stream()
                .map(e -> String.format("[%s] %s", e.getSeverity(), e.getMessage()))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 获取开始节点
     */
    public NodeDefinition getStartNode() {
        if (processDefinition == null) {
            return null;
        }
        return processDefinition.getStartNode();
    }

    /**
     * 获取结束节点列表
     */
    public List<NodeDefinition> getEndNodes() {
        if (processDefinition == null) {
            return List.of();
        }
        return processDefinition.getEndNodes();
    }

    /**
     * 获取指定类型的节点列表
     */
    public List<NodeDefinition> getNodesByType(String type) {
        if (processDefinition == null) {
            return List.of();
        }
        return processDefinition.getNodesByType(NodeTypeEnums.fromType(type));
    }

    /**
     * 获取用户任务节点列表
     */
    public List<NodeDefinition> getUserTasks() {
        return getNodesByType("userTask");
    }

    /**
     * 获取服务任务节点列表
     */
    public List<NodeDefinition> getServiceTasks() {
        return getNodesByType("serviceTask");
    }

    /**
     * 打印解析报告
     */
    public void printReport() {
        System.out.println("=== Parse Report ===");
        System.out.println("Success: " + success);
        System.out.println("Status: " + status);
        System.out.println("Format: " + format);
        System.out.println("Process ID: " + (processDefinition != null ? processDefinition.getProcessId() : "N/A"));
        System.out.println("Process Name: " + (processDefinition != null ? processDefinition.getProcessName() : "N/A"));
        System.out.println("Nodes: " + (processDefinition != null && processDefinition.getNodes() != null ?
                processDefinition.getNodes().size() : 0));
        System.out.println("Sequences: " + (processDefinition != null && processDefinition.getSequences() != null ?
                processDefinition.getSequences().size() : 0));
        System.out.println("Errors: " + (errors != null ? errors.size() : 0));
        System.out.println("Warnings: " + (warnings != null ? warnings.size() : 0));
        System.out.println("Parse Time: " + parseTime + "ms");

        if (errors != null && !errors.isEmpty()) {
            System.out.println("\n--- Errors ---");
            errors.forEach(e -> System.out.printf("[%s] %s%n", e.getSeverity(), e.getMessage()));
        }

        if (warnings != null && !warnings.isEmpty()) {
            System.out.println("\n--- Warnings ---");
            warnings.forEach(w -> System.out.println(w.getMessage()));
        }
    }
}