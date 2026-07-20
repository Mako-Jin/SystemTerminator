package com.yaocode.sts.components.flow.core.engine.parser.xml;

import com.yaocode.sts.components.flow.core.engine.parser.enums.ParseStrategyEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * XML 解析器工厂
 *
 * <p>参考 Tomcat Digester 的设计思想，提供：
 * <ul>
 *   <li>解析器缓存</li>
 *   <li>根据策略创建解析器</li>
 *   <li>根据文件大小自动选择解析器</li>
 *   <li>自定义解析器注册</li>
 * </ul>
 *
 * <p>使用示例：
 * <pre>
 * // 获取 DOM 解析器
 * XmlParser parser = XmlParserFactory.getParser(ParseStrategy.DOM);
 *
 * // 根据文件自动选择
 * XmlParser parser = XmlParserFactory.getParserForFile(new File("large.bpmn"));
 *
 * // 注册自定义解析器
 * XmlParserFactory.registerParser(ParseStrategy.SAX, new CustomSaxParser());
 * </pre>
 *
 * @author Process Engine Team
 */
@Slf4j
public class XmlParserFactory {

    /**
     * 解析器缓存
     */
    private static final Map<ParseStrategyEnums, XmlParser> PARSER_CACHE = new ConcurrentHashMap<>();

    /**
     * 默认 DOM 阈值（1MB）
     */
    private static final int DEFAULT_DOM_THRESHOLD = 1024 * 1024;

    /**
     * 获取 XML 解析器
     *
     * @param strategy 解析策略
     * @return XML 解析器
     */
    public static XmlParser getParser(ParseStrategyEnums strategy) {
        return PARSER_CACHE.computeIfAbsent(strategy, s -> {
            XmlParser parser = switch (s) {
                case DOM -> new DomXmlParser();
                case SAX -> new SaxXmlParser();
                case HYBRID -> new HybridXmlParser();
                default -> throw new IllegalArgumentException("不支持的解析策略: " + strategy);
            };
            log.debug("创建 XML 解析器: {} -> {}", strategy, parser.getClass().getSimpleName());
            return parser;
        });
    }

    /**
     * 根据文件大小自动选择解析器
     *
     * @param file XML 文件
     * @return XML 解析器
     */
    public static XmlParser getParserForFile(File file) {
        if (file == null) {
            return getParser(ParseStrategyEnums.DOM);
        }

        long size = file.length();

        // 大于阈值使用 SAX（内存友好）
        if (size > DEFAULT_DOM_THRESHOLD) {
            log.debug("文件较大 ({} MB)，使用 SAX 解析", size / (1024 * 1024));
            return getParser(ParseStrategyEnums.SAX);
        }

        // 小文件使用 DOM（支持随机访问）
        log.debug("文件较小 ({} KB)，使用 DOM 解析", size / 1024);
        return getParser(ParseStrategyEnums.DOM);
    }

    /**
     * 根据输入流内容自动选择解析器
     *
     * @param inputStream XML 输入流
     * @param systemId    系统标识符
     * @return XML 解析器
     */
    public static XmlParser getParserForStream(InputStream inputStream, String systemId) {
        // 尝试读取前几个字节判断文件大小
        try {
            if (!inputStream.markSupported()) {
                return getParser(ParseStrategyEnums.DOM);
            }

            inputStream.mark(DEFAULT_DOM_THRESHOLD + 1);
            byte[] buffer = new byte[DEFAULT_DOM_THRESHOLD + 1];
            int bytesRead = inputStream.read(buffer);
            inputStream.reset();

            if (bytesRead > DEFAULT_DOM_THRESHOLD) {
                log.debug("流较大 ({} bytes)，使用 SAX 解析: {}", bytesRead, systemId);
                return getParser(ParseStrategyEnums.SAX);
            }
        } catch (Exception e) {
            log.warn("检测流大小失败，使用 DOM 解析: {}", systemId, e);
        }

        return getParser(ParseStrategyEnums.DOM);
    }

    /**
     * 根据输入流内容自动选择解析器
     */
    public static XmlParser getParserForContent(InputStream inputStream) {
        // 可以读取前几个字节判断是否需要流式解析
        // 默认使用 DOM
        return getParserForStream(inputStream, null);
    }

    /**
     * 注册自定义解析器
     *
     * @param strategy 解析策略
     * @param parser   XML 解析器
     */
    public static void registerParser(ParseStrategyEnums strategy, XmlParser parser) {
        if (parser == null) {
            throw new IllegalArgumentException("解析器不能为空");
        }
        PARSER_CACHE.put(strategy, parser);
        log.info("注册 XML 解析器: {} -> {}", strategy, parser.getClass().getSimpleName());
    }

    /**
     * 注销解析器
     *
     * @param strategy 解析策略
     * @return 被移除的解析器
     */
    public static XmlParser unregisterParser(ParseStrategyEnums strategy) {
        XmlParser removed = PARSER_CACHE.remove(strategy);
        if (removed != null) {
            log.info("注销 XML 解析器: {}", strategy);
        }
        return removed;
    }

    /**
     * 获取所有已注册的解析器
     *
     * @return 解析器映射
     */
    public static Map<ParseStrategyEnums, XmlParser> getAllParsers() {
        return new ConcurrentHashMap<>(PARSER_CACHE);
    }

    /**
     * 清空解析器缓存
     */
    public static void clearCache() {
        PARSER_CACHE.clear();
        log.debug("清空 XML 解析器缓存");
    }

    /**
     * 获取解析器数量
     */
    public static int getParserCount() {
        return PARSER_CACHE.size();
    }

    /**
     * 获取默认 DOM 阈值
     */
    public static int getDefaultDomThreshold() {
        return DEFAULT_DOM_THRESHOLD;
    }
}
