package com.yaocode.sts.components.flow.core.engine.parser.impl;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.components.flow.core.engine.parser.rule.ParseRule;
import com.yaocode.sts.components.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.components.flow.core.engine.parser.xml.XmlParser;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * BPMN 2.0 流程解析器
 *
 * <p>支持解析 BPMN 2.0 XML 格式的流程定义文件。
 *
 * @author Process Engine Team
 */
@Slf4j
public class BpmnProcessParser extends AbstractProcessParser {

    /**
     * 支持的文件格式
     */
    private static final List<String> SUPPORTED_FORMATS = List.of(".bpmn", ".xml", ".bpmn20.xml");

    private static final int MAX_RECURSION_DEPTH = 100;

    /**
     * 格式名称
     */
    private static final String FORMAT_NAME = "BPMN 2.0";

    /**
     * 规则注册中心
     */
    @Setter
    private RuleRegistry ruleRegistry;

    /**
     * XML 解析器
     */
    @Setter
    private XmlParser xmlParser;

    /**
     * BPMN 模型构建器
     */
    private final BpmnModelBuilder modelBuilder = new BpmnModelBuilder();

    @Override
    protected Object doParse(byte[] content, String resourceName, ParseContext context) throws ParseException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(content)) {
            return doParse(bais, resourceName, context);
        } catch (Exception e) {
            throw new ParseException("BPMN 解析失败: " + resourceName, e);
        }
    }

    @Override
    protected Object doParse(InputStream inputStream, String resourceName, ParseContext context) throws ParseException {
        if (xmlParser == null) {
            throw new ParseException("XML 解析器未设置");
        }

        try {
            // 使用 XML 解析器解析
            Document document = xmlParser.parse(inputStream, resourceName);
            Element rootElement = document.getDocumentElement();

            if (rootElement == null) {
                throw new ParseException("BPMN 文档根元素为空");
            }

            // 使用规则引擎解析
            return parseElement(rootElement, context, null);

        } catch (Exception e) {
            throw new ParseException("BPMN 解析失败: " + resourceName, e);
        }
    }

    private Object parseElement(Element element, ParseContext context, Object parent) {
        return parseElement(element, context, parent, 0);
    }

    /**
     * 递归解析元素
     */
    private Object parseElement(Element element, ParseContext context, Object parent, int depth) {
        String tagName = element.getTagName();
        String localName = tagName.contains(":") ?
                tagName.substring(tagName.indexOf(":") + 1) : tagName;

        // 处理命名空间
        String namespaceURI = element.getNamespaceURI();
        if (namespaceURI != null && !namespaceURI.isEmpty()) {
            String prefix = element.getPrefix();
            context.getNamespaces().put(prefix != null ? prefix : "", namespaceURI);
        }

        // 获取解析规则
        ParseRule rule = ruleRegistry != null ? ruleRegistry.getRule(context, localName) : null;
        if (rule == null) {
            log.warn("未找到元素规则: {}", tagName);
            context.addWarning(ParseWarning.builder()
                    .message("未找到元素规则: " + tagName)
                    .element(element)
                    .build());
            return null;
        }

        // 压入父对象
        context.pushParent(parent);

        try {
            // 执行规则开始
            Object currentObject = rule.begin(element, context);
            if (Objects.nonNull(currentObject)) {
                context.setCurrentObject(currentObject);
            }

            // 设置属性
            org.w3c.dom.NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attributes.item(i);
                rule.setProperty(element, context, attr.getName(), attr.getValue());
            }

            if (depth > MAX_RECURSION_DEPTH) {
                context.addError(ParseError.builder()
                        .message("XML 嵌套深度超过限制: " + MAX_RECURSION_DEPTH)
                        .severity(ErrorSeverityEnums.FATAL)
                        .build());
                return null;
            }

            // 递归解析子元素
            org.w3c.dom.NodeList children = element.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                org.w3c.dom.Node child = children.item(i);
                if (child instanceof Element) {
                    parseElement((Element) child, context, currentObject, depth + 1);
                }
            }

            // 执行规则结束
            rule.end(element, context, parent, currentObject);

            return currentObject;

        } catch (Exception e) {
            log.error("解析元素失败: {}", tagName, e);
            context.addError(ParseError.builder()
                    .message("解析元素失败: " + tagName + ", " + e.getMessage())
                    .severity(ErrorSeverityEnums.ERROR)
                    .element(element)
                    .cause(e)
                    .build());
            return null;
        } finally {
            context.popParent();
        }
    }

    @Override
    protected ProcessDefinition buildProcessDefinition(Object parsedObject, ParseContext context) {
        return modelBuilder.build(parsedObject, context);
    }

    @Override
    public boolean supports(String resourceName) {
        if (resourceName == null) {
            return false;
        }
        String lower = resourceName.toLowerCase();
        return SUPPORTED_FORMATS.stream().anyMatch(lower::endsWith);
    }

    @Override
    public List<String> getSupportedFormats() {
        return SUPPORTED_FORMATS;
    }

    @Override
    public String getParserName() {
        return "BPMN 2.0 XML Parser";
    }

    @Override
    protected String getFormat() {
        return FORMAT_NAME;
    }

    @Override
    public String getVersion() {
        return "2.0.0";
    }

    @Override
    public String getDescription() {
        return "解析 BPMN 2.0 XML 格式的流程定义文件";
    }
}