package com.yaocode.sts.components.flow.core.engine.parser.api;

import com.yaocode.sts.components.flow.core.engine.parser.ParseResult;
import com.yaocode.sts.components.flow.core.engine.parser.ParserConfiguration;
import com.yaocode.sts.components.flow.core.engine.parser.listener.ParseListener;
import com.yaocode.sts.components.flow.core.exception.ParseException;

import java.io.InputStream;
import java.util.List;

/**
 * 流程定义解析器接口
 *
 * <p>负责将特定格式的流程定义文件解析为统一的流程定义模型。
 *
 * <p>支持的格式：
 * <ul>
 *   <li>BPMN 2.0 XML (.bpmn, .xml, .bpmn20.xml)</li>
 *   <li>JSON (.json) - 用于 3D 流程设计器</li>
 *   <li>自定义格式（通过实现此接口扩展）</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 通过 ParserService 获取解析器
 * ProcessDefinitionParser parser = parserService.getParser("process.bpmn");
 * ParseResult result = parser.parse(content, "process.bpmn");
 *
 * // 或者直接使用具体解析器
 * BpmnProcessParser parser = new BpmnProcessParser();
 * ParseResult result = parser.parse(content, "process.bpmn");
 * </pre>
 *
 * @author Process Engine Team
 * @version 2.0.0
 */
public interface ProcessDefinitionParser {

    // ==================== 核心解析方法 ====================

    /**
     * 解析流程定义（从字节数组）
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

    // ==================== 格式支持 ====================

    /**
     * 判断是否支持该资源格式
     *
     * @param resourceName 资源名称（如：process.bpmn）
     * @return true 支持，false 不支持
     */
    boolean supports(String resourceName);

    /**
     * 获取支持的格式列表
     *
     * @return 格式扩展名列表（如：["BPMN", "XML", "JSON"]）
     */
    List<String> getSupportedFormats();

    // ==================== 解析器信息 ====================

    /**
     * 获取解析器名称
     *
     * @return 解析器名称（如："BPMN 2.0 XML Parser"）
     */
    String getParserName();

    /**
     * 获取解析器版本
     *
     * @return 版本号
     */
    default String getVersion() {
        return "1.0.0";
    }

    /**
     * 获取解析器描述
     *
     * @return 描述信息
     */
    default String getDescription() {
        return getParserName();
    }

    // ==================== 监听器管理（可选） ====================

    /**
     * 添加解析监听器
     *
     * @param listener 监听器
     */
    default void addParseListener(ParseListener listener) {
        // 默认空实现，子类可覆盖
    }

    /**
     * 移除解析监听器
     *
     * @param listener 监听器
     */
    default void removeParseListener(ParseListener listener) {
        // 默认空实现，子类可覆盖
    }

    // ==================== 配置管理（可选） ====================

    /**
     * 设置解析配置
     *
     * @param configuration 解析配置
     */
    default void setConfiguration(ParserConfiguration configuration) {
        // 默认空实现，子类可覆盖
    }

    /**
     * 获取解析配置
     *
     * @return 解析配置
     */
    default ParserConfiguration getConfiguration() {
        return ParserConfiguration.defaultConfig();
    }
}