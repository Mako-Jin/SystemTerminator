package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 调用活动解析规则
 *
 * <p>用于调用另一个流程定义
 *
 * @author Process Engine Team
 */
@Slf4j
public class CallActivityRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "callActivity_" + System.currentTimeMillis();
            context.addError(ParseError.of("调用活动缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("callActivity")
                .behaviorType("callActivity")
                .build();

        // 解析调用的流程定义
        String calledElement = getAttribute(element, "calledElement", null);
        if (calledElement != null && !calledElement.isEmpty()) {
            nodeDefinition.addProperty("calledElement", calledElement);
        }

        // Camunda 扩展
        String calledElementType = getAttribute(element, "camunda:calledElementType", null);
        if (calledElementType != null && !calledElementType.isEmpty()) {
            nodeDefinition.addProperty("calledElementType", calledElementType);
        }

        String variableMappingClass = getAttribute(element, "camunda:variableMappingClass", null);
        if (variableMappingClass != null && !variableMappingClass.isEmpty()) {
            nodeDefinition.addProperty("variableMappingClass", variableMappingClass);
        }

        context.addElementId(nodeId);

        log.debug("开始解析调用活动: id={}, calledElement={}", nodeId, calledElement);
        return nodeDefinition;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition node = (NodeDefinition) currentObject;

        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(node);
        }

        log.debug("调用活动解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "calledElement".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "CallActivityRule";
    }

    @Override
    public int getPriority() {
        return 30;
    }
}
