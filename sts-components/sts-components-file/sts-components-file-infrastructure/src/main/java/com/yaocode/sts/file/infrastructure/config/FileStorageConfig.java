package com.yaocode.sts.file.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件存储配置
 *
 * @author yaocode
 * @since 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "yaocode.file")
public class FileStorageConfig {

    /** 上传配置 */
    private UploadConfig upload = new UploadConfig();

    /** 存储配置 */
    private StorageConfig storage = new StorageConfig();

    /** 去重配置 */
    private DeduplicationConfig deduplication = new DeduplicationConfig();

    /** 安全配置 */
    private SecurityConfig security = new SecurityConfig();

    /** 版本配置 */
    private VersionConfig version = new VersionConfig();

    /** 性能配置 */
    private PerformanceProperties performance = new PerformanceProperties();

    @Data
    public static class UploadConfig {
        /** 分片大小（默认10MB） */
        private long chunkSize = 10 * 1024 * 1024L;

        /** 最大文件大小（默认1GB） */
        private long maxFileSize = 1024 * 1024 * 1024L;

        /** 最大并发上传数 */
        private int maxConcurrentUploads = 10;

        /** 临时文件过期时间（小时） */
        private int tempExpireHours = 24;

        /** 临时文件目录 */
        private String tempDir = "${java.io.tmpdir}/file-upload";

        /** 允许的文件扩展名 */
        private List<String> allowedExtensions = new ArrayList<>();

        /** 禁止的文件扩展名 */
        private List<String> blockedExtensions = List.of("exe", "bat", "sh", "com", "scr");

        /** 流式上传缓冲区大小 */
        private int streamBufferSize = 8192;

        /** 上传超时时间（秒） */
        private int uploadTimeout = 300;

        private boolean deduplicationEnabled = true;
        private String defaultDeduplicationStrategy = "REUSE";
    }

    @Data
    public static class StorageConfig {
        /** 默认存储类型 */
        private String defaultType = "local";

        /** 存储插件配置 */
        private Map<String, StoragePluginConfig> plugins = new HashMap<>();

        /** 存储切换阈值（超过此大小切换到对象存储） */
        private long switchThreshold = 100 * 1024 * 1024L;

        /** 是否启用存储健康检查 */
        private boolean healthCheckEnabled = true;

        /** 健康检查间隔（秒） */
        private int healthCheckInterval = 60;

        /** 存储节点选择策略 */
        private String selectionStrategy = "auto";
    }

    @Data
    public static class StoragePluginConfig {
        /** 是否启用 */
        private boolean enabled = true;

        /** 端点地址 */
        private String endpoint;

        /** 访问密钥 */
        private String accessKey;

        /** 秘密密钥 */
        private String secretKey;

        /** 存储桶/容器 */
        private String bucket;

        /** 区域 */
        private String region;

        /** 连接超时（毫秒） */
        private int connectTimeout = 5000;

        /** 读取超时（毫秒） */
        private int readTimeout = 30000;

        /** 自定义配置 */
        private Map<String, Object> extra = new HashMap<>();
    }

    @Data
    public static class DeduplicationConfig {
        /** 是否启用去重 */
        private boolean enabled = true;

        /** 默认去重策略 */
        private String defaultStrategy = "REUSE";

        /** 是否允许跨租户复用 */
        private boolean crossTenantEnabled = true;

        /** 是否允许跨业务复用 */
        private boolean crossBusinessEnabled = false;
    }

    @Data
    public static class SecurityConfig {
        /** 最大文件名长度 */
        private int maxFilenameLength = 255;

        /** 是否验证MIME类型 */
        private boolean validateMimeType = true;

        /** 允许的MIME类型（为空则全部允许） */
        private List<String> allowedMimeTypes = new ArrayList<>();

        /** 是否启用病毒扫描 */
        private boolean virusScanEnabled = false;

        /** 病毒扫描超时（秒） */
        private int virusScanTimeout = 60;

        /** 是否启用文件内容校验 */
        private boolean contentValidationEnabled = true;
    }

    @Data
    public static class VersionConfig {
        /** 最大版本数（0表示不限制） */
        private int maxVersions = 100;

        /** 是否启用版本分支 */
        private boolean branchEnabled = true;

        /** 是否自动创建版本 */
        private boolean autoCreateVersion = true;

        /** 版本保留天数（0表示永久保留） */
        private int retentionDays = 0;

        /** 是否启用版本差异计算 */
        private boolean diffEnabled = true;
    }

    @Data
    public static class PerformanceProperties {
        private int hashBufferSize = 8192;
        private int uploadBufferSize = 8192;
        private int downloadBufferSize = 8192;
        private boolean enableStreaming = true;
        private boolean enableParallel = false;
        private int parallelThreshold = 100 * 1024 * 1024;
    }
}
