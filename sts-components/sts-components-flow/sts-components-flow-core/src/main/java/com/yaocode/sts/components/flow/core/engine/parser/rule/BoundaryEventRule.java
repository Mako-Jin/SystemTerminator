package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 边界事件解析规则
 *
 * <p>支持多种边界事件：
 * <ul>
 *   <li>错误边界 (error)</li>
 *   <li>信号边界 (signal)</li>
 *   <li>消息边界 (message)</li>
 *   <li>定时器边界 (timer)</li>
 *   <li>升级边界 (escalation)</li>
 *   <li>补偿边界 (compensation)</li>
 *   <li>条件边界 (conditional)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class BoundaryEventRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "boundaryEvent_" + System.currentTimeMillis();
            context.addError(ParseError.of("边界事件缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("boundaryEvent")
                .behaviorType("boundary")
                .build();

        // 检查是否取消活动
        String cancelActivity = getAttribute(element, "cancelActivity", "true");
        nodeDefinition.addProperty("cancelActivity", Boolean.parseBoolean(cancelActivity));

        // 附加到的节点
        String attachedToRef = getAttribute(element, "attachedToRef", null);
        if (attachedToRef != null && !attachedToRef.isEmpty()) {
            nodeDefinition.addProperty("attachedToRef", attachedToRef);
        }

        // 检查事件类型
        Element errorDef = findChild(element, "errorEventDefinition");
        Element signalDef = findChild(element, "signalEventDefinition");
        Element messageDef = findChild(element, "messageEventDefinition");
        Element timerDef = findChild(element, "timerEventDefinition");
        Element escalationDef = findChild(element, "escalationEventDefinition");
        Element compensationDef = findChild(element, "compensationEventDefinition");
        Element conditionalDef = findChild(element, "conditionalEventDefinition");

        if (errorDef != null) {
            nodeDefinition.addProperty("eventType", "error");
            String errorRef = errorDef.getAttribute("errorRef");
            if (!errorRef.isEmpty()) {
                nodeDefinition.addProperty("errorRef", errorRef);
            }
        } else if (signalDef != null) {
            nodeDefinition.addProperty("eventType", "signal");
            String signalRef = signalDef.getAttribute("signalRef");
            if (!signalRef.isEmpty()) {
                nodeDefinition.addProperty("signalRef", signalRef);
            }
        } else if (messageDef != null) {
            nodeDefinition.addProperty("eventType", "message");
            String messageRef = messageDef.getAttribute("messageRef");
            if (!messageRef.isEmpty()) {
                nodeDefinition.addProperty("messageRef", messageRef);
            }
        } else if (timerDef != null) {
            nodeDefinition.addProperty("eventType", "timer");
            Element timeDef = findChild(timerDef, "timeCycle");
            if (timeDef == null) {
                timeDef = findChild(timerDef, "timeDuration");
            }
            if (timeDef == null) {
                timeDef = findChild(timerDef, "timeDate");
            }
            if (timeDef != null) {
                nodeDefinition.addProperty("timerExpression", timeDef.getTextContent().trim());
            }
        } else if (escalationDef != null) {
            nodeDefinition.addProperty("eventType", "escalation");
            String escalationRef = escalationDef.getAttribute("escalationRef");
            if (!escalationRef.isEmpty()) {
                nodeDefinition.addProperty("escalationRef", escalationRef);
            }
        } else if (compensationDef != null) {
            nodeDefinition.addProperty("eventType", "compensation");
        } else if (conditionalDef != null) {
            nodeDefinition.addProperty("eventType", "conditional");
            Element condition = findChild(conditionalDef, "condition");
            if (condition != null) {
                nodeDefinition.addProperty("condition", condition.getTextContent().trim());
            }
        } else {
            nodeDefinition.addProperty("eventType", "none");
        }

        context.addElementId(nodeId);

        log.debug("开始解析边界事件: id={}, attachedTo={}", nodeId, attachedToRef);
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

        log.debug("边界事件解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "attachedToRef".equals(attributeName) ||
                "cancelActivity".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "BoundaryEventRule";
    }

    @Override
    public int getPriority() {
        return 25;
    }
}
