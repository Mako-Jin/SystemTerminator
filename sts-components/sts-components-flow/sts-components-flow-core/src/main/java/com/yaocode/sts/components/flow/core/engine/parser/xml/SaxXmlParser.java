package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStrategyEnums;
import com.yaocode.sts.components.flow.core.engine.parser.rule.ParseRule;
import com.yaocode.sts.components.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * SAX 解析器实现
 *
 * <p>特点：
 * <ul>
 *   <li>流式解析，内存友好</li>
 *   <li>适合大文件（> 10MB）</li>
 *   <li>不支持随机访问和修改</li>
 *   <li>参考 Tomcat 的 Digester 实现</li>
 * </ul>
 *
 * <p>使用场景：
 * <ul>
 *   <li>超大 XML 文件解析</li>
 *   <li>只需要读取不需要修改的场景</li>
 *   <li>与规则引擎配合使用</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class SaxXmlParser extends AbstractXmlParser {

    /**
     * 规则注册中心
     */
    @Setter
    private RuleRegistry ruleRegistry;

    /**
     * 解析上下文
     */
    @Setter
    private ParseContext context;

    /**
     * 元素栈（用于跟踪当前元素路径）
     */
    private final Stack<String> elementStack = new Stack<>();

    /**
     * 对象栈（用于跟踪当前对象）
     */
    private final Stack<Object> objectStack = new Stack<>();

    /**
     * 命名空间映射
     */
    private final Map<String, String> namespaceMap = new HashMap<>();

    @Override
    public Document parse(InputStream inputStream, String systemId) throws ParseException {
        long startTime = System.currentTimeMillis();
        logParseStart(systemId);

        try {
            SAXParserFactory factory = getSAXParserFactory();
            SAXParser parser = factory.newSAXParser();

            // 创建 SAX 处理器（参考 Tomcat Digester 风格）
            DefaultHandler handler = new DefaultHandler() {

                @Override
                public void startElement(String uri, String localName,
                                         String qName, Attributes attributes) throws SAXException {
                    // 记录命名空间
                    if (uri != null && !uri.isEmpty()) {
                        String prefix = getPrefix(qName);
                        namespaceMap.put(prefix, uri);
                        if (context != null) {
                            context.getNamespaces().put(prefix, uri);
                        }
                    }

                    // 更新元素栈
                    elementStack.push(qName);

                    // 获取规则
                    ParseRule rule = getRule(qName);
                    if (rule == null) {
                        log.trace("未找到元素规则: {}", qName);
                        return;
                    }

                    try {
                        // 创建元素对象（用于规则）
                        Element element = createElement(qName, attributes);

                        // 执行规则 begin
                        Object currentObject = rule.begin(element, context);
                        if (currentObject != null) {
                            objectStack.push(currentObject);
                            if (context != null) {
                                context.setCurrentObject(currentObject);
                            }
                        }

                        // 设置属性
                        for (int i = 0; i < attributes.getLength(); i++) {
                            String attrName = attributes.getQName(i);
                            String attrValue = attributes.getValue(i);
                            rule.setProperty(element, context, attrName, attrValue);
                        }

                        // 注册 ID
                        String id = attributes.getValue("id");
                        if (id != null && !id.isEmpty() && context != null) {
                            context.addElementId(id);
                        }

                    } catch (Exception e) {
                        log.error("处理元素开始失败: {}", qName, e);
                        throw new SAXException("处理元素开始失败: " + qName, e);
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    try {
                        // 获取规则
                        ParseRule rule = getRule(qName);
                        if (rule == null) {
                            elementStack.pop();
                            return;
                        }

                        // 弹出对象
                        Object currentObject = objectStack.isEmpty() ? null : objectStack.pop();
                        Object parent = objectStack.isEmpty() ? null : objectStack.peek();

                        // 执行规则 end
                        if (context != null) {
                            context.setCurrentObject(currentObject);
                        }
                        rule.end(null, context, parent, currentObject);

                        // 弹出元素栈
                        elementStack.pop();

                    } catch (Exception e) {
                        log.error("处理元素结束失败: {}", qName, e);
                        throw new SAXException("处理元素结束失败: " + qName, e);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    String text = new String(ch, start, length).trim();
                    if (text.isEmpty()) {
                        return;
                    }

                    // 如果有当前对象，可以处理文本内容
                    if (!objectStack.isEmpty()) {
                        Object current = objectStack.peek();
                        // 文本内容可以在规则中处理
                        // 这里只是记录，具体处理由规则负责
                    }
                }

                @Override
                public void warning(SAXParseException e) {
                    log.warn("SAX 解析警告: systemId={}, line={}, column={}, message={}",
                            systemId, e.getLineNumber(), e.getColumnNumber(), e.getMessage());
                }

                @Override
                public void error(SAXParseException e) throws SAXException {
                    log.error("SAX 解析错误: systemId={}, line={}, column={}, message={}",
                            systemId, e.getLineNumber(), e.getColumnNumber(), e.getMessage());
                    throw e;
                }

                @Override
                public void fatalError(SAXParseException e) throws SAXException {
                    log.error("SAX 致命错误: systemId={}, line={}, column={}, message={}",
                            systemId, e.getLineNumber(), e.getColumnNumber(), e.getMessage());
                    throw e;
                }

                private String getPrefix(String qName) {
                    int colonIndex = qName.indexOf(':');
                    return colonIndex > 0 ? qName.substring(0, colonIndex) : "";
                }
            };

            // 执行解析
            parser.parse(inputStream, handler);

            logParseComplete(systemId, startTime);
            log.debug("SAX 解析完成: systemId={}", systemId);

            // SAX 模式不构建完整 Document，返回 null
            return null;

        } catch (Exception e) {
            logParseError(systemId, e);
            throw new ParseException(
                    String.format("SAX 解析失败: systemId=%s, message=%s",
                            systemId, e.getMessage()),
                    e);
        }
    }

    /**
     * 获取规则
     */
    private ParseRule getRule(String qName) {
        if (ruleRegistry == null) {
            return null;
        }

        return ruleRegistry.getRule(context, qName);
    }

    /**
     * 获取前缀
     */
    private String getPrefix(String qName) {
        int colonIndex = qName.indexOf(':');
        return colonIndex > 0 ? qName.substring(0, colonIndex) : "";
    }

    /**
     * 创建 Element 对象（用于规则）
     */
    private Element createElement(String qName, Attributes attributes) {
        try {
            javax.xml.parsers.DocumentBuilderFactory factory =
                    javax.xml.parsers.DocumentBuilderFactory.newInstance();
            org.w3c.dom.Document doc = factory.newDocumentBuilder().newDocument();
            Element element = doc.createElement(qName);

            for (int i = 0; i < attributes.getLength(); i++) {
                element.setAttribute(attributes.getQName(i), attributes.getValue(i));
            }

            return element;
        } catch (Exception e) {
            throw new RuntimeException("创建元素失败", e);
        }
    }

    @Override
    public ParseStrategyEnums getStrategy() {
        return ParseStrategyEnums.SAX;
    }

    @Override
    public String getDescription() {
        return String.format("SAX XML Parser (ruleRegistry=%s, context=%s)",
                ruleRegistry != null ? "enabled" : "disabled",
                context != null ? "enabled" : "disabled");
    }

    // ==================== 状态查询 ====================

    /**
     * 获取当前元素路径
     */
    public String getCurrentPath() {
        return String.join("/", elementStack);
    }

    /**
     * 获取当前元素栈深度
     */
    public int getDepth() {
        return elementStack.size();
    }

    /**
     * 重置解析器状态
     */
    public void reset() {
        elementStack.clear();
        objectStack.clear();
        namespaceMap.clear();
        if (context != null) {
            context.setCurrentObject(null);
        }
    }
}
