package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStrategyEnums;
import com.yaocode.sts.components.flow.core.exception.ParseException;
import org.w3c.dom.Document;

import java.io.InputStream;

/**
 * XML 解析器抽象类
 *
 * <p>定义 XML 解析的统一接口，支持多种解析策略：
 * <ul>
 *   <li>DOM 解析 - 适合小文件，支持随机访问</li>
 *   <li>SAX 解析 - 适合大文件，内存友好</li>
 *   <li>混合解析 - 根据文件大小自动选择</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * XmlParser parser = new DomXmlParser();
 * Document doc = parser.parse(inputStream, "process.bpmn");
 *
 * // 或使用工厂
 * XmlParser parser = XmlParserFactory.getParser(ParseStrategy.DOM);
 * </pre>
 *
 * @author Process Engine Team
 * TODO DomXmlParser 将整个文档加载到内存，大文件可能导致内存溢出。缺乏流式处理。
 */
public interface XmlParser {

    /**
     * 解析 XML 输入流
     *
     * @param inputStream XML 输入流
     * @param systemId    系统标识符（可选，用于错误定位）
     * @return 解析后的 Document 对象（SAX 模式可能返回 null）
     * @throws ParseException 解析异常
     */
    Document parse(InputStream inputStream, String systemId) throws ParseException;

    /**
     * 解析 XML 输入流（简化版本）
     *
     * @param inputStream XML 输入流
     * @return 解析后的 Document 对象
     * @throws ParseException 解析异常
     */
    Document parse(InputStream inputStream) throws ParseException;

    /**
     * 获取解析器类型
     *
     * @return 解析策略类型
     */
    ParseStrategyEnums getStrategy();

    /**
     * 获取解析器名称
     *
     * @return 解析器名称
     */
    String getParserName();

    /**
     * 是否支持命名空间
     *
     * @return true 支持，false 不支持
     */
    boolean isNamespaceAware();

    /**
     * 是否验证 DTD
     *
     * @return true 验证，false 不验证
     */
    boolean isValidating();

    /**
     * 获取解析器描述
     *
     * @return 描述信息
     */
    String getDescription();

}
