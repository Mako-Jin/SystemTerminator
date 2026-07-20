package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 中间抛出事件解析规则
 *
 * <p>支持多种中间抛出事件：
 * <ul>
 *   <li>消息抛出 (message)</li>
 *   <li>信号抛出 (signal)</li>
 *   <li>升级抛出 (escalation)</li>
 *   <li>链接抛出 (link)</li>
 *   <li>补偿抛出 (compensation)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class IntermediateThrowEventRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "intermediateThrow_" + System.currentTimeMillis();
            context.addError(ParseError.of("中间抛出事件缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("intermediateThrowEvent")
                .behaviorType("throw")
                .build();

        // 检查事件类型
        Element messageDef = findChild(element, "messageEventDefinition");
        Element signalDef = findChild(element, "signalEventDefinition");
        Element escalationDef = findChild(element, "escalationEventDefinition");
        Element linkDef = findChild(element, "linkEventDefinition");
        Element compensationDef = findChild(element, "compensationEventDefinition");

        if (messageDef != null) {
            nodeDefinition.addProperty("eventType", "message");
            String messageRef = messageDef.getAttribute("messageRef");
            if (!messageRef.isEmpty()) {
                nodeDefinition.addProperty("messageRef", messageRef);
            }
        } else if (signalDef != null) {
            nodeDefinition.addProperty("eventType", "signal");
            String signalRef = signalDef.getAttribute("signalRef");
            if (!signalRef.isEmpty()) {
                nodeDefinition.addProperty("signalRef", signalRef);
            }
        } else if (escalationDef != null) {
            nodeDefinition.addProperty("eventType", "escalation");
            String escalationRef = escalationDef.getAttribute("escalationRef");
            if (!escalationRef.isEmpty()) {
                nodeDefinition.addProperty("escalationRef", escalationRef);
            }
        } else if (linkDef != null) {
            nodeDefinition.addProperty("eventType", "link");
            String linkName = linkDef.getAttribute("name");
            if (!linkName.isEmpty()) {
                nodeDefinition.addProperty("linkName", linkName);
            }
        } else if (compensationDef != null) {
            nodeDefinition.addProperty("eventType", "compensation");
        } else {
            nodeDefinition.addProperty("eventType", "none");
        }

        context.addElementId(nodeId);

        log.debug("开始解析中间抛出事件: id={}", nodeId);
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

        log.debug("中间抛出事件解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        if ("id".equals(attributeName) || "name".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "IntermediateThrowEventRule";
    }

    @Override
    public int getPriority() {
        return 25;
    }
}
