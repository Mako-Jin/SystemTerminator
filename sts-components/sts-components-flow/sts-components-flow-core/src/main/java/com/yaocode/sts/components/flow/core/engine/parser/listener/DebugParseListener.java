package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * 调试监听器
 *
 * <p>用于调试解析过程，输出详细的解析信息
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class DebugParseListener extends AbstractParseListener {

    /**
     * 是否输出元素属性
     */
    private boolean showAttributes = true;

    /**
     * 是否输出元素内容
     */
    private boolean showContent = false;

    /**
     * 最大输出深度
     */
    private int maxDepth = -1;

    /**
     * 当前深度
     */
    private int currentDepth = 0;

    public DebugParseListener() {
        super("DebugParseListener");
    }

    public DebugParseListener(boolean showAttributes) {
        super("DebugParseListener");
        this.showAttributes = showAttributes;
    }

    @Override
    public void parseStarted(ParseContext context) {
        currentDepth = 0;
        log.info("========== 调试模式开始 ==========");
        log.info("开始解析，当前状态: {}", context.getStatus());
        log.info("命名空间: {}", context.getNamespaces());
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        log.info("========== 调试模式结束 ==========");
        log.info("解析完成，状态: {}", context.getStatus());
        log.info("定义对象: {}", context.getDefinitions().keySet());
        log.info("元素ID: {}", context.getElementIds());
        log.info("错误: {}", context.getErrors());
        log.info("警告: {}", context.getWarnings());
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        log.error("========== 调试模式异常 ==========");
        log.error("解析失败，状态: {}", context.getStatus());
        log.error("异常信息: ", error);
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        currentDepth++;

        if (maxDepth >= 0 && currentDepth > maxDepth) {
            return;
        }

        String indent = getIndent();
        String tagName = element.getTagName();
        String id = element.getAttribute("id");

        log.debug("{}<{}>", indent, tagName);

        if (!id.isEmpty()) {
            log.debug("{}  id='{}'", indent, id);
        }

        if (showAttributes) {
            NamedNodeMap attributes = element.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                org.w3c.dom.Attr attr = (org.w3c.dom.Attr) attributes.item(i);
                String name = attr.getName();
                String value = attr.getValue();
                if (!"id".equals(name)) {
                    log.debug("{}  @{}='{}'", indent, name, value);
                }
            }
        }

        if (showContent) {
            String text = element.getTextContent();
            if (text != null && !text.trim().isEmpty()) {
                log.debug("{}  text='{}'", indent, text.trim());
            }
        }
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        if (maxDepth >= 0 && currentDepth > maxDepth) {
            currentDepth--;
            return;
        }

        String indent = getIndent();
        log.debug("{}</{}> -> {}", indent, element.getTagName(),
                parsedObject != null ? parsedObject.getClass().getSimpleName() : "null");

        currentDepth--;
    }

    @Override
    public void namespaceDeclared(String prefix, String uri, ParseContext context) {
        log.trace("命名空间: {} -> {}", prefix, uri);
    }

    private String getIndent() {
        return "  ".repeat(Math.max(0, currentDepth));
    }

    public void resetDepth() {
        this.currentDepth = 0;
    }

    @Override
    public String getListenerName() {
        return "DebugParseListener";
    }

    @Override
    public String toString() {
        return String.format("DebugParseListener{showAttributes=%s, showContent=%s, maxDepth=%d}",
                showAttributes, showContent, maxDepth);
    }
}
