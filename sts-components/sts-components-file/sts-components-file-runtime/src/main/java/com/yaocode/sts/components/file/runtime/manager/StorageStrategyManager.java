package com.yaocode.sts.components.file.runtime.manager;

import com.yaocode.sts.components.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.components.file.core.strategy.StorageStrategy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储策略工厂
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class StorageStrategyManager {

    private final List<StorageStrategy> strategies = new ArrayList<>();

    private final Map<StrategyTypeEnums, StorageStrategy> strategyMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        for (StorageStrategy strategy : strategies) {
            strategyMap.put(strategy.getStrategy(), strategy);
            log.info("注册存储策略: {}", strategy.getStrategy());
        }
    }

    /**
     * 获取策略
     */
    public StorageStrategy getStrategy(String strategyName) {
        StrategyTypeEnums strategyTypeEnums = StrategyTypeEnums.fromCode(strategyName);
        return getStrategy(strategyTypeEnums);
    }

    /**
     * 获取策略
     */
    public StorageStrategy getStrategy(StrategyTypeEnums strategyTypeEnums) {
        StorageStrategy strategy = strategyMap.get(strategyTypeEnums);
        if (strategy == null) {
            // 默认策略
            return getDefaultStrategy();
        }
        return strategy;
    }

    /**
     * 获取默认策略
     */
    public StorageStrategy getDefaultStrategy() {
        // 按优先级排序，取第一个
        return strategies.stream()
                .min(Comparator.comparingInt(StorageStrategy::getPriority))
                .orElse(null);
    }

    /**
     * 获取所有策略
     */
    public List<StorageStrategy> getAllStrategies() {
        return strategies;
    }
}
