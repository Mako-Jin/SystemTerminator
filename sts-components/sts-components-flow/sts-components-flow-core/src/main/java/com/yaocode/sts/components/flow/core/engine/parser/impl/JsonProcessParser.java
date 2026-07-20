package com.yaocode.sts.components.flow.core.engine.parser.impl;

import com.yaocode.sts.common.tools.JSONUtils;
import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * JSON 流程解析器
 * 支持 3D 流程设计器导出的 JSON 格式
 */
@Slf4j
public class JsonProcessParser extends AbstractProcessParser {

    private static final List<String> SUPPORTED_FORMATS = List.of(".json");
    private static final String FORMAT_NAME = "JSON";

    @Override
    protected Object doParse(byte[] content, String resourceName, ParseContext context) throws ParseException {
        try {
            Map<String, Object> jsonData = JSONUtils.parseMap(content);
            return parseJson(jsonData, context);
        } catch (Exception e) {
            throw new ParseException("JSON 解析失败: " + resourceName, e);
        }
    }

    @Override
    protected Object doParse(InputStream inputStream, String resourceName, ParseContext context) throws ParseException {
        try {
            // TODO 使用 Map<String, Object> 进行类型转换，容易出现运行时异常。
            Map<String, Object> jsonData = JSONUtils.parseMap(inputStream);
            return parseJson(jsonData, context);
        } catch (Exception e) {
            throw new ParseException("JSON 解析失败: " + resourceName, e);
        }
    }

    /**
     * 解析 JSON 数据
     */
    private Object parseJson(Map<String, Object> jsonData, ParseContext context) {
        // 构建流程定义
        ProcessDefinition.ProcessDefinitionBuilder builder = ProcessDefinition.builder();

        // 提取流程信息
        String processId = getString(jsonData, "processId", "id", "key");
        if (processId != null) {
            builder.processId(processId);
            context.addElementId(processId);
        }

        String name = getString(jsonData, "name", "processName");
        builder.processName(name);

        String description = getString(jsonData, "description", "desc");
        builder.description(description);

        ProcessDefinition processDefinition = builder.build();

        // 解析节点
        List<Map<String, Object>> nodes = getList(jsonData, "nodes", "elements", "tasks");
        if (nodes != null) {
            nodes.forEach(nodeData -> {
                NodeDefinition node = parseNode(nodeData);
                processDefinition.addNode(node);
                context.addElementId(node.getNodeId());
            });
        }

        // 解析连线
        List<Map<String, Object>> flows = getList(jsonData, "flows", "connections", "sequenceFlows");
        if (flows != null) {
            flows.forEach(flowData -> {
                SequenceDefinition sequence = parseSequence(flowData);
                processDefinition.addSequence(sequence);
                context.addElementId(sequence.getSequenceId());
            });
        }

        return processDefinition;
    }



    /**
     * 解析 JSON 节点
     */
    private NodeDefinition parseNode(Map<String, Object> data) {
        NodeDefinition.NodeDefinitionBuilder builder = NodeDefinition.builder();

        String id = getString(data, "id", "nodeId");
        builder.nodeId(id);

        String name = getString(data, "name", "label");
        builder.name(name);

        String type = getString(data, "type", "nodeType");
        builder.type(type);

        String behaviorType = getString(data, "behaviorType", "behavior");
        builder.behaviorType(behaviorType);

        // 解析配置
        Map<String, Object> config = getMap(data, "config", "properties", "attributes");
        if (config != null) {
            builder.properties(config);
        }

        return builder.build();
    }

    /**
     * 解析 JSON 连线
     */
    private SequenceDefinition parseSequence(Map<String, Object> data) {
        SequenceDefinition.SequenceDefinitionBuilder builder = SequenceDefinition.builder();

        String id = getString(data, "id", "flowId");
        builder.sequenceId(id);

        String source = getString(data, "sourceRef", "source", "from");
        builder.sourceRef(source);

        String target = getString(data, "targetRef", "target", "to");
        builder.targetRef(target);

        String condition = getString(data, "conditionExpression", "condition");
        builder.conditionExpression(condition);

        return builder.build();
    }

    @Override
    protected ProcessDefinition buildProcessDefinition(Object parsedObject, ParseContext context) {
        if (parsedObject instanceof ProcessDefinition) {
            return (ProcessDefinition) parsedObject;
        }
        return ProcessDefinition.builder().build();
    }

    @Override
    public boolean supports(String resourceName) {
        if (resourceName == null) {
            return false;
        }
        String lower = resourceName.toLowerCase();
        return SUPPORTED_FORMATS.stream().anyMatch(lower::endsWith);
    }

    @Override
    public List<String> getSupportedFormats() {
        return SUPPORTED_FORMATS;
    }

    @Override
    public String getParserName() {
        return "JSON Flow Parser";
    }

    @Override
    protected String getFormat() {
        return FORMAT_NAME;
    }

    // 辅助方法
    private String getString(Map<String, Object> data, String... keys) {
        for (String key : keys) {
            Object value = data.get(key);
            if (value instanceof String) {
                return (String) value;
            }
        }
        return null;
    }

    private List<Map<String, Object>> getList(Map<String, Object> data, String... keys) {
        for (String key : keys) {
            Object value = data.get(key);
            if (value instanceof List) {
                return (List<Map<String, Object>>) value;
            }
        }
        return null;
    }

    private Map<String, Object> getMap(Map<String, Object> data, String... keys) {
        for (String key : keys) {
            Object value = data.get(key);
            if (value instanceof Map) {
                return (Map<String, Object>) value;
            }
        }
        return null;
    }
}
