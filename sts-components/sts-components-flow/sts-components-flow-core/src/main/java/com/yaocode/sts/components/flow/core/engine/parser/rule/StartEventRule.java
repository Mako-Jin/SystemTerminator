package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 开始事件解析规则
 *
 * <p>支持多种开始事件类型：
 * <ul>
 *   <li>无事件开始 (none)</li>
 *   <li>定时器开始 (timer)</li>
 *   <li>消息开始 (message)</li>
 *   <li>信号开始 (signal)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class StartEventRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "startEvent_" + System.currentTimeMillis();
            context.addError(ParseError.of("开始事件缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("startEvent")
                .behaviorType("startEvent")
                .build(); // 默认无事件

        // 检查是否有事件定义
        Element timerDef = findChild(element, "timerEventDefinition");
        Element messageDef = findChild(element, "messageEventDefinition");
        Element signalDef = findChild(element, "signalEventDefinition");
        Element errorDef = findChild(element, "errorEventDefinition");
        Element escalationDef = findChild(element, "escalationEventDefinition");
        Element compensationDef = findChild(element, "compensationEventDefinition");
        Element conditionalDef = findChild(element, "conditionalEventDefinition");
        Element linkDef = findChild(element, "linkEventDefinition");

        if (timerDef != null) {
            nodeDefinition.setBehaviorType("timerStartEvent");
            // 解析定时器表达式
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
        } else if (messageDef != null) {
            nodeDefinition.setBehaviorType("messageStartEvent");
            String messageRef = messageDef.getAttribute("messageRef");
            if (!messageRef.isEmpty()) {
                nodeDefinition.addProperty("messageRef", messageRef);
            }
        } else if (signalDef != null) {
            nodeDefinition.setBehaviorType("signalStartEvent");
            String signalRef = signalDef.getAttribute("signalRef");
            if (!signalRef.isEmpty()) {
                nodeDefinition.addProperty("signalRef", signalRef);
            }
        } else if (errorDef != null) {
            nodeDefinition.setBehaviorType("errorStartEvent");
            String errorRef = errorDef.getAttribute("errorRef");
            if (!errorRef.isEmpty()) {
                nodeDefinition.addProperty("errorRef", errorRef);
            }
        } else if (escalationDef != null) {
            nodeDefinition.setBehaviorType("escalationStartEvent");
        } else if (compensationDef != null) {
            nodeDefinition.setBehaviorType("compensationStartEvent");
        } else if (conditionalDef != null) {
            nodeDefinition.setBehaviorType("conditionalStartEvent");
            // 解析条件表达式
            Element condition = findChild(conditionalDef, "condition");
            if (condition != null) {
                nodeDefinition.addProperty("condition", condition.getTextContent().trim());
            }
        } else if (linkDef != null) {
            nodeDefinition.setBehaviorType("linkStartEvent");
        }
        // 注册ID
        context.addElementId(nodeId);

        log.debug("开始解析开始事件: id={}, type={}", nodeId, nodeDefinition.getBehaviorType());
        return nodeDefinition;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition node = (NodeDefinition) currentObject;

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(node);
        }

        log.debug("开始事件解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();
        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "StartEventRule";
    }

    @Override
    public int getPriority() {
        return 20;
    }
}
