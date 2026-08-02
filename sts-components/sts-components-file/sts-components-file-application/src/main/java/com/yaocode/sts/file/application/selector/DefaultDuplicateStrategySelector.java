package com.yaocode.sts.file.application.selector;

import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.model.FileExistenceContext;
import com.yaocode.sts.file.core.model.FileUploadContext;
import com.yaocode.sts.file.core.spi.DuplicateStrategy;
import com.yaocode.sts.file.core.spi.DuplicateStrategySelector;
import com.yaocode.sts.file.core.strategy.AbstractDuplicateStrategy;
import com.yaocode.sts.file.infrastructure.config.FileStorageConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
public class DefaultDuplicateStrategySelector implements DuplicateStrategySelector {

    private final CopyOnWriteArrayList<DuplicateStrategy> strategies = new CopyOnWriteArrayList<>();

    @Resource
    private FileStorageConfig fileStorageConfig;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private volatile DuplicateFileStrategyEnums fallbackStrategy;

    @PostConstruct
    public void init() {
        String defaultStrategy = fileStorageConfig != null ?
                fileStorageConfig.getUpload().getDefaultDeduplicationStrategy() : "REUSE";
        try {
            this.fallbackStrategy = DuplicateFileStrategyEnums.valueOf(defaultStrategy.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.warn("无效的默认策略: {}, 使用 REUSE", defaultStrategy);
            this.fallbackStrategy = DuplicateFileStrategyEnums.REUSE;
        }
        sortStrategies();
        log.info("策略选择器初始化完成，策略数量: {}, 默认策略: {}", strategies.size(), fallbackStrategy);
        strategies.forEach(s -> log.info("  - {} (优先级: {})", s.getName(), s.getPriority()));
    }

    private void sortStrategies() {
        List<DuplicateStrategy> sorted = strategies.stream()
                .sorted(Comparator.comparingInt(DuplicateStrategy::getPriority))
                .toList();
        strategies.clear();
        strategies.addAll(sorted);
    }

    @Override
    public DuplicateFileStrategyEnums selectStrategy(FileUploadContext context, FileExistenceContext existFile) {
        if (!Objects.equals(EnableEnums.ENABLED.getCode(), context.getEnableDeduplication())) {
            log.debug("去重未启用，跳过策略选择");
            return null;
        }
        if (existFile == null || !existFile.getExists()) {
            log.debug("文件不存在，跳过策略选择");
            return null;
        }
        if (context.getSpecifiedStrategy() != null) {
            log.info("使用用户指定策略: {}", context.getSpecifiedStrategy());
            return context.getSpecifiedStrategy();
        }

        for (DuplicateStrategy strategy : strategies) {
            try {
                if (strategy instanceof AbstractDuplicateStrategy) {
                    if (!((AbstractDuplicateStrategy) strategy).isEnabled()) {
                        continue;
                    }
                }
                if (strategy.isSupport(context, existFile)) {
                    DuplicateFileStrategyEnums result = strategy.getStrategy();
                    if (result != null) {
                        log.info("策略匹配成功: {} → {}", strategy.getName(), result);
                        return result;
                    }
                }
            } catch (Exception e) {
                log.error("策略匹配异常: {}", strategy.getName(), e);
            }
        }
        log.debug("没有匹配的策略，使用默认策略: {}", fallbackStrategy);
        return fallbackStrategy;
    }

    @Override
    public List<DuplicateStrategy> getAllStrategies() {
        return new CopyOnWriteArrayList<>(strategies);
    }

    @Override
    public void registerStrategy(DuplicateStrategy strategy) {
        if (strategy == null) {
            return;
        }
        lock.writeLock().lock();
        try {
            boolean exists = strategies.stream().anyMatch(s -> s.getName().equals(strategy.getName()));
            if (exists) {
                log.warn("策略 {} 已存在，跳过注册", strategy.getName());
                return;
            }
            strategies.add(strategy);
            sortStrategies();
            log.info("注册策略: {}", strategy.getName());
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void unregisterStrategy(DuplicateStrategy strategy) {
        if (strategy == null) {
            return;
        }
        lock.writeLock().lock();
        try {
            boolean removed = strategies.remove(strategy);
            if (removed) {
                sortStrategies();
                log.info("移除策略: {}", strategy.getName());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void enableStrategy(String strategyName) {
        lock.writeLock().lock();
        try {
            for (DuplicateStrategy strategy : strategies) {
                if (strategy.getName().equals(strategyName) && strategy instanceof AbstractDuplicateStrategy) {
                    ((AbstractDuplicateStrategy) strategy).setEnabled(true);
                    log.info("启用策略: {}", strategyName);
                    return;
                }
            }
            log.warn("未找到策略: {}", strategyName);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void disableStrategy(String strategyName) {
        lock.writeLock().lock();
        try {
            for (DuplicateStrategy strategy : strategies) {
                if (strategy.getName().equals(strategyName) && strategy instanceof AbstractDuplicateStrategy) {
                    ((AbstractDuplicateStrategy) strategy).setEnabled(false);
                    log.info("禁用策略: {}", strategyName);
                    return;
                }
            }
            log.warn("未找到策略: {}", strategyName);
        } finally {
            lock.writeLock().unlock();
        }
    }
}