package com.yaocode.sts.flow.core.engine.parser;

import com.yaocode.sts.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.engine.parser.error.ParseWarning;
import com.yaocode.sts.flow.core.model.NodeDefinition;
import com.yaocode.sts.flow.core.model.ProcessDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * 解析上下文测试类
 */
@DisplayName("解析上下文测试")
class ParseContextTest {

    private ParseContext context;

    @BeforeEach
    void setUp() {
        context = new ParseContext();
    }

    @Test
    @DisplayName("测试父对象栈操作")
    void testParentStack() {
        // 初始时当前父对象为空
        assertNull(context.getCurrentParent());

        // 压入父对象
        NodeDefinition parent1 = new NodeDefinition();
        parent1.setNodeId("parent1");
        context.pushParent(parent1);
        assertEquals(parent1, context.getCurrentParent());

        // 压入另一个父对象
        ProcessDefinition parent2 = new ProcessDefinition();
        parent2.setProcessId("parent2");
        context.pushParent(parent2);
        assertEquals(parent2, context.getCurrentParent());

        // 弹出父对象
        context.popParent();
        assertEquals(parent1, context.getCurrentParent());

        // 再次弹出
        context.popParent();
        assertNull(context.getCurrentParent());
    }

    @Test
    @DisplayName("测试当前对象操作")
    void testCurrentObject() {
        assertNull(context.getCurrentObject());

        NodeDefinition node = new NodeDefinition();
        node.setNodeId("test-node");
        context.setCurrentObject(node);

        assertEquals(node, context.getCurrentObject());

        // 清除当前对象
        context.setCurrentObject(null);
        assertNull(context.getCurrentObject());
    }

    @Test
    @DisplayName("测试属性操作")
    void testAttributes() {
        assertNull(context.getAttribute("testKey"));

        context.setAttribute("testKey", "testValue");
        assertEquals("testValue", context.getAttribute("testKey"));

        // 覆盖属性
        context.setAttribute("testKey", "newValue");
        assertEquals("newValue", context.getAttribute("testKey"));
    }

    @Test
    @DisplayName("测试命名空间操作")
    void testNamespaces() {
        assertTrue(context.getNamespaces().isEmpty());

        context.setCurrentNamespace("http://example.com/test");
        assertEquals("http://example.com/test", context.getCurrentNamespace());

        // 添加命名空间映射
        context.getNamespaces().put("ns", "http://example.com/ns");
        assertEquals("http://example.com/ns", context.getNamespaces().get("ns"));
    }

    @Test
    @DisplayName("测试元素ID管理")
    void testElementIds() {
        assertFalse(context.containsElementId("test-id"));

        context.addElementId("test-id");
        assertTrue(context.containsElementId("test-id"));
        assertFalse(context.containsElementId("other-id"));
    }

    @Test
    @DisplayName("测试定义管理")
    void testDefinitions() {
        assertTrue(context.getDefinitions().isEmpty());

        ProcessDefinition process = new ProcessDefinition();
        process.setProcessId("test-process");
        context.addDefinition("test-process", process);

        assertEquals(1, context.getDefinitions().size());
        assertEquals(process, context.getDefinitions().get("test-process"));
    }

    @Test
    @DisplayName("测试错误管理")
    void testErrors() {
        assertTrue(context.getErrors().isEmpty());
        assertFalse(context.hasError());
        assertFalse(context.hasFatalError());

        ParseError error1 = ParseError.builder()
                .errorCode("ERR001")
                .message("测试错误1")
                .build();
        context.addError(error1);

        assertEquals(1, context.getErrors().size());
        assertTrue(context.hasError());
        assertFalse(context.hasFatalError());

        // 添加致命错误
        ParseError fatalError = ParseError.builder()
                .errorCode("FATAL001")
                .message("致命错误")
                .fatal(true)
                .build();
        context.addError(fatalError);

        assertTrue(context.hasFatalError());
    }

    @Test
    @DisplayName("测试警告管理")
    void testWarnings() {
        assertTrue(context.getWarnings().isEmpty());

        ParseWarning warning = ParseWarning.builder()
                .warningCode("WARN001")
                .message("测试警告")
                .build();
        context.addWarning(warning);

        assertEquals(1, context.getWarnings().size());
    }

    @Test
    @DisplayName("测试扩展属性")
    void testExtensions() {
        assertTrue(context.getExtensions().isEmpty());

        context.addExtension("customKey", "customValue");
        assertEquals("customValue", context.getExtension("customKey", String.class));
        assertEquals(1, context.getExtensions().size());
    }

    @Test
    @DisplayName("测试重置上下文")
    void testReset() {
        // 设置各种属性
        context.setAttribute("key1", "value1");
        context.addError(ParseError.of("test error"));
        context.addWarning(ParseWarning.of("test warning"));
        context.addExtension("extKey", "extValue");
        context.getNamespaces().put("ns", "uri");

        context.reset();

        // 验证所有状态已清除
        assertNull(context.getAttribute("key1"));
        assertTrue(context.getErrors().isEmpty());
        assertTrue(context.getWarnings().isEmpty());
        assertTrue(context.getExtensions().isEmpty());
        assertTrue(context.getNamespaces().isEmpty());
        assertNull(context.getCurrentParent());
        assertNull(context.getCurrentObject());
    }
}