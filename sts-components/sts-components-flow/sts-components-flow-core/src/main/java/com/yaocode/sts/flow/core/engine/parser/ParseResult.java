package com.yaocode.sts.flow.core.engine.parser;

import com.yaocode.sts.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.flow.core.enums.NodeTypeEnums;
import com.yaocode.sts.flow.core.model.NodeDefinition;
import com.yaocode.sts.flow.core.model.ProcessDefinition;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * 解析结果
 *
 * <p>包含从流程定义文件中提取的所有信息
 *
 * @author Process Engine Team
 */
@Slf4j
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
     * 生成解析报告字符串
     *
     * @return 格式化的解析报告
     */
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Parse Report ===\n");
        sb.append("Success: ").append(success).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Format: ").append(format).append("\n");
        sb.append("Process ID: ").append(processDefinition != null ? processDefinition.getProcessId() : "N/A").append("\n");
        sb.append("Process Name: ").append(processDefinition != null ? processDefinition.getProcessName() : "N/A").append("\n");
        sb.append("Nodes: ").append(processDefinition != null && processDefinition.getNodes() != null ?
                processDefinition.getNodes().size() : 0).append("\n");
        sb.append("Sequences: ").append(processDefinition != null && processDefinition.getSequences() != null ?
                processDefinition.getSequences().size() : 0).append("\n");
        sb.append("Errors: ").append(errors != null ? errors.size() : 0).append("\n");
        sb.append("Warnings: ").append(warnings != null ? warnings.size() : 0).append("\n");
        sb.append("Parse Time: ").append(parseTime).append("ms\n");

        if (errors != null && !errors.isEmpty()) {
            sb.append("\n--- Errors ---\n");
            errors.forEach(e -> sb.append("[").append(e.getSeverity()).append("] ").append(e.getMessage()).append("\n"));
        }

        if (warnings != null && !warnings.isEmpty()) {
            sb.append("\n--- Warnings ---\n");
            warnings.forEach(w -> sb.append(w.getMessage()).append("\n"));
        }
        return sb.toString();
    }

    /**
     * 通过日志输出解析报告
     */
    public void printReport() {
        log.info("Parse Report: {}", generateReport());
    }
}