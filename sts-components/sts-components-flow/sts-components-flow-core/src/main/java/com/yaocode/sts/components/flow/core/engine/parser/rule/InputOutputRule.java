package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 输入输出映射规则
 *
 * <p>解析 Camunda 的 inputOutput 扩展元素
 *
 * @author Process Engine Team
 */
@Slf4j
public class InputOutputRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        // 创建输入输出映射对象
        Map<String, Object> ioMapping = new HashMap<>();
        ioMapping.put("inputs", new HashMap<String, String>());
        ioMapping.put("outputs", new HashMap<String, String>());

        // 关联到父节点
        Object parent = context.getCurrentParent();
        if (parent instanceof NodeDefinition) {
            ioMapping.put("__parent", ((NodeDefinition) parent).getNodeId());
        }

        log.debug("开始解析输入输出映射");
        return ioMapping;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        Map<String, Object> ioMapping = (Map<String, Object>) currentObject;

        // 解析输入参数
        List<Element> inputs = findChildren(element, "inputParameter");
        Map<String, String> inputMap = (Map<String, String>) ioMapping.get("inputs");
        for (Element input : inputs) {
            String name = input.getAttribute("name");
            String value = getTextContent(input);
            if (!name.isEmpty()) {
                inputMap.put(name, value);

                // 检查是否是表达式
                if (value != null && value.startsWith("${") && value.endsWith("}")) {
                    inputMap.put(name + "_expression", value);
                }
            }
        }

        // 解析输出参数
        List<Element> outputs = findChildren(element, "outputParameter");
        Map<String, String> outputMap = (Map<String, String>) ioMapping.get("outputs");
        for (Element output : outputs) {
            String name = output.getAttribute("name");
            String value = getTextContent(output);
            if (!name.isEmpty()) {
                outputMap.put(name, value);

                if (value != null && value.startsWith("${") && value.endsWith("}")) {
                    outputMap.put(name + "_expression", value);
                }
            }
        }

        // 关联到父节点
        if (parent instanceof NodeDefinition node) {
            node.addProperty("inputParameters", inputMap);
            node.addProperty("outputParameters", outputMap);
        }

        log.debug("输入输出映射解析完成: inputs={}, outputs={}", inputMap.size(), outputMap.size());
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        // 无属性需要处理
    }

    @Override
    public String getRuleName() {
        return "InputOutputRule";
    }

    @Override
    public int getPriority() {
        return 50;
    }
}
