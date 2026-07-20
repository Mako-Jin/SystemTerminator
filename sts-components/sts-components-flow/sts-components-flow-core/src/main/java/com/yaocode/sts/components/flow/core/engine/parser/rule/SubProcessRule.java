package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 子流程解析规则
 *
 * <p>支持两种子流程：
 * <ul>
 *   <li>嵌入式子流程 (embeddedSubProcess) - 默认</li>
 *   <li>事件子流程 (eventSubProcess) - triggeredByEvent="true"</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class SubProcessRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "subProcess_" + System.currentTimeMillis();
            context.addError(ParseError.of("子流程缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        String behaviorType = "embeddedSubProcess";
        String triggeredByEvent = getAttribute(element, "triggeredByEvent", "false");
        if ("true".equalsIgnoreCase(triggeredByEvent)) {
            behaviorType = "eventSubProcess";
        }

        NodeDefinition.NodeDefinitionBuilder builder = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("subProcess")
                .behaviorType(behaviorType)
                .scope(true);

        NodeDefinition subProcess = builder.build();

        // 注册ID
        context.addElementId(nodeId);

        // 压入父栈（子流程内的节点会添加到这个子流程下）
        context.pushParent(subProcess);

        log.debug("开始解析子流程: id={}, type={}", nodeId, behaviorType);
        return subProcess;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition subProcess = (NodeDefinition) currentObject;

        // 弹出父栈
        context.popParent();

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(subProcess);
        } else if (parent instanceof NodeDefinition parentNode) {
            // 子流程嵌套
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new java.util.ArrayList<>());
            }
            parentNode.getChildren().add(subProcess);
        }

        log.debug("子流程解析完成: id={}", subProcess.getNodeId());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition subProcess = (NodeDefinition) context.getCurrentObject();

        // 忽略已处理的标准属性
        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "triggeredByEvent".equals(attributeName)) {
            return;
        }

        subProcess.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "SubProcessRule";
    }

    @Override
    public int getPriority() {
        return 20;
    }
}
