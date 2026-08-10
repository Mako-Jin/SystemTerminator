package com.yaocode.sts.file.plugins.local.exception;

import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.plugins.local.LocalStorageErrorCodeEnums;

/**
 * 本地存储分片操作异常
 *
 * @author yaocode
 * @since 1.0.0
 */
public class LocalChunkOperationException extends FileException {

    public LocalChunkOperationException(String message) {
        super(message);
    }

    public LocalChunkOperationException(String message, Exception e) {
        super(message, e);
    }

    public LocalChunkOperationException(String message, Object... args) {
        super(message, args);
    }

    public LocalChunkOperationException(String code, String message) {
        super(code, message);
    }

    public LocalChunkOperationException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public LocalChunkOperationException(LocalStorageErrorCodeEnums errorCode) {
        super(errorCode.getCode(), errorCode.getMsg());
    }

    public LocalChunkOperationException(LocalStorageErrorCodeEnums errorCode, Object... args) {
        super(errorCode.getCode(), errorCode.getMsg(), args);
    }
}
