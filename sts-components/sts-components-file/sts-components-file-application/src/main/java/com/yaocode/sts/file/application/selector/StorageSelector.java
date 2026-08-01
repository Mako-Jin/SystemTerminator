package com.yaocode.sts.file.application.selector;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.model.StorageSelectionContext;
import com.yaocode.sts.file.core.spi.StorageStrategy;
import com.yaocode.sts.file.infrastructure.config.FileStorageConfig;
import com.yaocode.sts.file.infrastructure.manager.StorageStrategyManager;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 存储选择器
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class StorageSelector {

    @Resource
    private StorageStrategyManager storageStrategyManager;

    @Resource
    private FileStorageConfig fileStorageConfig;

    /**
     * 选择存储类型
     * <p>
     * 优先级：参数 > 策略 > 配置 > 默认
     * </p>
     *
     * @param context 选择上下文
     * @return 存储类型枚举
     */
    public StorageTypeEnums selectStorage(StorageSelectionContext context) {
        // 1. 参数指定（最高优先级）
        StorageTypeEnums specifiedStorage = StorageTypeEnums.fromCode(context.getSpecifiedStorage());
        if (specifiedStorage != null) {
            log.debug("使用用户指定的存储类型: {}", specifiedStorage.getType());
            return specifiedStorage;
        }

        // 2. 策略选择
        if (StringUtils.hasText(context.getStrategy())) {
            StorageStrategy strategy = storageStrategyManager.getStrategy(context.getStrategy());
            if (strategy != null) {
                StorageTypeEnums result = strategy.selectStorage(context);
                if (result != null) {
                    log.debug("使用策略 '{}' 选择存储: {}", context.getStrategy(), result.getType());
                    return result;
                }
            }
        }

        // 3. 使用默认策略（auto）
        StorageStrategy defaultStrategy = storageStrategyManager.getDefaultStrategy();
        if (defaultStrategy != null) {
            StorageTypeEnums result = defaultStrategy.selectStorage(context);
            if (result != null) {
                log.debug("使用默认策略选择存储: {}", result.getType());
                return result;
            }
        }

        // 4. 使用配置的默认存储类型
        StorageTypeEnums defaultType = StorageTypeEnums.fromType(fileStorageConfig.getStorage().getDefaultType());
        if (defaultType != null) {
            log.debug("使用配置的默认存储类型: {}", defaultType.getType());
            return defaultType;
        }

        // 5. 硬编码默认值
        log.debug("使用硬编码默认存储类型: LOCAL");
        return StorageTypeEnums.LOCAL;
    }
}