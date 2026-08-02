package com.yaocode.sts.flow.core.engine.parser.impl;

import com.yaocode.sts.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.flow.core.engine.parser.ParseResult;
import com.yaocode.sts.flow.core.engine.parser.ParserConfiguration;
import com.yaocode.sts.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.engine.parser.listener.ParseListener;
import com.yaocode.sts.flow.core.engine.parser.api.Validator;
import com.yaocode.sts.flow.core.engine.parser.ValidationResult;
import com.yaocode.sts.flow.core.exception.ParseException;
import com.yaocode.sts.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 抽象流程解析器测试类
 */
@Slf4j
@DisplayName("抽象流程解析器测试")
class AbstractProcessParserTest {

    @Mock
    private ParseListener mockListener;

    @Mock
    private Validator mockValidator;

    private TestProcessParser testParser;

    @BeforeEach
    void setUp() {
        try (AutoCloseable closeable = MockitoAnnotations.openMocks(this)) {
            testParser = new TestProcessParser();
        } catch (Exception e) {
            throw new RuntimeException("初始化测试解析器失败", e);
        }

    }

    /**
     * 测试用的具体解析器实现
     */
    private static class TestProcessParser extends AbstractProcessParser {

        private boolean parseSuccess = true;
        private Object parseResult;

        void setParseSuccess(boolean parseSuccess) {
            this.parseSuccess = parseSuccess;
        }

        void setParseResult(Object parseResult) {
            this.parseResult = parseResult;
        }

        @Override
        protected Object doParse(byte[] content, String resourceName, ParseContext context) throws ParseException {
            if (!parseSuccess) {
                throw new ParseException("测试解析失败");
            }
            return parseResult != null ? parseResult : new Object();
        }

        @Override
        protected Object doParse(InputStream inputStream, String resourceName, ParseContext context) throws ParseException {
            if (!parseSuccess) {
                throw new ParseException("测试解析失败");
            }
            return parseResult != null ? parseResult : new Object();
        }

        @Override
        protected ProcessDefinition buildProcessDefinition(Object parsedObject, ParseContext context) {
            return ProcessDefinition.builder()
                    .processId("test-process")
                    .processName("测试流程")
                    .build();
        }

        @Override
        protected String getFormat() {
            return "TEST";
        }

        @Override
        public boolean supports(String resourceName) {
            return false;
        }

        @Override
        public List<String> getSupportedFormats() {
            return List.of();
        }

        @Override
        public String getParserName() {
            return "Test Parser";
        }

        @Override
        public String getVersion() {
            return "1.0.0";
        }

        @Override
        public String getDescription() {
            return "测试解析器";
        }
    }

    @Test
    @DisplayName("测试解析成功流程")
    void testParseSuccess() {
        // 1. 执行解析
        ParseResult result = testParser.parse("test content".getBytes(), "test.txt");

        // 2. 验证结果
        assertNotNull(result, "解析结果不能为空");
        assertTrue(result.isSuccess(), "解析应成功");
        assertEquals(ParseStatusEnums.COMPLETED, result.getStatus(), "解析状态应为COMPLETED");
        assertNotNull(result.getProcessDefinition(), "流程定义不能为空");
        assertEquals("test-process", result.getProcessDefinition().getProcessId(), "流程ID不正确");
        assertEquals("TEST", result.getFormat(), "格式名称不正确");
    }

    @Test
    @DisplayName("测试解析失败流程")
    void testParseFailure() {
        // 1. 设置解析失败
        testParser.setParseSuccess(false);

        // 2. 执行解析
        ParseResult result = testParser.parse("test content".getBytes(), "test.txt");

        // 3. 验证结果
        assertNotNull(result, "解析结果不能为空");
        assertFalse(result.isSuccess(), "解析应失败");
        assertEquals(ParseStatusEnums.FAILED, result.getStatus(), "解析状态应为FAILED");
        assertNotNull(result.getErrors(), "错误列表不能为空");
        assertFalse(result.getErrors().isEmpty(), "应有错误信息");
    }

    @Test
    @DisplayName("测试使用InputStream解析")
    void testParseWithInputStream() {
        // 1. 创建输入流
        try (InputStream inputStream = new ByteArrayInputStream("test content".getBytes())) {
            // 2. 执行解析
            ParseResult result = testParser.parse(inputStream, "test.txt");

            // 3. 验证结果
            assertNotNull(result, "解析结果不能为空");
            assertTrue(result.isSuccess(), "解析应成功");
        }
    }

    @Test
    @DisplayName("测试监听器触发")
    void testParseListener() {
        // 1. 添加监听器
        AtomicInteger startedCount = new AtomicInteger(0);
        AtomicInteger completedCount = new AtomicInteger(0);
        ParseListener countingListener = new ParseListener() {
            @Override
            public void parseStarted(ParseContext context) {
                startedCount.incrementAndGet();
            }

            @Override
            public void parseCompleted(ParseContext context, Object result) {
                completedCount.incrementAndGet();
            }

            @Override
            public void parseFailed(ParseContext context, Throwable error) {
            }
        };
        testParser.addParseListener(countingListener);

        // 2. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 3. 验证监听器被调用
        assertEquals(1, startedCount.get(), "parseStarted应被调用1次");
        assertEquals(1, completedCount.get(), "parseCompleted应被调用1次");
    }

    @Test
    @DisplayName("测试监听器在解析失败时触发")
    void testParseListenerOnFailure() {
        // 1. 添加监听器
        AtomicInteger failedCount = new AtomicInteger(0);
        ParseListener countingListener = new ParseListener() {
            @Override
            public void parseStarted(ParseContext context) {
            }

            @Override
            public void parseCompleted(ParseContext context, Object result) {
            }

            @Override
            public void parseFailed(ParseContext context, Throwable error) {
                failedCount.incrementAndGet();
            }
        };
        testParser.addParseListener(countingListener);
        testParser.setParseSuccess(false);

        // 2. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 3. 验证监听器被调用
        assertEquals(1, failedCount.get(), "parseFailed应被调用1次");
    }

    @Test
    @DisplayName("测试验证器执行")
    void testValidator() {
        // 1. 创建验证器
        ValidationResult validationResult = ValidationResult.builder()
                .errors(new ArrayList<>())
                .warnings(List.of())
                .build();
        when(mockValidator.validate(any())).thenReturn(validationResult);
        when(mockValidator.getName()).thenReturn("TestValidator");
        when(mockValidator.getPriority()).thenReturn(100);
        when(mockValidator.continueOnFatalError()).thenReturn(true);

        // 2. 添加验证器
        testParser.addValidator(mockValidator);

        // 3. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 4. 验证验证器被调用
        verify(mockValidator, times(1)).validate(any());
    }

    @Test
    @DisplayName("测试跳过验证配置")
    void testSkipValidation() {
        // 1. 创建验证器
        when(mockValidator.validate(any())).thenReturn(ValidationResult.builder().build());

        // 2. 添加验证器并设置跳过验证
        testParser.addValidator(mockValidator);
        ParserConfiguration config = ParserConfiguration.builder()
                .skipValidation(true)
                .build();
        testParser.setConfiguration(config);

        // 3. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 4. 验证验证器未被调用
        verify(mockValidator, never()).validate(any());
    }

    @Test
    @DisplayName("测试严格模式下致命错误抛出异常")
    void testStrictModeWithFatalError() {
        // 1. 设置严格模式
        ParserConfiguration config = ParserConfiguration.builder()
                .strictMode(true)
                .build();
        testParser.setConfiguration(config);

        // 2. 创建验证器返回致命错误
        ParseError fatalError = ParseError.builder()
                .message("致命错误")
                .severity(ErrorSeverityEnums.FATAL)
                .build();
        ValidationResult validationResult = ValidationResult.builder()
                .errors(List.of(fatalError))
                .build();
        when(mockValidator.validate(any())).thenReturn(validationResult);
        when(mockValidator.getName()).thenReturn("TestValidator");
        when(mockValidator.getPriority()).thenReturn(100);
        when(mockValidator.continueOnFatalError()).thenReturn(true);

        // 3. 添加验证器
        testParser.addValidator(mockValidator);

        // 4. 执行解析并验证抛出异常
        assertThrows(ParseException.class, () -> {
            testParser.parse("test content".getBytes(), "test.txt");
        }, "严格模式下存在致命错误应抛出异常");
    }

    @Test
    @DisplayName("测试添加/移除监听器")
    void testAddRemoveParseListener() {
        // 1. 添加监听器
        testParser.addParseListener(mockListener);

        // 2. 移除监听器
        testParser.removeParseListener(mockListener);

        // 3. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 4. 验证监听器未被调用
        verify(mockListener, never()).parseStarted(any());
    }

    @Test
    @DisplayName("测试添加/移除验证器")
    void testAddRemoveValidator() {
        // 1. 添加验证器
        when(mockValidator.getName()).thenReturn("TestValidator");
        when(mockValidator.getPriority()).thenReturn(100);
        testParser.addValidator(mockValidator);

        // 2. 移除验证器
        testParser.removeValidator(mockValidator);

        // 3. 执行解析
        testParser.parse("test content".getBytes(), "test.txt");

        // 4. 验证验证器未被调用
        verify(mockValidator, never()).validate(any());
    }

    @Test
    @DisplayName("测试空配置设置")
    void testSetNullConfiguration() {
        // 设置空配置不应抛出异常
        testParser.setConfiguration(null);

        // 验证配置仍为默认配置
        assertNotNull(testParser.getConfiguration(), "配置不应为空");
    }

    @Test
    @DisplayName("测试空监听器添加")
    void testAddNullListener() {
        // 添加空监听器不应抛出异常
        testParser.addParseListener(null);
    }

    @Test
    @DisplayName("测试空验证器添加")
    void testAddNullValidator() {
        // 添加空验证器不应抛出异常
        testParser.addValidator(null);
    }
}
