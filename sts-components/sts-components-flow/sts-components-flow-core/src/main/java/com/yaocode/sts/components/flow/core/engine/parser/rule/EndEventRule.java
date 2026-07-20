package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 结束事件解析规则
 *
 * <p>支持多种结束事件类型：
 * <ul>
 *   <li>无事件结束 (none)</li>
 *   <li>错误结束 (error)</li>
 *   <li>终止结束 (terminate)</li>
 *   <li>取消结束 (cancel)</li>
 *   <li>信号结束 (signal)</li>
 *   <li>消息结束 (message)</li>
 *   <li>补偿结束 (compensation)</li>
 *   <li>升级结束 (escalation)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class EndEventRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "endEvent_" + System.currentTimeMillis();
            context.addError(ParseError.of("结束事件缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("endEvent")
                .behaviorType("endEvent")
                .build(); // 默认无事件

        // 检查事件定义
        Element errorDef = findChild(element, "errorEventDefinition");
        Element terminateDef = findChild(element, "terminateEventDefinition");
        Element cancelDef = findChild(element, "cancelEventDefinition");
        Element signalDef = findChild(element, "signalEventDefinition");
        Element messageDef = findChild(element, "messageEventDefinition");
        Element compensationDef = findChild(element, "compensationEventDefinition");
        Element escalationDef = findChild(element, "escalationEventDefinition");

        if (errorDef != null) {
            nodeDefinition.setBehaviorType("errorEndEvent");
            String errorRef = errorDef.getAttribute("errorRef");
            if (!errorRef.isEmpty()) {
                nodeDefinition.addProperty("errorRef", errorRef);
            }
        } else if (terminateDef != null) {
            nodeDefinition.setBehaviorType("terminateEndEvent");
        } else if (cancelDef != null) {
            nodeDefinition.setBehaviorType("cancelEndEvent");
        } else if (signalDef != null) {
            nodeDefinition.setBehaviorType("signalEndEvent");
            String signalRef = signalDef.getAttribute("signalRef");
            if (!signalRef.isEmpty()) {
                nodeDefinition.addProperty("signalRef", signalRef);
            }
        } else if (messageDef != null) {
            nodeDefinition.setBehaviorType("messageEndEvent");
            String messageRef = messageDef.getAttribute("messageRef");
            if (!messageRef.isEmpty()) {
                nodeDefinition.addProperty("messageRef", messageRef);
            }
        } else if (compensationDef != null) {
            nodeDefinition.setBehaviorType("compensationEndEvent");
        } else if (escalationDef != null) {
            nodeDefinition.setBehaviorType("escalationEndEvent");
        }

        context.addElementId(nodeId);

        log.debug("开始解析结束事件: id={}, type={}", nodeId, nodeDefinition.getBehaviorType());
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

        log.debug("结束事件解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();
        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "EndEventRule";
    }

    @Override
    public int getPriority() {
        return 25;
    }
}
