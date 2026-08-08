package com.yaocode.sts.file.core.constants;

import com.yaocode.sts.common.basic.constants.SymbolConstants;

/**
 * 文件模块常量
 * <p>
 * 集中管理模块内的魔法值常量，避免散落在各处
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public interface FileConstants {

    // ==================== 缓冲区 & IO ====================

    /** 默认缓冲区大小（字节） */
    int BUFFER_SIZE = 8192;

    /** 二进制检测最大检查字节数 */
    int BINARY_CHECK_MAX_BYTES = 1024;

    // ==================== 默认值 ====================

    /** 默认文件名前缀 */
    String DEFAULT_FILE_PREFIX = "file_";

    // ==================== 时间格式 ====================

    /** 时间戳格式化模式 */
    String TIMESTAMP_FORMATTER_PATTERN = "yyyyMMddHHmmssSSS";

    // ==================== Diff 内容 ====================

    /** 文件内容完全相同提示 */
    String DIFF_CONTENT_IDENTICAL = "文件内容完全相同";

    /** Diff 新增行前缀 */
    String DIFF_ADDED_PREFIX = "+ ";

    /** Diff 删除行前缀 */
    String DIFF_DELETED_PREFIX = "- ";

    /** Diff 上下文行前缀（两个空格） */
    String DIFF_CONTEXT_PREFIX = "  ";

    /** Diff 跳过行提示模板 */
    String DIFF_SKIPPED_LINES_TEMPLATE = "  ... (%d lines skipped)\n";

    /** 二进制差异内容模板 */
    String DIFF_BINARY_TEMPLATE =
            """
                    二进制文件差异:
                      文件大小: %d -> %d bytes (变化: %d bytes)
                      差异字节数: %d
                      变更百分比: %.2f%%""";



    // ==================== 文件名相关 ====================

    /** 文件扩展名分隔符 */
    String EXTENSION_SEPARATOR = SymbolConstants.DOT;

    /** 唯一文件名分隔符 */
    String UNIQUE_NAME_SEPARATOR = SymbolConstants.UNDERSCORE;

    /** 非法字符正则（Windows/Linux 文件名非法字符） */
    String INVALID_FILE_NAME_REGEX = "[\\/:*?\"<>|]";

    /** 空白字符正则 */
    String WHITESPACE_REGEX = "\\s+";

    // ==================== 文件大小单位 ====================

    /** 1 KB = 1024 bytes */
    long ONE_KB = 1024L;

    /** 1 MB = 1024 * 1024 bytes */
    long ONE_MB = 1024L * 1024L;

    /** 1 GB = 1024 * 1024 * 1024 bytes */
    long ONE_GB = 1024L * 1024L * 1024L;

    /** 文件大小格式化 - B 模板 */
    String SIZE_FORMAT_B = "%d B";

    /** 文件大小格式化 - KB 模板 */
    String SIZE_FORMAT_KB = "%.2f KB";

    /** 文件大小格式化 - MB 模板 */
    String SIZE_FORMAT_MB = "%.2f MB";

    /** 文件大小格式化 - GB 模板 */
    String SIZE_FORMAT_GB = "%.2f GB";

}