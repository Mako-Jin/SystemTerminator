package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 中间捕获事件解析规则
 *
 * <p>支持多种中间捕获事件：
 * <ul>
 *   <li>消息捕获 (message)</li>
 *   <li>定时器捕获 (timer)</li>
 *   <li>信号捕获 (signal)</li>
 *   <li>错误捕获 (error)</li>
 *   <li>条件捕获 (conditional)</li>
 *   <li>链接捕获 (link)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class IntermediateCatchEventRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "intermediateCatch_" + System.currentTimeMillis();
            context.addError(ParseError.of("中间捕获事件缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("intermediateCatchEvent")
                .behaviorType("catch")
                .build();

        // 检查事件类型
        Element messageDef = findChild(element, "messageEventDefinition");
        Element timerDef = findChild(element, "timerEventDefinition");
        Element signalDef = findChild(element, "signalEventDefinition");
        Element errorDef = findChild(element, "errorEventDefinition");
        Element conditionalDef = findChild(element, "conditionalEventDefinition");
        Element linkDef = findChild(element, "linkEventDefinition");

        if (messageDef != null) {
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
        } else if (signalDef != null) {
            nodeDefinition.addProperty("eventType", "signal");
            String signalRef = signalDef.getAttribute("signalRef");
            if (!signalRef.isEmpty()) {
                nodeDefinition.addProperty("signalRef", signalRef);
            }
        } else if (errorDef != null) {
            nodeDefinition.addProperty("eventType", "error");
            String errorRef = errorDef.getAttribute("errorRef");
            if (!errorRef.isEmpty()) {
                nodeDefinition.addProperty("errorRef", errorRef);
            }
        } else if (conditionalDef != null) {
            nodeDefinition.addProperty("eventType", "conditional");
            Element condition = findChild(conditionalDef, "condition");
            if (condition != null) {
                nodeDefinition.addProperty("condition", condition.getTextContent().trim());
            }
        } else if (linkDef != null) {
            nodeDefinition.addProperty("eventType", "link");
            String linkName = linkDef.getAttribute("name");
            if (!linkName.isEmpty()) {
                nodeDefinition.addProperty("linkName", linkName);
            }
        } else {
            nodeDefinition.addProperty("eventType", "none");
        }

        context.addElementId(nodeId);

        log.debug("开始解析中间捕获事件: id={}", nodeId);
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

        log.debug("中间捕获事件解析完成: id={}", node.getNodeId());
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
        return "IntermediateCatchEventRule";
    }

    @Override
    public int getPriority() {
        return 25;
    }
}
