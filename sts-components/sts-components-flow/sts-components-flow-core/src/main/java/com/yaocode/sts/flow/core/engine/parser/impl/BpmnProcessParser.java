package com.yaocode.sts.flow.core.engine.parser.impl;

import com.yaocode.sts.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.engine.parser.rule.ParseRule;
import com.yaocode.sts.flow.core.engine.parser.rule.RuleRegistry;
import com.yaocode.sts.flow.core.engine.parser.xml.XmlParser;
import com.yaocode.sts.flow.core.exception.ParseException;
import com.yaocode.sts.flow.core.model.ProcessDefinition;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static final int MAX_ELEMENT_COUNT = 100000; // 最大元素数量限制

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

    /**
     * 延迟执行记录（使用对象引用作为 key，替代不稳定的 identityHashCode）
     */
    private final Map<Object, PendingEnd> pendingEnds = new ConcurrentHashMap<>();

    @Override
    protected ProcessDefinition doParse(byte[] content, String resourceName, ParseContext context) throws ParseException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(content)) {
            return doParse(bais, resourceName, context);
        } catch (Exception e) {
            throw new ParseException("BPMN 解析失败: " + resourceName, e);
        }
    }

    @Override
    protected ProcessDefinition doParse(InputStream inputStream, String resourceName, ParseContext context) throws ParseException {
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

            // 使用迭代方式解析
            return parseElementIterative(rootElement, context);

        } catch (Exception e) {
            throw new ParseException("BPMN 解析失败: " + resourceName, e);
        }
    }

    /**
     * 迭代式元素解析 - 避免栈溢出
     */
    private ProcessDefinition parseElementIterative(Element rootElement, ParseContext context) {
        // 使用栈来模拟递归
        Deque<ParseFrame> stack = new ArrayDeque<>();

        // 初始化根元素解析帧
        ParseFrame rootFrame = new ParseFrame(rootElement, null, 0);
        stack.push(rootFrame);

        int elementCount = 0;

        while (!stack.isEmpty()) {
            ParseFrame frame = stack.pop();
            Element element = frame.element;
            Object parent = frame.parent;
            int depth = frame.depth;

            // 检查元素数量限制
            if (++elementCount > MAX_ELEMENT_COUNT) {
                context.addError(ParseError.builder()
                        .message("元素数量超过限制: " + MAX_ELEMENT_COUNT)
                        .severity(ErrorSeverityEnums.FATAL)
                        .build());
                return null;
            }

            // 处理当前元素
            Object currentObject = processElement(element, context, parent);

            if (currentObject != null) {
                context.setCurrentObject(currentObject);
            }

            // 获取子元素并压入栈（逆序入栈保证处理顺序）
            List<Element> children = getChildElements(element);
            for (int i = children.size() - 1; i >= 0; i--) {
                Element child = children.get(i);
                ParseFrame childFrame = new ParseFrame(child, currentObject != null ? currentObject : parent, depth + 1);
                stack.push(childFrame);
            }
        }

        // 消费所有 PendingEnd，确保 rule.end() 在子元素之后被调用
        consumePendingEnds(context);

        // 返回根元素解析结果
        return context.getDefinition("root");
    }

    /**
     * 处理单个元素
     */
    private Object processElement(Element element, ParseContext context, Object parent) {
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
            log.trace("未找到元素规则: {}", tagName);
            return null;
        }

        // 处理元素开始
        context.pushParent(parent);
        try {
            Object currentObject = rule.begin(element, context);

            // 设置属性
            org.w3c.dom.NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attributes.item(i);
                rule.setProperty(element, context, attr.getName(), attr.getValue());
            }

            // 执行规则结束（在子元素处理完成后，由框架自动调用）
            // 使用延迟执行机制，以对象引用为 key 保证唯一性
            if (currentObject != null) {
                pendingEnds.put(currentObject, new PendingEnd(rule, element, parent, currentObject));
            }

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

    /**
     * 获取子元素列表
     */
    private List<Element> getChildElements(Element element) {
        List<Element> children = new ArrayList<>();
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                children.add((Element) node);
            }
        }
        return children;
    }

    /**
     * 消费所有 PendingEnd，按 LIFO 顺序执行 rule.end()
     * 确保父元素的 end 在子元素之后调用
     */
    private void consumePendingEnds(ParseContext context) {
        // 按注册的逆序执行（子元素先 end，父元素后 end）
        List<Map.Entry<Object, PendingEnd>> entries = new ArrayList<>(pendingEnds.entrySet());
        for (int i = entries.size() - 1; i >= 0; i--) {
            Map.Entry<Object, PendingEnd> entry = entries.get(i);
            PendingEnd pending = entry.getValue();
            try {
                pending.rule().end(pending.element(), context, pending.parent(), pending.currentObject());
            } catch (Exception e) {
                log.error("执行规则 end 失败: {}", pending.rule().getRuleName(), e);
                context.addError(ParseError.builder()
                        .message("执行规则结束失败: " + pending.rule().getRuleName())
                        .severity(ErrorSeverityEnums.ERROR)
                        .element(pending.element())
                        .cause(e)
                        .build());
            }
        }
        pendingEnds.clear();
    }

    /**
     * 解析帧 - 用于迭代解析
     */
    private record ParseFrame(Element element, Object parent, int depth) {}

    /**
     * 延迟执行记录
     */
    private record PendingEnd(
            ParseRule rule,
            Element element,
            Object parent,
            Object currentObject
    ) {}

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