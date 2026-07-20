package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.model.NodeDefinition;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Camunda 属性规则
 *
 * <p>解析 Camunda 的 properties 扩展元素
 *
 * @author Process Engine Team
 */
@Slf4j
public class PropertiesRule extends AbstractParseRule {

    @Override
    public Object begin(Element element, ParseContext context) {
        // 创建属性容器
        Map<String, String> properties = new HashMap<>();

        // 关联到父节点
        Object parent = context.getCurrentParent();
        if (parent instanceof NodeDefinition) {
            properties.put("__parent", ((NodeDefinition) parent).getNodeId());
        }

        log.debug("开始解析 Camunda 属性");
        return properties;
    }

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        Map<String, String> properties = (Map<String, String>) currentObject;

        // 解析每个 property 子元素
        List<Element> propertyList = findChildren(element, "property");
        for (Element prop : propertyList) {
            String name = prop.getAttribute("name");
            String value = prop.getAttribute("value");

            if (!name.isEmpty() && !value.isEmpty()) {
                properties.put(name, value);

                // 检查是否是表达式
                if (value.startsWith("${") && value.endsWith("}")) {
                    properties.put(name + "_expression", value);
                }
            }

            // 也支持通过文本内容设置值
            if (value.isEmpty()) {
                String textValue = getTextContent(prop);
                if (!textValue.isEmpty()) {
                    properties.put(name, textValue);
                }
            }
        }

        // 关联到父节点
        if (parent instanceof NodeDefinition node) {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                if (!"__parent".equals(entry.getKey())) {
                    node.addProperty("camunda_" + entry.getKey(), entry.getValue());
                }
            }
        }

        log.debug("Camunda 属性解析完成: {} 个属性", properties.size() - 1);
    }

    @Override
    public void setProperty(Element element, ParseContext context,
                            String attributeName, String attributeValue) {
        // 无属性需要处理
    }

    @Override
    public String getRuleName() {
        return "PropertiesRule";
    }

    @Override
    public int getPriority() {
        return 50;
    }

}
