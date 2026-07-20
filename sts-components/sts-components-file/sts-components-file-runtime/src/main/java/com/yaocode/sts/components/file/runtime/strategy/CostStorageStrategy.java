package com.yaocode.sts.components.file.runtime.strategy;

import com.yaocode.sts.components.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.components.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.components.file.core.model.StorageSelectionContext;
import com.yaocode.sts.components.file.core.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 成本策略：选择成本最低的存储
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class CostStorageStrategy implements StorageStrategy {

    @Override
    public StorageTypeEnums selectStorage(StorageSelectionContext context) {
        // 选择成本最低的存储
        Map<String, Double> costMap = context.getStorageCostMap();
        if (costMap == null || costMap.isEmpty()) {
            log.debug("存储成本映射为空，使用默认存储: LOCAL");
            return StorageTypeEnums.LOCAL;
        }

        // 找到成本最低的存储类型
        String minCostType = costMap.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        if (minCostType == null) {
            log.debug("未找到成本最低的存储，使用默认存储: LOCAL");
            return StorageTypeEnums.LOCAL;
        }

        // 转换为枚举
        StorageTypeEnums result = StorageTypeEnums.fromType(minCostType);
        if (result == null) {
            log.warn("未识别的存储类型: {}, 使用默认存储: LOCAL", minCostType);
            return StorageTypeEnums.LOCAL;
        }

        log.debug("成本策略选择存储: {} (成本: {})", result.getType(), costMap.get(minCostType));
        return result;
    }

    @Override
    public StrategyTypeEnums getStrategy() {
        return StrategyTypeEnums.COST;
    }

    @Override
    public int getPriority() {
        return 3;
    }
}