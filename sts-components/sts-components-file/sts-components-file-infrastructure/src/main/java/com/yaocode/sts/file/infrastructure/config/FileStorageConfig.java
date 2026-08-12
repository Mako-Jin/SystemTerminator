package com.yaocode.sts.file.infrastructure.config;

import com.yaocode.sts.file.core.enums.DuplicateFileStrategyEnums;
import com.yaocode.sts.file.core.enums.StorageTypeEnums;
import com.yaocode.sts.file.core.enums.StrategyTypeEnums;
import com.yaocode.sts.file.core.constants.FileConstants;
import com.yaocode.sts.file.infrastructure.constants.FileInfrastructureConstants;
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
@ConfigurationProperties(prefix = "yaocode.storage")
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
        private long chunkSize = FileInfrastructureConstants.DEFAULT_CHUNK_SIZE;

        /** 最大文件大小（默认1GB） */
        private long maxFileSize = FileInfrastructureConstants.DEFAULT_MAX_FILE_SIZE;

        /** 最大并发上传数 */
        private int maxConcurrentUploads = 10;

        /** 临时文件过期时间（小时） */
        private int tempExpireHours = FileInfrastructureConstants.DEFAULT_TEMP_EXPIRE_HOURS;

        /** 临时文件目录 */
        private String tempDir = FileInfrastructureConstants.DEFAULT_TEMP_DIR;

        /** 允许的文件扩展名 */
        private List<String> allowedExtensions = new ArrayList<>();

        /** 禁止的文件扩展名 */
        private List<String> blockedExtensions = List.of("exe", "bat", "sh", "com", "scr");

        /** 流式上传缓冲区大小 */
        private int streamBufferSize = FileConstants.BUFFER_SIZE;

        /** 上传超时时间（秒） */
        private int uploadTimeout = FileInfrastructureConstants.DEFAULT_UPLOAD_TIMEOUT;

        private boolean deduplicationEnabled = true;
        private String defaultDeduplicationStrategy = DuplicateFileStrategyEnums.REUSE.name();
    }

    @Data
    public static class StorageConfig {
        /** 默认存储类型 */
        private String defaultType = StorageTypeEnums.LOCAL.getType();

        /** 存储插件配置 */
        private Map<String, StoragePluginConfig> plugins = new HashMap<>();

        /** 存储切换阈值（超过此大小切换到对象存储） */
        private long switchThreshold = FileConstants.LARGE_FILE_THRESHOLD;

        /** 是否启用存储健康检查 */
        private boolean healthCheckEnabled = true;

        /** 健康检查间隔（秒） */
        private int healthCheckInterval = FileInfrastructureConstants.DEFAULT_HEALTH_CHECK_INTERVAL;

        /** 存储节点选择策略 */
        private String selectionStrategy = StrategyTypeEnums.AUTO.getCode();
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
        private int connectTimeout = FileInfrastructureConstants.DEFAULT_CONNECT_TIMEOUT;

        /** 读取超时（毫秒） */
        private int readTimeout = FileInfrastructureConstants.DEFAULT_READ_TIMEOUT;

        /** 自定义配置 */
        private Map<String, Object> extra = new HashMap<>();
    }

    @Data
    public static class DeduplicationConfig {
        /** 是否启用去重 */
        private boolean enabled = true;

        /** 默认去重策略 */
        private String defaultStrategy = DuplicateFileStrategyEnums.REUSE.name();

        /** 是否允许跨租户复用 */
        private boolean crossTenantEnabled = true;

        /** 是否允许跨业务复用 */
        private boolean crossBusinessEnabled = false;
    }

    @Data
    public static class SecurityConfig {
        /** 最大文件名长度 */
        private int maxFilenameLength = FileInfrastructureConstants.DEFAULT_MAX_FILENAME_LENGTH;

        /** 是否验证MIME类型 */
        private boolean validateMimeType = true;

        /** 允许的MIME类型（为空则全部允许） */
        private List<String> allowedMimeTypes = new ArrayList<>();

        /** 是否启用病毒扫描 */
        private boolean virusScanEnabled = false;

        /** 病毒扫描超时（秒） */
        private int virusScanTimeout = FileInfrastructureConstants.DEFAULT_VIRUS_SCAN_TIMEOUT;

        /** 是否启用文件内容校验 */
        private boolean contentValidationEnabled = true;
    }

    @Data
    public static class VersionConfig {
        /** 最大版本数（0表示不限制） */
        private int maxVersions = FileInfrastructureConstants.DEFAULT_MAX_VERSIONS;

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
        private int hashBufferSize = FileConstants.BUFFER_SIZE;
        private int uploadBufferSize = FileConstants.BUFFER_SIZE;
        private int downloadBufferSize = FileConstants.BUFFER_SIZE;
        private boolean enableStreaming = true;
        private boolean enableParallel = false;
        private long parallelThreshold = FileInfrastructureConstants.PARALLEL_THRESHOLD;
    }
}