package com.yaocode.sts.file.infrastructure.enums;

import com.yaocode.sts.file.infrastructure.constants.FileInfrastructureI18nKeyConstants;
import lombok.Getter;

/**
 * 基础设施层错误码枚举
 * <p>
 * 错误码格式: 文件模块(10) + 基础设施(9) + 分类(2位) + 序号(2位)
 * 例如: 109001 = 文件模块(10) + 基础设施(9) + 分类(00) + 序号(1)
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum FileInfrastructureErrorCodeEnums {

    // ==================== 插件管理错误 (00-09) ====================
    PLUGIN_LOAD_FAILED("109000", FileInfrastructureI18nKeyConstants.PLUGIN_LOAD_FAILED),
    PLUGIN_NOT_FOUND("109001", FileInfrastructureI18nKeyConstants.PLUGIN_NOT_FOUND),
    STORAGE_TYPE_NOT_SUPPORTED("109002", FileInfrastructureI18nKeyConstants.STORAGE_TYPE_NOT_SUPPORTED),

    // ==================== 存储操作错误 (10-29) ====================
    STORAGE_OPERATION_FAILED("109010", FileInfrastructureI18nKeyConstants.STORAGE_OPERATION_FAILED),
    STORAGE_NODE_UNAVAILABLE("109011", FileInfrastructureI18nKeyConstants.STORAGE_NODE_UNAVAILABLE),
    STORAGE_CAPACITY_EXCEEDED("109012", FileInfrastructureI18nKeyConstants.STORAGE_CAPACITY_EXCEEDED),

    // ==================== 上传会话错误 (30-39) ====================
    UPLOAD_SESSION_EXPIRED("109030", FileInfrastructureI18nKeyConstants.UPLOAD_SESSION_EXPIRED),
    UPLOAD_SESSION_CANCELLED("109031", FileInfrastructureI18nKeyConstants.UPLOAD_SESSION_CANCELLED),
    UPLOAD_SESSION_COMPLETED("109032", FileInfrastructureI18nKeyConstants.UPLOAD_SESSION_COMPLETED),

    // ==================== 数据库操作错误 (40-49) ====================
    DATA_ACCESS_FAILED("109040", FileInfrastructureI18nKeyConstants.DATA_ACCESS_FAILED),
    DATA_ALREADY_EXISTS("109041", FileInfrastructureI18nKeyConstants.DATA_ALREADY_EXISTS),
    DATA_NOT_FOUND("109042", FileInfrastructureI18nKeyConstants.DATA_NOT_FOUND),

    ;

    private final String code;
    private final String msg;

    FileInfrastructureErrorCodeEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 根据 code 获取枚举
     */
    public static FileInfrastructureErrorCodeEnums fromCode(String code) {
        if (code == null) {
            return null;
        }
        for (FileInfrastructureErrorCodeEnums e : values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
