package com.yaocode.sts.file.plugins.local;

/**
 * 本地存储插件常量
 * <p>
 * 集中管理本地存储插件中的魔法值
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface LocalStorageConstants {

    // ==================== 默认值 ====================

    /** 默认租户ID */
    String DEFAULT_TENANT_ID = "default";

    /** 默认业务分类 */
    String DEFAULT_BUCKET = "default";

    /** 分片存储目录名 */
    String CHUNKS_DIR = "chunks";

    /** 分片文件名模板 */
    String CHUNK_FILE_NAME_TEMPLATE = "chunk_%d.part";

    /** 重名文件时间戳后缀格式 */
    String RENAME_TIMESTAMP_FORMAT = "_HHmmss";

    String DATE_FORMATTER_PATTERN = "yyyyMMdd";

    /** 提取分片序号的正则（移除非数字字符） */
    String CHUNK_NUMBER_REGEX = "\\D";

    // ==================== 缓冲区 ====================

    /** 文件操作缓冲区大小（字节） */
    int BUFFER_SIZE = 8192;

    // ==================== 配置前缀 ====================

    /** Spring 配置前缀 */
    String CONFIG_PREFIX = "yaocode.storage.local";

}

