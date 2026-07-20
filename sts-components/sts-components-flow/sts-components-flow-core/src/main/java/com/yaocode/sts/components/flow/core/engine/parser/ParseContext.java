package com.yaocode.sts.components.flow.core.engine.parser;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseWarning;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;


/**
 * 解析上下文
 *
 * <p>贯穿整个解析过程，存储解析状态和中间结果
 *
 * @author Process Engine Team
 */
@Data
public class ParseContext {

    /**
     * 解析状态
     */
    private ParseStatusEnums status = ParseStatusEnums.INITIAL;

    /**
     * 错误列表
     */
    private List<ParseError> errors = new ArrayList<>();

    /**
     * 警告列表
     */
    private List<ParseWarning> warnings = new ArrayList<>();

    /**
     * 命名空间映射
     */
    private Map<String, String> namespaces = new HashMap<>();

    /**
     * 当前命名空间
     */
    private String currentNamespace;

    /**
     * 已解析的元素ID集合
     */
    private Set<String> elementIds = new HashSet<>();

    /**
     * 已解析的定义对象
     */
    private Map<String, Object> definitions = new HashMap<>();

    /**
     * 元素属性映射
     */
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 扩展属性
     */
    private Map<String, Object> extensions = new HashMap<>();

    /**
     * 当前解析的对象
     */
    private Object currentObject;

    /**
     * 父对象栈（用于维护父子关系）
     */
    private Stack<Object> parentStack = new Stack<>();

    // ==================== 错误和警告管理 ====================

    public void addError(ParseError error) {
        if (error != null) {
            this.errors.add(error);
            if (error.getSeverity() == ErrorSeverityEnums.FATAL) {
                this.status = ParseStatusEnums.FAILED;
            }
        }
    }

    public void addWarning(ParseWarning warning) {
        if (warning != null) {
            this.warnings.add(warning);
        }
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public boolean hasFatalError() {
        return errors.stream().anyMatch(e -> e.getSeverity() == ErrorSeverityEnums.FATAL);
    }

    // ==================== ID 管理 ====================

    public void addElementId(String id) {
        if (id != null && !id.isEmpty()) {
            this.elementIds.add(id);
        }
    }

    public boolean containsElementId(String id) {
        return id != null && this.elementIds.contains(id);
    }

    // ==================== 定义管理 ====================

    public void addDefinition(String key, Object definition) {
        if (key != null && definition != null) {
            this.definitions.put(key, definition);
        }
    }

    public Object getDefinition(String key) {
        return key != null ? this.definitions.get(key) : null;
    }

    // ==================== 父对象栈管理 ====================

    public void pushParent(Object parent) {
        if (parent != null) {
            this.parentStack.push(parent);
        }
    }

    public void popParent() {
        if (!this.parentStack.isEmpty()) {
            this.parentStack.pop();
        }
    }

    public Object getCurrentParent() {
        return this.parentStack.isEmpty() ? null : this.parentStack.peek();
    }

    public boolean isParentStackEmpty() {
        return this.parentStack.isEmpty();
    }

    // ==================== 扩展属性管理 ====================

    public void putExtension(String key, Object value) {
        if (key != null) {
            this.extensions.put(key, value);
        }
    }

    public Object getExtension(String key) {
        return key != null ? this.extensions.get(key) : null;
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtension(String key, Class<T> type) {
        Object value = getExtension(key);
        if (value != null && type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        return null;
    }

    public void setAttribute(String key, Object value) {
        if (key != null) {
            if (value != null) {
                attributes.put(key, value);
            } else {
                attributes.remove(key);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key) {
        if (key == null) {
            return null;
        }
        return (T) attributes.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getAttribute(String key, T defaultValue) {
        if (key == null) {
            return defaultValue;
        }
        T value = (T) attributes.get(key);
        return value != null ? value : defaultValue;
    }

    public Object removeAttribute(String key) {
        return key != null ? attributes.remove(key) : null;
    }

    public boolean hasAttribute(String key) {
        return key != null && attributes.containsKey(key);
    }

    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void clearAttributes() {
        attributes.clear();
    }

}
