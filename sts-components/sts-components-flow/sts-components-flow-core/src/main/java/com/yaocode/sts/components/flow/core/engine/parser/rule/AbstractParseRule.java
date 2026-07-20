package com.yaocode.sts.components.flow.core.engine.parser.rule;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 抽象解析规则基类
 *
 * <p>提供通用的解析辅助方法
 *
 * @author Process Engine Team
 */
@Slf4j
public abstract class AbstractParseRule implements ParseRule {

    /**
     * 属性缓存
     */
    protected final Map<String, Object> propertyCache = new ConcurrentHashMap<>();

    @Override
    public void end(Element element, ParseContext context, Object parent, Object currentObject) {
        // 默认实现为空，子类可覆盖
    }

    /**
     * 获取元素属性值（支持默认值）
     */
    protected String getAttribute(Element element, String name, String defaultValue) {
        String value = element.getAttribute(name);
        return !value.isEmpty() ? value : defaultValue;
    }

    /**
     * 获取元素属性值（布尔类型）
     */
    protected boolean getBooleanAttribute(Element element, String name, boolean defaultValue) {
        String value = element.getAttribute(name);
        if (value.isEmpty()) {
            return defaultValue;
        }
        return "true".equalsIgnoreCase(value) || "1".equals(value);
    }

    /**
     * 获取元素属性值（整数类型）
     */
    protected int getIntAttribute(Element element, String name, int defaultValue) {
        String value = element.getAttribute(name);
        if (value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            log.warn("解析整数属性失败: {}={}", name, value);
            return defaultValue;
        }
    }

    /**
     * 查找子元素
     */
    protected Element findChild(Element parent, String tagName) {
        org.w3c.dom.NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            org.w3c.dom.Node child = children.item(i);
            if (child instanceof Element && tagName.equals(child.getNodeName())) {
                return (Element) child;
            }
        }
        return null;
    }

    /**
     * 查找所有匹配的子元素
     */
    protected List<Element> findChildren(Element parent, String tagName) {
        List<Element> result = new java.util.ArrayList<>();
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            org.w3c.dom.Node child = children.item(i);
            if (child instanceof Element && tagName.equals(child.getNodeName())) {
                result.add((Element) child);
            }
        }
        return result;
    }

    /**
     * 获取元素的文本内容
     */
    protected String getTextContent(Element element) {
        return element.getTextContent() != null ? element.getTextContent().trim() : "";
    }

    /**
     * 注册 ID 到上下文
     */
    protected void registerId(Element element, ParseContext context) {
        String id = element.getAttribute("id");
        if (!id.isEmpty()) {
            context.addElementId(id);
        }
    }
}
