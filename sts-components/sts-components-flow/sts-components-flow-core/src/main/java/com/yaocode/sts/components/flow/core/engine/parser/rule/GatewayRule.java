package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 网关解析规则
 *
 * <p>支持多种网关类型：
 * <ul>
 *   <li>排他网关 (exclusiveGateway)</li>
 *   <li>并行网关 (parallelGateway)</li>
 *   <li>包容网关 (inclusiveGateway)</li>
 *   <li>事件网关 (eventBasedGateway)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class GatewayRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String tagName = element.getTagName();
        String nodeId = getAttribute(element, "id", null);

        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "gateway_" + System.currentTimeMillis();
            context.addError(ParseError.of("网关缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        String localName = tagName.contains(":") ?
                tagName.substring(tagName.indexOf(":") + 1) : tagName;
        // 确定网关类型
        String behaviorType = switch (localName) {
            case "exclusiveGateway" -> "exclusive";
            case "parallelGateway" -> "parallel";
            case "inclusiveGateway" -> "inclusive";
            case "eventBasedGateway" -> "eventBased";
            default -> localName;
        };

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type(tagName)
                .behaviorType(behaviorType)
                .build();

        // 解析默认出口
        String defaultFlow = getAttribute(element, "default", null);
        if (defaultFlow != null && !defaultFlow.isEmpty()) {
            nodeDefinition.addProperty("defaultSequenceFlow", defaultFlow);
        }

        context.addElementId(nodeId);

        log.debug("开始解析网关: id={}, type={}", nodeId, behaviorType);
        return nodeDefinition;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition node = (NodeDefinition) currentObject;

        // 解析条件表达式（网关出口的条件已在 SequenceFlowRule 中处理）

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(node);
        }

        log.debug("网关解析完成: id={}", node.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        // 忽略已处理的标准属性
        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "default".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "GatewayRule";
    }

    @Override
    public int getPriority() {
        return 30;
    }
}
