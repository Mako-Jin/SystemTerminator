package com.yaocode.sts.components.flow.core.engine.parser;

import com.yaocode.sts.components.flow.core.engine.parser.api.ProcessDefinitionParser;
import com.yaocode.sts.components.flow.core.exception.ParseException;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 流程解析服务接口
 *
 * <p>提供流程定义文件的解析能力，支持多种格式（BPMN、JSON等）。
 * <p>
 * // 解析单个文件
 * ParseResult result = parserService.parse(content, "process.bpmn");
 * <p>
 * // 批量解析
 * List&lt;ParseResult&gt; results = parserService.parseBatch(files);
 * <p>
 * // 获取特定解析器
 * ProcessDefinitionParser parser = parserService.getParser("process.json");
 * </pre>
 *
 * @author Process Engine Team
 * @version 2.0.0
 */
public interface ParserService {

    // ==================== 核心解析方法 ====================

    /**
     * 解析流程定义
     *
     * @param content      文件内容（字节数组）
     * @param resourceName 资源名称（用于识别格式和记录）
     * @return 解析结果
     * @throws ParseException 解析失败时抛出
     */
    ParseResult parse(byte[] content, String resourceName) throws ParseException;

    /**
     * 解析流程定义（从输入流）
     *
     * @param inputStream  输入流
     * @param resourceName 资源名称
     * @return 解析结果
     * @throws ParseException 解析失败时抛出
     */
    ParseResult parse(InputStream inputStream, String resourceName) throws ParseException;

    /**
     * 批量解析流程定义
     *
     * @param files 文件映射（文件名 -> 文件内容）
     * @return 解析结果列表
     */
    List<ParseResult> parseBatch(Map<String, byte[]> files);

    // ==================== 解析器管理 ====================

    /**
     * 根据资源名称获取对应的解析器
     *
     * @param resourceName 资源名称（如：process.bpmn）
     * @return 解析器实例
     * @throws ParseException 如果没有找到支持的解析器
     *
     * <p>
     * TODO 解析器选择策略可配置化
     * TODO 新增解析器选择器接口
     * TODO public interface ParserSelector {}
     */
    ProcessDefinitionParser getParser(String resourceName) throws ParseException;

    /**
     * 获取所有已注册的解析器
     *
     * @return 解析器列表
     */
    List<ProcessDefinitionParser> getAllParsers();

    /**
     * 获取所有支持的格式列表
     *
     * @return 格式列表（如：["bpmn", "xml", "json"]）
     */
    List<String> getSupportedFormats();

    /**
     * 动态注册解析器（支持运行时扩展）
     *
     * @param parser 要注册的解析器
     * @return true 注册成功，false 注册失败
     */
    boolean registerParser(ProcessDefinitionParser parser);

    /**
     * 注销解析器
     *
     * @param parserName 解析器名称
     * @return true 注销成功，false 注销失败
     */
    boolean unregisterParser(String parserName);

    // ==================== 工具方法 ====================

    /**
     * 检查是否支持该资源格式
     *
     * @param resourceName 资源名称
     * @return true 支持，false 不支持
     */
    boolean supports(String resourceName);

    /**
     * 获取解析服务统计信息
     *
     * @return 统计信息
     */
    Map<String, Object> getStatistics();

    /**
     * 获取解析配置
     *
     * @return 解析配置
     */
    ParserConfiguration getConfiguration();

    /**
     * 更新解析配置
     *
     * @param configuration 新的配置
     */
    void updateConfiguration(ParserConfiguration configuration);
}
