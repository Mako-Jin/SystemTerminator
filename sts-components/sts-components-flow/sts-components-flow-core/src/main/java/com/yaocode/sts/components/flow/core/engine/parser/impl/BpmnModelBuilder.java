package com.yaocode.sts.components.flow.core.engine.parser.impl;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import com.yaocode.sts.components.flow.core.model.SequenceDefinition;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * BPMN 模型构建器
 * 将解析结果转换为流程定义
 */
@Slf4j
public class BpmnModelBuilder {

    public ProcessDefinition build(Object parsedObject, ParseContext context) {
        if (parsedObject instanceof ProcessDefinition) {
            return (ProcessDefinition) parsedObject;
        }

        // 从上下文构建
        ProcessDefinition.ProcessDefinitionBuilder builder = ProcessDefinition.builder();

        // 收集所有节点和连线
        List<NodeDefinition> allNodes = new ArrayList<>();
        List<SequenceDefinition> allSequences = new ArrayList<>();

        // 从上下文获取定义
        for (Object obj : context.getDefinitions().values()) {
            if (obj instanceof ProcessDefinition pd) {
                if (pd.getNodes() != null) {
                    allNodes.addAll(pd.getNodes());
                }
                if (pd.getSequences() != null) {
                    allSequences.addAll(pd.getSequences());
                }
                builder.processId(pd.getProcessId())
                        .processName(pd.getProcessName())
                        .description(pd.getDescription())
                        .category(pd.getCategory())
                        .startable(pd.isStartable());
            }
        }

        builder.nodes(allNodes).sequences(allSequences);

        // 从上下文获取扩展属性
        builder.extensions(context.getExtensions());

        return builder.build();
    }
}
