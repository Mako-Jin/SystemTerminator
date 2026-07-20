package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 指标统计监听器
 *
 * <p>统计解析过程中的各项指标：
 * <ul>
 *   <li>解析耗时</li>
 *   <li>元素数量统计</li>
 *   <li>错误/警告统计</li>
 *   <li>命名空间统计</li>
 *   <li>内存使用情况</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class MetricsParseListener extends AbstractParseListener {

    /**
     * 解析开始时间
     */
    private long startTime;

    /**
     * 元素统计
     */
    private final Map<String, AtomicLong> elementCount = new ConcurrentHashMap<>();

    /**
     * 命名空间统计
     */
    private final Map<String, AtomicLong> namespaceCount = new ConcurrentHashMap<>();

    /**
     * 错误统计
     */
    private final AtomicLong errorCount = new AtomicLong(0);

    /**
     * 警告统计
     */
    private final AtomicLong warningCount = new AtomicLong(0);

    /**
     * 当前内存使用
     */
    private long memoryUsed;

    /**
     * 是否输出统计报告
     */
    private boolean printReport = true;

    public MetricsParseListener() {
        super("MetricsParseListener");
    }

    public MetricsParseListener(boolean printReport) {
        super("MetricsParseListener");
        this.printReport = printReport;
    }

    @Override
    public void parseStarted(ParseContext context) {
        startTime = System.currentTimeMillis();
        memoryUsed = getMemoryUsed();

        if (log.isDebugEnabled()) {
            log.debug("开始解析，内存使用: {} MB", memoryUsed / (1024 * 1024));
        }
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        long elapsed = System.currentTimeMillis() - startTime;
        long memoryUsedAfter = getMemoryUsed();
        long memoryDiff = memoryUsedAfter - memoryUsed;

        if (printReport) {
            log.info("========== 解析指标报告 ==========");
            log.info("解析耗时: {} ms", elapsed);
            log.info("内存使用: {} MB -> {} MB (增长 {} MB)",
                    memoryUsed / (1024 * 1024),
                    memoryUsedAfter / (1024 * 1024),
                    memoryDiff / (1024 * 1024));
            log.info("元素总数: {}", getTotalElements());
            log.info("命名空间数: {}", namespaceCount.size());
            log.info("错误数: {}", errorCount.get());
            log.info("警告数: {}", warningCount.get());

            // 打印元素统计
            if (!elementCount.isEmpty()) {
                log.info("元素统计:");
                elementCount.entrySet().stream()
                        .sorted((e1, e2) -> Long.compare(e2.getValue().get(), e1.getValue().get()))
                        .forEach(e -> log.info("  - {}: {}", e.getKey(), e.getValue().get()));
            }

            // 打印命名空间统计
            if (!namespaceCount.isEmpty()) {
                log.info("命名空间统计:");
                namespaceCount.forEach((ns, count) ->
                        log.info("  - {}: {}", ns, count.get()));
            }
        }
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        long elapsed = System.currentTimeMillis() - startTime;
        log.error("解析失败，耗时: {} ms", elapsed);
        log.error("错误数: {}, 警告数: {}", errorCount.get(), warningCount.get());
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        String tagName = element.getTagName();
        elementCount.computeIfAbsent(tagName, k -> new AtomicLong()).incrementAndGet();
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        // 可以记录解析后的对象类型统计
        if (parsedObject != null) {
            String typeName = parsedObject.getClass().getSimpleName();
            elementCount.computeIfAbsent("parsed_" + typeName, k -> new AtomicLong()).incrementAndGet();
        }
    }

    @Override
    public void namespaceDeclared(String prefix, String uri, ParseContext context) {
        namespaceCount.computeIfAbsent(uri, k -> new AtomicLong()).incrementAndGet();
    }

    @Override
    public void validationStarted(ParseContext context) {
        if (log.isDebugEnabled()) {
            log.debug("验证开始，当前元素数: {}", context.getElementIds().size());
        }
    }

    @Override
    public void validationCompleted(ParseContext context, Object result) {
        // 记录验证结果
        if (result != null) {
            log.debug("验证完成，结果: {}", result);
        }
    }

    /**
     * 获取总元素数
     */
    public long getTotalElements() {
        return elementCount.values().stream()
                .mapToLong(AtomicLong::get)
                .sum();
    }

    /**
     * 获取内存使用（MB）
     */
    private long getMemoryUsed() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * 重置统计信息
     */
    public void reset() {
        elementCount.clear();
        namespaceCount.clear();
        errorCount.set(0);
        warningCount.set(0);
        startTime = 0;
        memoryUsed = 0;
    }

    public long getErrorCount() {
        return errorCount.get();
    }

    public long getWarningCount() {
        return warningCount.get();
    }

    @Override
    public String getListenerName() {
        return "MetricsParseListener";
    }
}
