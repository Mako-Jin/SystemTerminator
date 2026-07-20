package com.yaocode.sts.components.flow.core.engine.parser;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 解析器配置
 *
 * <p>用于配置解析器的行为，包括：
 * <ul>
 *   <li>严格模式：是否在验证失败时抛出异常</li>
 *   <li>缓存控制：是否启用解析结果缓存</li>
 *   <li>文件大小限制：超过此大小使用流式解析</li>
 *   <li>超时控制：解析超时时间</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Data
@Builder
public class ParserConfiguration {

    /**
     * 是否启用严格模式
     *
     * <p>严格模式下，任何验证错误都会导致解析失败
     */
    @Builder.Default
    private boolean strictMode = false;

    /**
     * 是否启用缓存
     */
    @Builder.Default
    private boolean enableCache = true;

    /**
     * 最大内存解析文件大小（字节）
     *
     * <p>超过此大小的文件将使用流式解析（SAX）以减少内存占用
     */
    @Builder.Default
    private long maxMemoryParseSize = 10 * 1024 * 1024; // 10MB

    /**
     * 是否跳过验证
     */
    @Builder.Default
    private boolean skipValidation = false;

    /**
     * 解析超时时间（毫秒）
     */
    @Builder.Default
    private long parseTimeout = 30000;

    /**
     * 是否保留原始内容
     */
    @Builder.Default
    private boolean keepRawContent = false;

    /**
     * 字符编码
     */
    @Builder.Default
    private String encoding = "UTF-8";

    /**
     * 自定义属性
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();

    /**
     * 创建默认配置
     *
     * @return 默认配置
     */
    public static ParserConfiguration defaultConfig() {
        return ParserConfiguration.builder().build();
    }

    /**
     * 创建严格模式配置
     *
     * @return 严格模式配置
     */
    public static ParserConfiguration strictConfig() {
        return ParserConfiguration.builder()
                .strictMode(true)
                .skipValidation(false)
                .build();
    }

    /**
     * 创建高性能配置（跳过验证和缓存）
     *
     * @return 高性能配置
     */
    public static ParserConfiguration performanceConfig() {
        return ParserConfiguration.builder()
                .strictMode(false)
                .enableCache(false)
                .skipValidation(true)
                .build();
    }
}