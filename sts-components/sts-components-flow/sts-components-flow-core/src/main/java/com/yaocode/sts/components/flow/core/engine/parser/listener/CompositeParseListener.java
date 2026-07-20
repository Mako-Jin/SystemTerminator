package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 组合监听器
 *
 * <p>将多个监听器组合成一个，按顺序执行
 *
 * <p>使用示例：
 * <pre>
 * CompositeParseListener composite = new CompositeParseListener();
 * composite.addListener(new LoggingParseListener());
 * composite.addListener(new MetricsParseListener());
 * composite.addListener(new ValidationParseListener());
 * parser.addParseListener(composite);
 * </pre>
 *
 * @author Process Engine Team
 */
public class CompositeParseListener extends AbstractParseListener {

    /**
     * 监听器列表（按优先级排序）
     */
    private final List<ParseListener> listeners = new ArrayList<>();

    public CompositeParseListener() {
        super("CompositeParseListener");
    }

    public CompositeParseListener(String name) {
        super(name);
    }

    /**
     * 添加监听器（自动按优先级排序）
     */
    public CompositeParseListener addListener(ParseListener listener) {
        if (listener != null && listener.isEnabled()) {
            // 避免添加自身
            if (listener == this) {
                throw new IllegalArgumentException("不能添加自身作为子监听器");
            }

            // 如果是组合监听器，展开其子监听器
            if (listener instanceof CompositeParseListener composite) {
                for (ParseListener child : composite.getListeners()) {
                    addListener(child);
                }
            } else {
                listeners.add(listener);
                // 按优先级排序
                listeners.sort(Comparator.comparingInt(ParseListener::getPriority));
            }
        }
        return this;
    }

    /**
     * 移除监听器
     */
    public CompositeParseListener removeListener(ParseListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
        return this;
    }

    /**
     * 移除所有监听器
     */
    public CompositeParseListener clearListeners() {
        listeners.clear();
        return this;
    }

    /**
     * 获取所有监听器
     */
    public List<ParseListener> getListeners() {
        return Collections.unmodifiableList(listeners);
    }

    /**
     * 获取监听器数量
     */
    public int getListenerCount() {
        return listeners.size();
    }

    @Override
    public void parseStarted(ParseContext context) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.parseStarted(context);
            }
        }
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.parseCompleted(context, result);
            }
        }
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.parseFailed(context, error);
            }
        }
    }

    @Override
    public void elementParsing(Element element, ParseContext context) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.elementParsing(element, context);
            }
        }
    }

    @Override
    public void elementParsed(Element element, ParseContext context, Object parsedObject) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.elementParsed(element, context, parsedObject);
            }
        }
    }

    @Override
    public void namespaceDeclared(String prefix, String uri, ParseContext context) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.namespaceDeclared(prefix, uri, context);
            }
        }
    }

    @Override
    public void validationStarted(ParseContext context) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.validationStarted(context);
            }
        }
    }

    @Override
    public void validationCompleted(ParseContext context, Object result) {
        for (ParseListener listener : listeners) {
            if (listener.isEnabled()) {
                listener.validationCompleted(context, result);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return !listeners.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("%s{listeners=%d, names=%s}",
                getClass().getSimpleName(),
                listeners.size(),
                listeners.stream().map(ParseListener::getListenerName).toList());
    }
}
