package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

/**
 * 日志监听器
 *
 * <p>记录解析过程中的关键事件，用于调试和监控
 *
 * <p>日志级别：
 * <ul>
 *   <li>INFO - 解析开始/完成/失败</li>
 *   <li>DEBUG - 元素解析事件</li>
 *   <li>TRACE - 命名空间事件</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class LoggingParseListener extends AbstractParseListener {

    /**
     * 是否记录详细日志
     */
    private boolean verbose = false;

    public LoggingParseListener() {
        super("LoggingParseListener");
    }

    public LoggingParseListener(boolean verbose) {
        super("LoggingParseListener");
        this.verbose = verbose;
    }

    @Override
    public void parseStarted(ParseContext context) {
        log.info("========== 解析开始 ==========");
        log.info("解析状态: {}", context.getStatus());
        if (verbose) {
            log.debug("当前命名空间: {}", context.getCurrentNamespace());
            log.debug("已注册ID数量: {}", context.getElementIds().size());
        }
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        log.info("========== 解析完成 ==========");
        log.info("解析状态: {}", context.getStatus());
        log.info("错误数: {}, 警告数: {}",
                context.getErrors().size(),
                context.getWarnings().size());

        if (verbose) {
            log.debug("定义对象数量: {}", context.getDefinitions().size());
            if (result != null) {
                log.debug("解析结果类型: {}", result.getClass().getSimpleName());
            }
        }

        if (!context.getErrors().isEmpty()) {
            log.warn("存在解析错误:");
            context.getErrors().forEach(e ->
                    log.warn("  - [{}] {}", e.getSeverity(), e.getMessage()));
        }

        if (!context.getWarnings().isEmpty()) {
            log.info("存在解析警告:");
            context.getWarnings().forEach(w ->
                    log.info("  - {}", w.getMessage()));
        }
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        log.error("========== 解析失败 ==========");
        log.error("解析状态: {}", context.getStatus());
        log.error("错误信息: {}", error.getMessage(), error);

        if (!context.getErrors().isEmpty()) {
            log.error("已有错误:");
            context.getErrors().forEach(e ->
                    log.error("  - [{}] {}", e.getSeverity(), e.getMessage()));
        }
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        if (log.isDebugEnabled()) {
            String tagName = element.getTagName();
            String id = element.getAttribute("id");
            log.debug("开始解析元素: <{}> id='{}'", tagName, id);
        }
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        if (log.isDebugEnabled()) {
            String tagName = element.getTagName();
            String id = element.getAttribute("id");
            log.debug("元素解析完成: <{}> id='{}' -> {}", tagName, id,
                    parsedObject != null ? parsedObject.getClass().getSimpleName() : "null");
        }
    }

    @Override
    public void namespaceDeclared(String prefix, String uri, ParseContext context) {
        if (log.isTraceEnabled()) {
            log.trace("命名空间声明: {} -> {}", prefix, uri);
        }
    }

    @Override
    public void validationStarted(ParseContext context) {
        log.info("========== 验证开始 ==========");
        log.info("待验证元素数: {}", context.getElementIds().size());
    }

    @Override
    public void validationCompleted(ParseContext context, Object result) {
        log.info("========== 验证完成 ==========");
        log.info("验证结果: {}", result);
    }

    @Override
    public String getListenerName() {
        return "LoggingParseListener";
    }
}