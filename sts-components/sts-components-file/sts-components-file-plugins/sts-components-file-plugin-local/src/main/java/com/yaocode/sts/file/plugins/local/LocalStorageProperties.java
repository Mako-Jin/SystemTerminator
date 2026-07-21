package com.yaocode.sts.file.plugins.local;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地存储配置属性
 */
@Data
@ConfigurationProperties(prefix = "yaocode.storage.local")
public class LocalStorageProperties {

    /**
     * 是否开启
     */
    private boolean enabled = true;

    /**
     * 存储根目录
     */
    private String basePath = "./uploads";

    /**
     * 是否保留原始文件名
     */
    private boolean keepOriginalName = false;

    /**
     * 最大文件大小（MB）
     */
    private Long maxFileSize = 100L;

    /**
     * 是否启用自动清理
     */
    private boolean autoCleanEnabled = false;

    /**
     * 自动清理天数（超过该天数的文件将被清理）
     */
    private Integer autoCleanDays = 30;
}
