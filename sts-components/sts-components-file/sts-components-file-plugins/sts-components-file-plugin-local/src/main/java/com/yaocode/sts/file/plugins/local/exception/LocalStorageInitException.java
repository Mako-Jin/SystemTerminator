package com.yaocode.sts.file.plugins.local.exception;

import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.plugins.local.LocalStorageErrorCodeEnums;

/**
 * 本地存储初始化异常
 *
 * @author yaocode
 * @since 1.0.0
 */
public class LocalStorageInitException extends FileException {

    public LocalStorageInitException(String message) {
        super(message);
    }

    public LocalStorageInitException(String message, Exception e) {
        super(message, e);
    }

    public LocalStorageInitException(String message, Object... args) {
        super(message, args);
    }

    public LocalStorageInitException(String code, String message) {
        super(code, message);
    }

    public LocalStorageInitException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public LocalStorageInitException(LocalStorageErrorCodeEnums errorCode) {
        super(errorCode.getCode(), errorCode.getMsg());
    }

    public LocalStorageInitException(LocalStorageErrorCodeEnums errorCode, Object... args) {
        super(errorCode.getCode(), errorCode.getMsg(), args);
    }
}
