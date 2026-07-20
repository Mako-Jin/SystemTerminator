package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import org.w3c.dom.Element;

/**
 * 解析监听器接口
 *
 * <p>在解析过程的关键节点提供扩展钩子，用于：
 * <ul>
 *   <li>日志记录</li>
 *   <li>性能监控</li>
 *   <li>自定义处理</li>
 *   <li>调试辅助</li>
 *   <li>缓存管理</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 添加监听器
 * parser.addParseListener(new LoggingParseListener());
 * parser.addParseListener(new MetricsParseListener());
 *
 * // 组合监听器
 * CompositeParseListener composite = new CompositeParseListener();
 * composite.addListener(new LoggingParseListener());
 * composite.addListener(new MetricsParseListener());
 * parser.addParseListener(composite);
 * </pre>
 *
 * @author Process Engine Team
 */
public interface ParseListener {

    // ==================== 生命周期事件 ====================

    /**
     * 解析开始前触发
     *
     * @param context 解析上下文
     */
    void parseStarted(ParseContext context);

    /**
     * 解析完成后触发
     *
     * @param context 解析上下文
     * @param result  解析结果
     */
    void parseCompleted(ParseContext context, Object result);

    /**
     * 解析失败时触发
     *
     * @param context 解析上下文
     * @param error   错误信息
     */
    void parseFailed(ParseContext context, Throwable error);

    // ==================== 元素事件（BPMN 解析器支持） ====================

    /**
     * 元素解析前触发
     *
     * @param element XML 元素
     * @param context 解析上下文
     */
    default void elementParsing(Element element, ParseContext context) {
        // 默认空实现，子类按需覆盖
    }

    /**
     * 元素解析后触发
     *
     * @param element      XML 元素
     * @param context      解析上下文
     * @param parsedObject 解析后的对象
     */
    default void elementParsed(Element element, ParseContext context, Object parsedObject) {
        // 默认空实现，子类按需覆盖
    }

    /**
     * 命名空间声明时触发
     *
     * @param prefix  命名空间前缀
     * @param uri     命名空间 URI
     * @param context 解析上下文
     */
    default void namespaceDeclared(String prefix, String uri, ParseContext context) {
        // 默认空实现，子类按需覆盖
    }

    // ==================== 其他事件 ====================

    /**
     * 验证开始前触发
     *
     * @param context 解析上下文
     */
    default void validationStarted(ParseContext context) {
        // 默认空实现，子类按需覆盖
    }

    /**
     * 验证完成后触发
     *
     * @param context 解析上下文
     * @param result  验证结果
     */
    default void validationCompleted(ParseContext context, Object result) {
        // 默认空实现，子类按需覆盖
    }

    /**
     * 获取监听器名称
     *
     * @return 监听器名称
     */
    default String getListenerName() {
        return getClass().getSimpleName();
    }

    /**
     * 获取监听器优先级（数值越小优先级越高）
     *
     * @return 优先级
     */
    default int getPriority() {
        return 100;
    }

    /**
     * 是否启用
     *
     * @return true 启用，false 禁用
     */
    default boolean isEnabled() {
        return true;
    }
}