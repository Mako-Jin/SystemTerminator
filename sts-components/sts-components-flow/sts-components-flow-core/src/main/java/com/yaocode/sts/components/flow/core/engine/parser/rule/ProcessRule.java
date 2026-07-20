package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 流程解析规则
 *
 * <p>解析 BPMN 的 process 元素
 *
 * @author Process Engine Team
 */
@Slf4j
public class ProcessRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        // 创建流程定义对象
        ProcessDefinition.ProcessDefinitionBuilder builder = ProcessDefinition.builder();

        String processId = getAttribute(element, "id", null);
        if (processId == null || processId.isEmpty()) {
            context.addError(ParseError.of("流程定义缺少 id 属性", element));
            processId = "process_" + System.currentTimeMillis();
        }

        builder.processId(processId)
                .processName(getAttribute(element, "name", processId))
                .description(getAttribute(element, "description", ""))
                .category(getAttribute(element, "category", ""))
                .versionTag(getAttribute(element, "versionTag", "1.0"))
                .startable(getBooleanAttribute(element, "isStartable", true))
                .executable(getBooleanAttribute(element, "isExecutable", true));

        ProcessDefinition process = builder.build();

        // 注册ID
        context.addElementId(processId);
        context.addDefinition(processId, process);

        log.debug("开始解析流程: id={}, name={}", processId, process.getProcessName());
        return process;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        ProcessDefinition process = (ProcessDefinition) currentObject;

        // 验证必须有开始事件
        boolean hasStartEvent = process.getNodes() != null &&
                process.getNodes().stream().anyMatch(n -> "startEvent".equals(n.getType()));

        if (!hasStartEvent) {
            context.addError(ParseError.of("流程必须包含至少一个开始事件 (startEvent)", element));
        }

        // 验证必须有结束事件（警告）
        boolean hasEndEvent = process.getNodes() != null &&
                process.getNodes().stream().anyMatch(n -> "endEvent".equals(n.getType()));

        if (!hasEndEvent) {
            context.addWarning(ParseWarning.of("流程建议包含至少一个结束事件 (endEvent)", element));
        }

        log.debug("流程解析完成: id={}, nodes={}, sequences={}",
                process.getProcessId(),
                process.getNodes() != null ? process.getNodes().size() : 0,
                process.getSequences() != null ? process.getSequences().size() : 0);
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        // 处理扩展属性
        if (attributeName.startsWith("custom:")) {
            ProcessDefinition process = (ProcessDefinition) context.getCurrentObject();
            if (process.getExtensions() == null) {
                process.setExtensions(new java.util.HashMap<>());
            }
            process.getExtensions().put(attributeName, attributeValue);
        }
    }

    @Override
    public String getRuleName() {
        return "ProcessRule";
    }

    @Override
    public int getPriority() {
        return 10;
    }
}
