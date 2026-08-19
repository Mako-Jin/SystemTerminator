package com.yaocode.sts.flow.core.engine.parser;

import com.yaocode.sts.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.flow.core.model.ProcessDefinition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 解析上下文
 *
 * <p>贯穿整个解析过程，存储解析状态和中间结果
 *
 * @author Process Engine Team
 */
@Getter
public class ParseContext {

    /**
     * 解析状态
     * -- SETTER --
     *  设置解析状态

     */
    @Setter
    private ParseStatusEnums status = ParseStatusEnums.INITIAL;

    /**
     * 错误列表
     */
    private final List<ParseError> errors = new ArrayList<>();

    /**
     * 警告列表
     */
    private final List<ParseWarning> warnings = new ArrayList<>();

    /**
     * 命名空间映射
     */
    private final Map<String, String> namespaces = new HashMap<>();

    /**
     * 已解析的元素ID集合
     */
    private final Set<String> elementIds = new HashSet<>();

    /**
     * 已解析的定义对象
     */
    private final Map<String, ProcessDefinition> definitions = new HashMap<>();

    /**
     * 当前命名空间
     * -- SETTER --
     *  设置当前命名空间

     */
    @Setter
    private String currentNamespace;

    /**
     * 元素属性映射
     */
    private final Map<String, Object> attributes = new HashMap<>();

    /**
     * 扩展属性
     */
    private final Map<String, Object> extensions = new HashMap<>();

    /**
     * 当前解析的对象
     * -- SETTER --
     *  设置当前解析的对象

     */
    @Setter
    private Object currentObject;

    /**
     * 父对象栈（用于维护父子关系，使用 ArrayDeque 替代 Stack 以获得更好的性能）
     */
    private final Deque<Object> parentStack = new ArrayDeque<>();

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

    public void addDefinition(String key, ProcessDefinition definition) {
        if (key != null && definition != null) {
            this.definitions.put(key, definition);
        }
    }

    public ProcessDefinition getDefinition(String key) {
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

    /**
     * 获取错误列表（不可修改视图）
     */
    public List<ParseError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    /**
     * 获取警告列表（不可修改视图）
     */
    public List<ParseWarning> getWarnings() {
        return Collections.unmodifiableList(warnings);
    }

    /**
     * 获取命名空间映射（不可修改视图）
     */
    public Map<String, String> getNamespaces() {
        return Collections.unmodifiableMap(namespaces);
    }

    /**
     * 获取元素 ID 集合（不可修改视图）
     */
    public Set<String> getElementIds() {
        return Collections.unmodifiableSet(elementIds);
    }

    /**
     * 获取定义映射（不可修改视图）
     */
    public Map<String, ProcessDefinition> getDefinitions() {
        return Collections.unmodifiableMap(definitions);
    }

    /**
     * 获取扩展属性（不可修改视图）
     */
    public Map<String, Object> getExtensions() {
        return Collections.unmodifiableMap(extensions);
    }

    /**
     * 获取元素属性映射（不可修改视图）
     */
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    public void clearAttributes() {
        attributes.clear();
    }

}