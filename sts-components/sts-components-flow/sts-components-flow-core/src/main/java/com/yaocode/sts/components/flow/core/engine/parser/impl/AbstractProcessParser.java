package com.yaocode.sts.components.flow.core.engine.parser.impl;

import com.yaocode.sts.components.flow.core.engine.parser.ConfigurableParser;
import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import com.yaocode.sts.components.flow.core.engine.parser.ParseResult;
import com.yaocode.sts.components.flow.core.engine.parser.ParserConfiguration;
import com.yaocode.sts.components.flow.core.engine.parser.ValidationResult;
import com.yaocode.sts.components.flow.core.engine.parser.api.ProcessDefinitionParser;
import com.yaocode.sts.components.flow.core.engine.parser.listener.ParseListener;
import com.yaocode.sts.components.flow.core.engine.parser.api.Validator;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.components.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import com.yaocode.sts.components.flow.core.model.ProcessDefinition;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 抽象解析器基类
 *
 * <p>使用模板方法模式定义解析流程，子类只需实现具体的解析逻辑。
 *
 * <p>解析流程：
 * <ol>
 *   <li>前置处理 {@link #preProcess}</li>
 *   <li>执行解析 {@link #doParse}</li>
 *   <li>后置处理 {@link #postProcess}</li>
 *   <li>构建流程定义 {@link #buildProcessDefinition}</li>
 *   <li>执行验证 {@link #validate}</li>
 *   <li>构建结果 {@link #buildResult}</li>
 * </ol>
 *
 * @author Process Engine Team
 */
@Slf4j
public abstract class AbstractProcessParser implements ProcessDefinitionParser, ConfigurableParser {

    /**
     * 解析监听器列表
     */
    protected final List<ParseListener> listeners = new ArrayList<>();

    /**
     * 验证器列表
     */
    protected final List<Validator> validators = new ArrayList<>();

    /**
     * 解析配置
     */
    protected ParserConfiguration configuration = ParserConfiguration.defaultConfig();

    // ==================== 核心解析方法（模板方法） ====================

    @Override
    public ParseResult parse(byte[] content, String resourceName) throws ParseException {
        long startTime = System.currentTimeMillis();
        ParseContext context = createContext();

        try {
            // 1. 前置处理
            preProcess(content, resourceName, context);

            // 2. 执行解析
            Object rawResult = doParse(content, resourceName, context);

            // 3. 后置处理
            Object processedResult = postProcess(rawResult, context);

            // 4. 构建流程定义
            ProcessDefinition processDefinition = buildProcessDefinition(processedResult, context);

            // 5. 执行验证
            if (!configuration.isSkipValidation()) {
                validate(context);
            }

            // 6. 检查是否有致命错误
            if (context.hasFatalError() && configuration.isStrictMode()) {
                throw new ParseException("解析失败，存在致命错误");
            }

            // 7. 构建结果
            ParseResult result = buildResult(processDefinition, rawResult, context, startTime);

            // 8. 触发完成事件
            fireParseCompleted(context, result);

            return result;

        } catch (Exception e) {
            log.error("解析失败: {}", resourceName, e);
            context.addError(ParseError.builder()
                    .message(e.getMessage())
                    .severity(ErrorSeverityEnums.FATAL)
                    .cause(e)
                    .build());
            fireParseFailed(context, e);
            return buildErrorResult(context, startTime, e);
        }
    }

    @Override
    public ParseResult parse(InputStream inputStream, String resourceName) throws ParseException {
        long startTime = System.currentTimeMillis();
        ParseContext context = createContext();

        try {
            preProcess(inputStream, resourceName, context);
            Object rawResult = doParse(inputStream, resourceName, context);
            Object processedResult = postProcess(rawResult, context);
            ProcessDefinition processDefinition = buildProcessDefinition(processedResult, context);

            if (!configuration.isSkipValidation()) {
                validate(context);
            }

            if (context.hasFatalError() && configuration.isStrictMode()) {
                throw new ParseException("解析失败，存在致命错误");
            }

            ParseResult result = buildResult(processDefinition, rawResult, context, startTime);
            fireParseCompleted(context, result);
            return result;

        } catch (Exception e) {
            log.error("解析失败: {}", resourceName, e);
            context.addError(ParseError.builder()
                    .message(e.getMessage())
                    .severity(ErrorSeverityEnums.FATAL)
                    .cause(e)
                    .build());
            fireParseFailed(context, e);
            return buildErrorResult(context, startTime, e);
        }
    }

    // ==================== 模板方法钩子 ====================

    /**
     * 创建解析上下文
     */
    protected ParseContext createContext() {
        return new ParseContext();
    }

    /**
     * 前置处理（钩子方法）
     */
    protected void preProcess(byte[] content, String resourceName, ParseContext context) {
        fireParseStarted(context);
    }

    /**
     * 前置处理（输入流版本）
     */
    protected void preProcess(InputStream inputStream, String resourceName, ParseContext context) {
        fireParseStarted(context);
    }

    /**
     * 执行具体解析（子类实现）
     */
    protected abstract Object doParse(byte[] content, String resourceName, ParseContext context) throws ParseException;

    /**
     * 执行具体解析（输入流版本，子类实现）
     */
    protected abstract Object doParse(InputStream inputStream, String resourceName, ParseContext context) throws ParseException;

    /**
     * 后置处理（钩子方法）
     */
    protected Object postProcess(Object rawResult, ParseContext context) {
        return rawResult;
    }

    /**
     * 构建流程定义（子类实现）
     */
    protected abstract ProcessDefinition buildProcessDefinition(Object parsedObject, ParseContext context);

    /**
     * 执行验证
     */
    protected void validate(ParseContext context) {
        for (Validator validator : validators) {
            log.debug("执行验证器: {}", validator.getName());
            ValidationResult result = validator.validate(context);
            context.getErrors().addAll(result.getErrors());
            context.getWarnings().addAll(result.getWarnings());

            if (context.hasFatalError() && !validator.continueOnFatalError()) {
                break;
            }
        }
    }

    /**
     * 构建成功结果
     */
    protected ParseResult buildResult(ProcessDefinition processDefinition, Object rawResult,
                                      ParseContext context, long startTime) {
        return ParseResult.builder()
                .success(!context.hasError())
                .status(context.hasFatalError() ? ParseStatusEnums.FAILED :
                        context.hasError() ? ParseStatusEnums.PARTIAL_SUCCESS : ParseStatusEnums.COMPLETED)
                .processDefinition(processDefinition)
                .rawResult(rawResult)
                .parseTime(System.currentTimeMillis() - startTime)
                .format(getFormat())
                .errors(new ArrayList<>(context.getErrors()))
                .warnings(new ArrayList<>(context.getWarnings()))
                .extensions(context.getExtensions())
                .build();
    }

    /**
     * 构建错误结果
     */
    protected ParseResult buildErrorResult(ParseContext context, long startTime, Exception e) {
        return ParseResult.builder()
                .success(false)
                .status(ParseStatusEnums.FAILED)
                .parseTime(System.currentTimeMillis() - startTime)
                .format(getFormat())
                .errors(new ArrayList<>(context.getErrors()))
                .warnings(new ArrayList<>(context.getWarnings()))
                .build();
    }

    // ==================== 事件触发 ====================

    protected void fireParseStarted(ParseContext context) {
        listeners.forEach(l -> l.parseStarted(context));
    }

    protected void fireParseCompleted(ParseContext context, Object result) {
        listeners.forEach(l -> l.parseCompleted(context, result));
    }

    protected void fireParseFailed(ParseContext context, Throwable error) {
        listeners.forEach(l -> l.parseFailed(context, error));
    }

    // ==================== 公共方法 ====================

    @Override
    public void addParseListener(ParseListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    @Override
    public void removeParseListener(ParseListener listener) {
        if (listener != null) {
            this.listeners.remove(listener);
        }
    }

    public void addValidator(Validator validator) {
        if (validator != null) {
            this.validators.add(validator);
            this.validators.sort(Comparator.comparingInt(Validator::getPriority));
        }
    }

    public void removeValidator(Validator validator) {
        if (validator != null) {
            this.validators.remove(validator);
        }
    }

    @Override
    public void setConfiguration(ParserConfiguration configuration) {
        if (configuration != null) {
            this.configuration = configuration;
        }
    }

    @Override
    public ParserConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * 获取格式名称（子类实现）
     */
    protected abstract String getFormat();
}