package com.yaocode.sts.components.file.runtime.strategy;

import com.yaocode.sts.components.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.components.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.components.file.core.model.StorageSelectionContext;
import com.yaocode.sts.components.file.core.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 性能策略：选择性能最好的存储
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class PerformanceStorageStrategy implements StorageStrategy {

    @Override
    public StorageTypeEnums selectStorage(StorageSelectionContext context) {
        // 选择性能最好的存储
        Map<String, Double> performanceMap = context.getStoragePerformanceMap();
        if (performanceMap == null || performanceMap.isEmpty()) {
            log.debug("存储性能映射为空，使用默认存储: MINIO");
            return StorageTypeEnums.MINIO;
        }

        // 找到性能最好的存储类型
        String bestType = performanceMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (bestType == null) {
            log.debug("未找到性能最好的存储，使用默认存储: MINIO");
            return StorageTypeEnums.MINIO;
        }

        // 转换为枚举
        StorageTypeEnums result = StorageTypeEnums.fromType(bestType);
        if (result == null) {
            log.warn("未识别的存储类型: {}, 使用默认存储: MINIO", bestType);
            return StorageTypeEnums.MINIO;
        }

        log.debug("性能策略选择存储: {} (性能评分: {})", result.getType(), performanceMap.get(bestType));
        return result;
    }

    @Override
    public StrategyTypeEnums getStrategy() {
        return StrategyTypeEnums.PERFORMANCE;
    }

    @Override
    public int getPriority() {
        return 4;
    }
}
