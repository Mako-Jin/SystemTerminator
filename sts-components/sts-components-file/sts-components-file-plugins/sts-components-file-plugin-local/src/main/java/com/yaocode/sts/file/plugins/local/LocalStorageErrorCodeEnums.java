package com.yaocode.sts.file.plugins.local;

import lombok.Getter;

/**
 * 本地存储错误码枚举
 * <p>
 * 错误码格式: 文件模块(10) + 存储插件(8) + 分类(2位) + 序号(2位)
 * 例如: 108001 = 文件模块(10) + 存储插件(8) + 分类(00) + 序号(1)
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum LocalStorageErrorCodeEnums {

    // ==================== 初始化错误 (00-09) ====================
    INIT_FAILED("108000", LocalStorageI18nKeyConstants.INIT_FAILED),

    // ==================== 参数校验错误 (10-19) ====================
    INPUT_STREAM_NULL("108010", LocalStorageI18nKeyConstants.INPUT_STREAM_NULL),
    FILE_NAME_EMPTY("108011", LocalStorageI18nKeyConstants.FILE_NAME_EMPTY),
    FILE_PATH_EMPTY("108012", LocalStorageI18nKeyConstants.FILE_PATH_EMPTY),
    FILE_SIZE_EXCEEDED("108013", LocalStorageI18nKeyConstants.FILE_SIZE_EXCEEDED),

    // ==================== 存储操作错误 (20-39) ====================
    UPLOAD_FAILED("108020", LocalStorageI18nKeyConstants.UPLOAD_FAILED),
    DOWNLOAD_FAILED("108021", LocalStorageI18nKeyConstants.DOWNLOAD_FAILED),
    DELETE_FAILED("108022", LocalStorageI18nKeyConstants.DELETE_FAILED),
    FILE_NOT_EXIST("108023", LocalStorageI18nKeyConstants.FILE_NOT_EXIST),

    // ==================== 分片操作错误 (40-49) ====================
    CHUNK_UPLOAD_FAILED("108040", LocalStorageI18nKeyConstants.CHUNK_UPLOAD_FAILED),
    CHUNK_MERGE_FAILED("108041", LocalStorageI18nKeyConstants.CHUNK_MERGE_FAILED),
    CHUNK_DIR_NOT_FOUND("108042", LocalStorageI18nKeyConstants.CHUNK_DIR_NOT_FOUND),
    CHUNK_FILES_EMPTY("108043", LocalStorageI18nKeyConstants.CHUNK_FILES_EMPTY),

    ;

    private final String code;
    private final String msg;

    LocalStorageErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据 code 获取枚举
     */
    public static LocalStorageErrorCodeEnums fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (LocalStorageErrorCodeEnums e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
