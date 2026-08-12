package com.yaocode.sts.file.infrastructure.constants;

/**
 * 文件基础设施层通用常量
 * <p>
 * 集中管理基础设施层的魔法值常量
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileInfrastructureConstants {

    /** 临时文件默认目录 */
    String DEFAULT_TEMP_DIR = "./uploads/tmp";

    // ==================== 存储阈值 ====================

    /** 默认最大文件大小 (1GB) */
    long DEFAULT_MAX_FILE_SIZE = 1024L * 1024 * 1024;

    /** 默认分片大小 (10MB) */
    long DEFAULT_CHUNK_SIZE = 10L * 1024 * 1024;

    // ==================== 超时配置 ====================

    /** 默认连接超时 (毫秒) */
    int DEFAULT_CONNECT_TIMEOUT = 5000;

    /** 默认读取超时 (毫秒) */
    int DEFAULT_READ_TIMEOUT = 30000;

    /** 默认上传超时 (秒) */
    int DEFAULT_UPLOAD_TIMEOUT = 300;

    /** 默认健康检查间隔 (秒) */
    int DEFAULT_HEALTH_CHECK_INTERVAL = 60;

    /** 默认病毒扫描超时 (秒) */
    int DEFAULT_VIRUS_SCAN_TIMEOUT = 60;

    /** 默认临时文件过期时间 (小时) */
    int DEFAULT_TEMP_EXPIRE_HOURS = 24;

    // ==================== 安全配置 ====================

    /** 默认最大文件名长度 */
    int DEFAULT_MAX_FILENAME_LENGTH = 255;

    // ==================== 版本配置 ====================

    /** 默认最大版本数 */
    int DEFAULT_MAX_VERSIONS = 100;

    // ==================== 性能配置 ====================

    /** 并行处理阈值 (100MB) */
    long PARALLEL_THRESHOLD = 100L * 1024 * 1024;

}