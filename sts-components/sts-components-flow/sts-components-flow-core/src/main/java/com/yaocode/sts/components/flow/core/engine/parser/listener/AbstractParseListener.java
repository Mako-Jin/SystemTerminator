package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.Setter;
import org.w3c.dom.Element;

/**
 * 抽象解析监听器基类
 *
 * <p>提供空实现，方便用户只关注感兴趣的事件
 *
 * <p>使用示例：
 * <pre>
 * public class CustomListener extends AbstractParseListener {
 *     {@literal @}Override
 *     public void parseCompleted(ParseContext context, Object result) {
 *         // 只关注解析完成事件
 *         System.out.println("解析完成: " + result);
 *     }
 * }
 * </pre>
 *
 * @author Process Engine Team
 */
public abstract class AbstractParseListener implements ParseListener {

    /**
     * 监听器名称
     */
    private String name;

    /**
     * 是否启用
     */
    @Setter
    private boolean enabled = true;

    /**
     * 优先级
     */
    @Setter
    private int priority = 100;

    public AbstractParseListener() {
        this.name = getClass().getSimpleName();
    }

    public AbstractParseListener(String name) {
        this.name = name;
    }

    @Override
    public void parseStarted(ParseContext context) {
        // 默认空实现
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        // 默认空实现
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        // 默认空实现
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        // 默认空实现
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        // 默认空实现
    }

    @Override
    public void namespaceDeclared(String prefix, String uri, ParseContext context) {
        // 默认空实现
    }

    @Override
    public void validationStarted(ParseContext context) {
        // 默认空实现
    }

    @Override
    public void validationCompleted(ParseContext context, Object result) {
        // 默认空实现
    }

    @Override
    public String getListenerName() {
        return name;
    }

    public void setListenerName(String name) {
        this.name = name;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return String.format("%s{name='%s', enabled=%s, priority=%d}",
                getClass().getSimpleName(), name, enabled, priority);
    }
}
