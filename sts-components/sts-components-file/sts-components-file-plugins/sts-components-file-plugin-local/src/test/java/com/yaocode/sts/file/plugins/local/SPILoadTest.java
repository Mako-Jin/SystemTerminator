package com.yaocode.sts.file.plugins.local;

import com.yaocode.sts.file.core.spi.StoragePlugin;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ServiceLoader;

public class SPILoadTest {

    @Test
    public void testSPILoad() {
        // 1. 检查配置文件是否存在
        URL url = this.getClass().getClassLoader()
                .getResource("META-INF/services/com.yaocode.sts.file.core.spi.StoragePlugin");
        System.out.println("配置文件URL: " + url);

        if (url == null) {
            System.err.println("❌ 找不到SPI配置文件！");
            System.err.println("请确保文件在: src/main/resources/META-INF/services/com.yaocode.sts.file.core.spi.StoragePlugin");
            return;
        }

        // 2. 尝试加载
        ServiceLoader<StoragePlugin> loader = ServiceLoader.load(StoragePlugin.class);
        System.out.println("ServiceLoader: " + loader);

        int count = 0;
        for (StoragePlugin plugin : loader) {
            System.out.println("✅ 找到插件: " + plugin.getPluginName() + " -> " + plugin.getClass().getName());
            count++;
        }

        if (count == 0) {
            System.err.println("❌ ServiceLoader没有找到任何插件实现");
            System.err.println("请检查实现类是否实现了 StoragePlugin 接口");
            System.err.println("实现类: com.yaocode.sts.file.plugins.local.LocalStoragePlugin");
        } else {
            System.out.println("✅ 共加载 " + count + " 个插件");
        }
    }
}