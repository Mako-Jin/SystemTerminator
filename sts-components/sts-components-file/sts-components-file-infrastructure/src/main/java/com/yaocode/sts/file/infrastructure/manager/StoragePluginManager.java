package com.yaocode.sts.file.infrastructure.manager;

import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.spi.StoragePlugin;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 存储插件管理器（线程安全）
 *
 * @author yaocode
 * @since 1.0.0
 */
@Slf4j
@Component
public class StoragePluginManager {

    /**
     * 存储类型 → 插件映射
     * ConcurrentHashMap 保证读写的线程安全
     */
    private final Map<StorageTypeEnums, StoragePlugin> pluginMap = new ConcurrentHashMap<>();

    /**
     * 插件列表（用于遍历）
     * CopyOnWriteArrayList 保证遍历时的线程安全
     */
    private final CopyOnWriteArrayList<StoragePlugin> pluginList = new CopyOnWriteArrayList<>();

    /**
     * 加载锁，防止并发加载
     */
    private final Object loadLock = new Object();

    /**
     * 是否已初始化（使用 AtomicBoolean 保证可见性）
     */
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @PostConstruct
    public void loadPlugins() {
        // 使用 CAS 防止重复加载
        if (!initialized.compareAndSet(false, true)) {
            log.debug("插件已加载，跳过重复加载");
            return;
        }

        synchronized (loadLock) {
            log.info("开始加载存储插件...");
            ServiceLoader<StoragePlugin> loader = ServiceLoader.load(StoragePlugin.class);

            int loadedCount = 0;
            for (StoragePlugin plugin : loader) {
                StorageTypeEnums storageType = plugin.getStorageType();
                if (storageType == null) {
                    log.warn("插件 {} 的存储类型为空，跳过加载", plugin.getPluginName());
                    continue;
                }

                // putIfAbsent 是原子操作
                StoragePlugin existing = pluginMap.putIfAbsent(storageType, plugin);
                if (existing != null) {
                    log.warn("存储类型 {} 已有插件 {}，跳过加载 {}",
                            storageType, existing.getPluginName(), plugin.getPluginName());
                    continue;
                }

                pluginList.add(plugin);
                loadedCount++;
                log.info("加载存储插件: {} ({})", plugin.getPluginName(), storageType);
            }

            log.info("存储插件加载完成，共加载 {} 个插件", loadedCount);
            if (pluginMap.isEmpty()) {
                log.warn("未加载到任何存储插件");
            }
        }
    }

    /**
     * 获取存储插件（线程安全，ConcurrentHashMap 保证）
     */
    public StoragePlugin getPlugin(StorageTypeEnums storageType) {
        return storageType == null ? null : pluginMap.get(storageType);
    }

    public StoragePlugin getPlugin(String storageType) {
        if (storageType == null) {
            return null;
        }
        StorageTypeEnums typeEnum = StorageTypeEnums.fromType(storageType);
        return typeEnum != null ? pluginMap.get(typeEnum) : null;
    }

    public Map<StorageTypeEnums, StoragePlugin> getAllPlugins() {
        return new ConcurrentHashMap<>(pluginMap);
    }

    public CopyOnWriteArrayList<StoragePlugin> getPluginList() {
        return new CopyOnWriteArrayList<>(pluginList);
    }

    public boolean isSupported(StorageTypeEnums storageType) {
        return storageType != null && pluginMap.containsKey(storageType);
    }

    /**
     * 动态注册插件（线程安全）
     */
    public boolean registerPlugin(StoragePlugin plugin) {
        if (plugin == null) {
            return false;
        }

        StorageTypeEnums storageType = plugin.getStorageType();
        if (storageType == null) {
            log.warn("插件 {} 的存储类型为空，无法注册", plugin.getPluginName());
            return false;
        }

        // putIfAbsent 是原子操作，保证线程安全
        StoragePlugin existing = pluginMap.putIfAbsent(storageType, plugin);
        if (existing != null) {
            log.warn("存储类型 {} 已有插件 {}，拒绝注册 {}",
                    storageType, existing.getPluginName(), plugin.getPluginName());
            return false;
        }

        pluginList.add(plugin);
        log.info("动态注册存储插件: {} ({})", plugin.getPluginName(), storageType);
        return true;
    }

    /**
     * 动态卸载插件（线程安全）
     */
    public boolean unregisterPlugin(StorageTypeEnums storageType) {
        if (storageType == null) {
            return false;
        }

        StoragePlugin removed = pluginMap.remove(storageType);
        if (removed != null) {
            pluginList.remove(removed);
            log.info("动态卸载存储插件: {} ({})", removed.getPluginName(), storageType);
            return true;
        }
        return false;
    }

    /**
     * 重新加载插件
     */
    public void reloadPlugins() {
        synchronized (loadLock) {
            // 清空
            pluginMap.clear();
            pluginList.clear();
            initialized.set(false);
            // 重新加载
            loadPlugins();
            log.info("存储插件重新加载完成");
        }
    }
}