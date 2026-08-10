package com.yaocode.sts.file.plugins.local.exception;

import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.plugins.local.LocalStorageErrorCodeEnums;

/**
 * 本地存储操作异常（上传/下载/删除等）
 *
 * @author yaocode
 * @since 1.0.0
 */
public class LocalStorageOperationException extends FileException {

    public LocalStorageOperationException(String message) {
        super(message);
    }

    public LocalStorageOperationException(String message, Exception e) {
        super(message, e);
    }

    public LocalStorageOperationException(String message, Object... args) {
        super(message, args);
    }

    public LocalStorageOperationException(String code, String message) {
        super(code, message);
    }

    public LocalStorageOperationException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public LocalStorageOperationException(LocalStorageErrorCodeEnums errorCode) {
        super(errorCode.getCode(), errorCode.getMsg());
    }

    public LocalStorageOperationException(LocalStorageErrorCodeEnums errorCode, Object... args) {
        super(errorCode.getCode(), errorCode.getMsg(), args);
    }

}
