package com.yaocode.sts.flow.core.engine.parser;

import com.yaocode.sts.flow.core.engine.parser.api.ProcessDefinitionParser;
import com.yaocode.sts.flow.core.engine.parser.enums.ErrorSeverityEnums;
import com.yaocode.sts.flow.core.engine.parser.enums.ParseStatusEnums;
import com.yaocode.sts.flow.core.engine.parser.error.ParseError;
import com.yaocode.sts.flow.core.exception.ParseException;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Pattern;

/**
 * 默认解析服务实现
 *
 * <p>特性：
 * <ul>
 *   <li>支持多种格式的流程定义解析</li>
 *   <li>解析器缓存，提高性能</li>
 *   <li>支持运行时动态注册/注销解析器</li>
 *   <li>支持批量解析</li>
 *   <li>线程安全</li>
 * </ul>
 *
 * @author Process Engine Team
 */
@Slf4j
public class StandardParserService implements ParserService {

    /**
     * 已注册的解析器列表（线程安全）
     */
    private final List<ProcessDefinitionParser> parsers = new CopyOnWriteArrayList<>();

    /**
     * 解析器缓存（格式 -> 解析器）
     */
    private final Map<String, ProcessDefinitionParser> parserCache = new ConcurrentHashMap<>();

    /**
     * 解析器缓存 - 使用Caffeine实现
     */
    // 还没引入依赖，暂不替换
//    private final Cache<String, ProcessDefinitionParser> parserCache = Caffeine.newBuilder()
//            .maximumSize(100)
//            .expireAfterAccess(30, TimeUnit.MINUTES)
//            .expireAfterWrite(60, TimeUnit.MINUTES)
//            .recordStats()
//            .removalListener(new RemovalListener<String, ProcessDefinitionParser>() {
//                @Override
//                public void onRemoval(String key, ProcessDefinitionParser value, RemovalCause cause) {
//                    log.debug("解析器缓存移除: key={}, cause={}", key, cause);
//                }
//            })
//            .build();

    /**
     * 解析配置
     */
    private volatile ParserConfiguration configuration;

    /**
     * 统计信息
     */
    private final ParserStatistics statistics = new ParserStatistics();


    /**
     * 构造方法
     *
     * @param parsers       初始解析器列表
     * @param configuration 解析配置
     */
    public StandardParserService(List<ProcessDefinitionParser> parsers, ParserConfiguration configuration) {
        this.parsers.addAll(parsers);
        this.configuration = configuration;
        log.info("默认解析服务初始化完成，已注册 {} 个解析器", parsers.size());
        parsers.forEach(p -> log.info("  - {} (支持: {})", p.getParserName(), p.getSupportedFormats()));
    }

    /**
     * 空构造方法（用于延迟初始化）
     */
    public StandardParserService() {
        this.configuration = ParserConfiguration.defaultConfig();
    }

    /**
     * 文件名验证：禁止路径遍历字符，允许中文字符和多级扩展名
     */
    private static final Pattern SAFE_FILENAME_PATTERN =
            Pattern.compile("^[^/\\:*?\"<>|\\s]+$");

    private void validateResourceName(String resourceName) throws ParseException {
        if (resourceName == null || resourceName.isEmpty()) {
            throw new ParseException("资源名称不能为空");
        }

        // 检查路径遍历攻击
        if (resourceName.contains("..") || resourceName.contains("/") ||
                resourceName.contains("\\")) {
            throw new ParseException("非法的资源名称: " + resourceName);
        }

        // 提取纯文件名（去除路径）后验证
        String fileName = resourceName;
        int lastSeparator = Math.max(resourceName.lastIndexOf('/'), resourceName.lastIndexOf('\\'));
        if (lastSeparator >= 0) {
            fileName = resourceName.substring(lastSeparator + 1);
        }

        if (!SAFE_FILENAME_PATTERN.matcher(fileName).matches()) {
            throw new ParseException("资源名称格式不正确: " + resourceName);
        }
    }

    // ==================== 核心解析方法 ====================

    @Override
    public ParseResult parse(byte[] content, String resourceName) throws ParseException {
        long startTime = System.currentTimeMillis();
        try {
            ProcessDefinitionParser parser = getParser(resourceName);
            ParseResult result = parser.parse(content, resourceName);
            statistics.recordParse(resourceName, true, System.currentTimeMillis() - startTime);
            return result;
        } catch (ParseException e) {
            statistics.recordParse(resourceName, false, System.currentTimeMillis() - startTime);
            throw e;
        }
    }

    @Override
    public ParseResult parse(InputStream inputStream, String resourceName) throws ParseException {
        long startTime = System.currentTimeMillis();
        try {
            ProcessDefinitionParser parser = getParser(resourceName);
            ParseResult result = parser.parse(inputStream, resourceName);
            statistics.recordParse(resourceName, true, System.currentTimeMillis() - startTime);
            return result;
        } catch (ParseException e) {
            statistics.recordParse(resourceName, false, System.currentTimeMillis() - startTime);
            throw e;
        }
    }

    @Override
    public List<ParseResult> parseBatch(Map<String, byte[]> files) {
        List<ParseResult> results = new ArrayList<>();
        for (Map.Entry<String, byte[]> entry : files.entrySet()) {
            try {
                results.add(parse(entry.getValue(), entry.getKey()));
            } catch (ParseException e) {
                log.error("批量解析失败: {}", entry.getKey(), e);
                results.add(ParseResult.builder()
                        .success(false)
                        .status(ParseStatusEnums.FAILED)
                        .format(getFormatFromName(entry.getKey()))
                        .error(ParseError.builder()
                                .message(e.getMessage())
                                .severity(ErrorSeverityEnums.FATAL)
                                .cause(e)
                                .build())
                        .build());
            }
        }
        return results;
    }

    // ==================== 解析器管理 ====================

    @Override
    public ProcessDefinitionParser getParser(String resourceName) throws ParseException {
        validateResourceName(resourceName);

        // 规范化缓存key：提取扩展名并转小写
        String cacheKey = extractExtension(resourceName).toLowerCase();

        // 从缓存获取
        ProcessDefinitionParser cached = parserCache.get(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 遍历查找
        for (ProcessDefinitionParser parser : parsers) {
            if (parser.supports(resourceName)) {
                // 缓存所有支持的格式扩展名
                for (String format : parser.getSupportedFormats()) {
                    parserCache.put(format.toLowerCase(), parser);
                }
                log.debug("为资源 {} 选择解析器: {}", resourceName, parser.getParserName());
                return parser;
            }
        }

        throw new ParseException(String.format("不支持的文件格式: %s，支持格式: %s",
                resourceName, getSupportedFormats()));
    }

    @Override
    public List<ProcessDefinitionParser> getAllParsers() {
        return new ArrayList<>(parsers);
    }

    @Override
    public List<String> getSupportedFormats() {
        return parsers.stream()
                .flatMap(p -> p.getSupportedFormats().stream())
                .distinct()
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public boolean registerParser(ProcessDefinitionParser parser) {
        if (parser == null) {
            return false;
        }

        // 检查是否已存在同名解析器
        for (ProcessDefinitionParser existing : parsers) {
            if (existing.getParserName().equals(parser.getParserName())) {
                log.warn("解析器已存在: {}", parser.getParserName());
                return false;
            }
        }

        boolean added = parsers.add(parser);
        log.info("动态注册解析器: {}", parser.getParserName());
        // 清除缓存，因为新解析器可能支持已有格式
        parserCache.clear();
        statistics.recordRegistration(parser.getParserName());
        return added;
    }

    @Override
    public boolean unregisterParser(String parserName) {
        if (parserName == null || parserName.isEmpty()) {
            return false;
        }

        boolean removed = parsers.removeIf(p -> p.getParserName().equals(parserName));
        if (removed) {
            log.info("注销解析器: {}", parserName);
            // 清除缓存
            parserCache.clear();
            statistics.recordUnregistration(parserName);
        }
        return removed;
    }

    // ==================== 工具方法 ====================

    @Override
    public boolean supports(String resourceName) {
        try {
            getParser(resourceName);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public Map<String, Object> getStatistics() {
        return statistics.toMap();
    }

    @Override
    public ParserConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public void updateConfiguration(ParserConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("配置不能为空");
        }
        this.configuration = configuration;
        log.info("更新解析配置: {}", configuration);
        // 通知所有解析器更新配置
        for (ProcessDefinitionParser parser : parsers) {
            if (parser instanceof ConfigurableParser) {
                ((ConfigurableParser) parser).setConfiguration(configuration);
            }
        }
    }

    // ==================== 辅助方法 ====================

    /**
     * 从文件名获取格式扩展名（带点前缀，如 .bpmn）
     */
    private String extractExtension(String resourceName) {
        if (resourceName == null) {
            return "";
        }
        int lastDot = resourceName.lastIndexOf('.');
        if (lastDot > 0 && lastDot < resourceName.length() - 1) {
            return resourceName.substring(lastDot);
        }
        return "";
    }

    /**
     * 从文件名获取格式
     */
    private String getFormatFromName(String resourceName) {
        if (resourceName == null) {
            return "unknown";
        }
        int lastDot = resourceName.lastIndexOf('.');
        if (lastDot > 0) {
            return resourceName.substring(lastDot + 1).toLowerCase();
        }
        return "unknown";
    }

    // ==================== 内部统计类 ====================

    private static class ParserStatistics {
        private final Map<String, LongAdder> parseCount = new ConcurrentHashMap<>();
        private final Map<String, LongAdder> successCount = new ConcurrentHashMap<>();
        private final Map<String, LongAdder> failureCount = new ConcurrentHashMap<>();
        private final Map<String, LongAdder> totalTime = new ConcurrentHashMap<>();
        private final List<String> registeredParsers = new CopyOnWriteArrayList<>();

        void recordParse(String resourceName, boolean success, long time) {
            String key = getFormatFromName(resourceName);
            parseCount.computeIfAbsent(key, k -> new LongAdder()).increment();
            if (success) {
                successCount.computeIfAbsent(key, k -> new LongAdder()).increment();
            } else {
                failureCount.computeIfAbsent(key, k -> new LongAdder()).increment();
            }
            totalTime.computeIfAbsent(key, k -> new LongAdder()).add(time);
        }

        void recordRegistration(String parserName) {
            registeredParsers.add(parserName);
        }

        void recordUnregistration(String parserName) {
            registeredParsers.remove(parserName);
        }

        Map<String, Object> toMap() {
            Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("registeredParsers", new ArrayList<>(registeredParsers));
            stats.put("totalParsers", registeredParsers.size());

            Map<String, Object> formatStats = new java.util.HashMap<>();
            for (String format : parseCount.keySet()) {
                long total = parseCount.get(format).sum();
                long success = successCount.getOrDefault(format, new LongAdder()).sum();
                long failure = failureCount.getOrDefault(format, new LongAdder()).sum();
                long avgTime = total > 0 ? totalTime.getOrDefault(format, new LongAdder()).sum() / total : 0;

                Map<String, Object> fStats = new java.util.HashMap<>();
                fStats.put("total", total);
                fStats.put("success", success);
                fStats.put("failure", failure);
                fStats.put("avgTime", avgTime);
                formatStats.put(format, fStats);
            }
            stats.put("formatStats", formatStats);
            return stats;
        }

        private static String getFormatFromName(String resourceName) {
            if (resourceName == null) return "unknown";
            int lastDot = resourceName.lastIndexOf('.');
            return lastDot > 0 ? resourceName.substring(lastDot + 1).toLowerCase() : "unknown";
        }
    }
}