package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.List;

/**
 * 服务任务解析规则
 *
 * <p>支持多种服务任务实现方式：
 * <ul>
 *   <li>Java 委托类 (camunda:class)</li>
 *   <li>委托表达式 (camunda:delegateExpression)</li>
 *   <li>表达式 (camunda:expression)</li>
 *   <li>脚本 (camunda:script)</li>
 *   <li>Web Service (camunda:ws)</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class ServiceTaskRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        String nodeId = getAttribute(element, "id", null);
        if (nodeId == null || nodeId.isEmpty()) {
            nodeId = "serviceTask_" + System.currentTimeMillis();
            context.addError(ParseError.of("服务任务缺少 id 属性，使用自动生成ID: " + nodeId, element));
        }

        NodeDefinition nodeDefinition = NodeDefinition.builder()
                .nodeId(nodeId)
                .name(getAttribute(element, "name", nodeId))
                .type("serviceTask")
                .behaviorType("javaDelegate")
                .build(); // 默认

        // 解析 Camunda 扩展属性 - 实现方式
        String className = getAttribute(element, "camunda:class", null);
        String delegateExpression = getAttribute(element, "camunda:delegateExpression", null);
        String expression = getAttribute(element, "camunda:expression", null);
        String script = getAttribute(element, "camunda:script", null);
        String ws = getAttribute(element, "camunda:ws", null);
        String type = getAttribute(element, "camunda:type", null);

        if (className != null && !className.isEmpty()) {
            nodeDefinition.setBehaviorType("javaDelegate");
            nodeDefinition.addProperty("delegateClass", className);
        } else if (delegateExpression != null && !delegateExpression.isEmpty()) {
            nodeDefinition.setBehaviorType("delegateExpression");
            nodeDefinition.addProperty("delegateExpression", delegateExpression);
        } else if (expression != null && !expression.isEmpty()) {
            nodeDefinition.setBehaviorType("expression");
            nodeDefinition.addProperty("expression", expression);
        } else if (script != null && !script.isEmpty()) {
            nodeDefinition.setBehaviorType("script");
            nodeDefinition.addProperty("script", script);
        } else if (ws != null && !ws.isEmpty()) {
            nodeDefinition.setBehaviorType("webService");
            nodeDefinition.addProperty("ws", ws);
        } else if (type != null && !type.isEmpty()) {
            nodeDefinition.setBehaviorType(type);
            nodeDefinition.addProperty("type", type);
        }

        // 解析其他 Camunda 属性
        String resultVariable = getAttribute(element, "camunda:resultVariable", null);
        if (resultVariable != null && !resultVariable.isEmpty()) {
            nodeDefinition.addProperty("resultVariable", resultVariable);
        }

        String variableMappingClass = getAttribute(element, "camunda:variableMappingClass", null);
        if (variableMappingClass != null && !variableMappingClass.isEmpty()) {
            nodeDefinition.addProperty("variableMappingClass", variableMappingClass);
        }

        String variableMappingDelegateExpression = getAttribute(element, "camunda:variableMappingDelegateExpression", null);
        if (variableMappingDelegateExpression != null && !variableMappingDelegateExpression.isEmpty()) {
            nodeDefinition.addProperty("variableMappingDelegateExpression", variableMappingDelegateExpression);
        }

        context.addElementId(nodeId);

        log.debug("开始解析服务任务: id={}, type={}", nodeId, nodeDefinition.getBehaviorType());
        return nodeDefinition;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        NodeDefinition node = (NodeDefinition) currentObject;

        // 解析子元素
        parseInputOutput(element, node);
        parseDocumentation(element, node);

        // 添加到父流程
        if (parent instanceof ProcessDefinition process) {
            if (process.getNodes() == null) {
                process.setNodes(new java.util.ArrayList<>());
            }
            process.getNodes().add(node);
        }

        log.debug("服务任务解析完成: id={}", node.getNodeId());
    }

    private void parseInputOutput(Element element, NodeDefinition node) {
        // 查找 inputOutput 子元素（由 InputOutputRule 处理）
        Element ioMapping = findChild(element, "inputOutput");
        if (ioMapping != null) {
            // 解析输入参数
            List<Element> inputs = findChildren(ioMapping, "inputParameter");
            for (Element input : inputs) {
                String name = input.getAttribute("name");
                String value = getTextContent(input);
                if (!name.isEmpty()) {
                    node.addProperty("input_" + name, value);
                }
            }

            // 解析输出参数
            List<Element> outputs = findChildren(ioMapping, "outputParameter");
            for (Element output : outputs) {
                String name = output.getAttribute("name");
                String value = getTextContent(output);
                if (!name.isEmpty()) {
                    node.addProperty("output_" + name, value);
                }
            }
        }
    }

    private void parseDocumentation(Element element, NodeDefinition node) {
        Element doc = findChild(element, "documentation");
        if (doc != null) {
            node.setDocumentation(getTextContent(doc));
        }
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        NodeDefinition node = (NodeDefinition) context.getCurrentObject();

        // 忽略已处理的标准属性
        if ("id".equals(attributeName) || "name".equals(attributeName) ||
                "camunda:class".equals(attributeName) ||
                "camunda:delegateExpression".equals(attributeName) ||
                "camunda:expression".equals(attributeName) ||
                "camunda:script".equals(attributeName) ||
                "camunda:ws".equals(attributeName) ||
                "camunda:type".equals(attributeName) ||
                "camunda:resultVariable".equals(attributeName)) {
            return;
        }

        node.addProperty(attributeName, attributeValue);
    }

    @Override
    public String getRuleName() {
        return "ServiceTaskRule";
    }

    @Override
    public int getPriority() {
        return 30;
    }
}
