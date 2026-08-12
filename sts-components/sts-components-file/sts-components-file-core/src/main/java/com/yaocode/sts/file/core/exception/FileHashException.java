package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 文件哈希操作异常
 * <p>
 * 专用于 MD5/SHA/SM3 等哈希计算过程中的异常，
 * 使用 {@link FileErrorCodeEnums} 中的哈希错误码。
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
public class FileHashException extends FileException {

    public FileHashException(FileErrorCodeEnums errorCode) {
        super(errorCode.getCode(), errorCode.getMsg());
    }

    public FileHashException(FileErrorCodeEnums errorCode, Object... args) {
        super(errorCode.getCode(), errorCode.getMsg(), args);
    }

    public FileHashException(FileErrorCodeEnums errorCode, Exception e) {
        super(errorCode.getCode(), errorCode.getMsg(), e);
    }

    public FileHashException(String message) {
        super(message);
    }

    public FileHashException(String message, Exception e) {
        super(message, e);
    }

    public FileHashException(String code, String message) {
        super(code, message);
    }
}
