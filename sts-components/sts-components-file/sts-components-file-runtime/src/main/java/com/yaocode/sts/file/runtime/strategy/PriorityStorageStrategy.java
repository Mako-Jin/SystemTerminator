package com.yaocode.sts.file.runtime.strategy;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.strategy.StorageStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 优先级策略：按用户指定的优先级顺序选择
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class PriorityStorageStrategy implements StorageStrategy {

    @Override
    public StorageTypeEnums selectStorage(StorageSelectionContext context) {
        // 根据用户传入的优先列表选择
        List<String> preferred = context.getPreferredStorages();
        if (preferred == null || preferred.isEmpty()) {
            log.debug("未指定优先存储列表，使用默认存储: LOCAL");
            return StorageTypeEnums.LOCAL;
        }

        // 遍历优先列表，找到第一个支持的存储类型
        for (String type : preferred) {
            StorageTypeEnums storageType = StorageTypeEnums.fromType(type);
            if (storageType != null) {
                log.debug("优先级策略选择存储: {} (优先列表第{}位)", storageType.getType(),
                        preferred.indexOf(type) + 1);
                return storageType;
            }
        }

        // 如果优先列表中的所有类型都不支持，使用默认
        log.warn("优先列表中的所有存储类型都不支持: {}, 使用默认存储: LOCAL", preferred);
        return StorageTypeEnums.LOCAL;
    }

    @Override
    public StrategyTypeEnums getStrategy() {
        return StrategyTypeEnums.PRIORITY;
    }

    @Override
    public int getPriority() {
        return 2;
    }
}