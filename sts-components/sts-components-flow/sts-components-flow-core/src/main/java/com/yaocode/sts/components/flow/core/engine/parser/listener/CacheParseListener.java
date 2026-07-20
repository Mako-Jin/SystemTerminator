package com.yaocode.sts.components.flow.core.engine.parser.listener;

import com.yaocode.sts.components.flow.core.engine.parser.ParseContext;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存监听器
 *
 * <p>缓存解析结果，避免重复解析相同文件
 *
 * @author Process Engine Team
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class CacheParseListener extends AbstractParseListener {

    /**
     * 缓存映射（资源名 -> 解析结果）
     */
    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    /**
     * 缓存命中次数
     */
    private long hitCount = 0;

    /**
     * 缓存未命中次数
     */
    private long missCount = 0;

    /**
     * 当前解析的资源名
     */
    private String currentResource;

    /**
     * 是否使用缓存
     */
    private boolean cacheEnabled = true;

    public CacheParseListener() {
        super("CacheParseListener");
    }

    @Override
    public void parseStarted(ParseContext context) {
        // 从上下文中获取资源名
        currentResource = (String) context.getExtension("resourceName");

        if (currentResource != null && cacheEnabled) {
            // 检查缓存
            Object cached = cache.get(currentResource);
            if (cached != null) {
                hitCount++;
                log.info("缓存命中: {}", currentResource);
                // 标记为缓存命中，后续会跳过解析
                context.putExtension("cacheHit", true);
                context.putExtension("cachedResult", cached);
            } else {
                missCount++;
                log.debug("缓存未命中: {}", currentResource);
            }
        }
    }

    @Override
    public void parseCompleted(ParseContext context, Object result) {
        // 如果缓存未命中且解析成功，存入缓存
        Boolean cacheHit = context.getExtension("cacheHit", Boolean.class);
        if (cacheHit == null || !cacheHit) {
            if (currentResource != null && result != null && cacheEnabled) {
                cache.put(currentResource, result);
                log.debug("缓存保存: {}", currentResource);
            }
        }
    }

    @Override
    public void parseFailed(ParseContext context, Throwable error) {
        // 解析失败时移除缓存
        if (currentResource != null) {
            cache.remove(currentResource);
            log.debug("移除缓存: {}", currentResource);
        }
    }

    /**
     * 获取缓存
     */
    public Object getCached(String resourceName) {
        return cache.get(resourceName);
    }

    /**
     * 放入缓存
     */
    public void putCache(String resourceName, Object result) {
        if (resourceName != null && result != null) {
            cache.put(resourceName, result);
            log.debug("手动缓存: {}", resourceName);
        }
    }

    /**
     * 移除缓存
     */
    public Object removeCache(String resourceName) {
        if (resourceName != null) {
            return cache.remove(resourceName);
        }
        return null;
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        cache.clear();
        log.debug("清空缓存");
    }

    /**
     * 获取缓存大小
     */
    public int getCacheSize() {
        return cache.size();
    }

    /**
     * 获取缓存命中率
     */
    public double getHitRate() {
        long total = hitCount + missCount;
        return total > 0 ? (double) hitCount / total : 0.0;
    }

    @Override
    public String getListenerName() {
        return "CacheParseListener";
    }
}
