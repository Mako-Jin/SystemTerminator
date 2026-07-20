package com.yaocode.sts.file.runtime.manager;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储插件管理器
 * <p>
 * 通过SPI机制加载所有存储插件
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class StoragePluginManager {

    private final Map<StorageTypeEnums, StoragePlugin> pluginMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void loadPlugins() {
        ServiceLoader<StoragePlugin> loader = ServiceLoader.load(StoragePlugin.class);
        for (StoragePlugin plugin : loader) {
            pluginMap.put(plugin.getStorageType(), plugin);
            log.info("加载存储插件: {} ({})", plugin.getPluginName(), plugin.getStorageType());
        }

        if (pluginMap.isEmpty()) {
            log.warn("未加载到任何存储插件，请确保有插件在classpath中");
        }
    }

    /**
     * 获取存储插件
     *
     * @param storageType 存储类型
     * @return 存储插件，如果不存在返回null
     */
    public StoragePlugin getPlugin(StorageTypeEnums storageType) {
        return pluginMap.get(storageType);
    }

    /**
     * 获取所有存储插件
     */
    public Map<StorageTypeEnums, StoragePlugin> getAllPlugins() {
        return new ConcurrentHashMap<>(pluginMap);
    }

    /**
     * 检查存储类型是否支持
     */
    public boolean isSupported(StorageTypeEnums storageType) {
        return pluginMap.containsKey(storageType);
    }
}
