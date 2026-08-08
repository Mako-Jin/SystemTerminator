package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 版本异常
 */
public class FileVersionException extends FileException {

    public FileVersionException(String message) {
        super(message);
    }

    public FileVersionException(String message, Exception e) {
        super(message, e);
    }

    public FileVersionException(String message, Object... args) {
        super(message, args);
    }

    public FileVersionException(String code, String message) {
        super(code, message);
    }

    public FileVersionException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileVersionException(FileErrorCodeEnums errorCode) {
        super(errorCode);
    }

    public FileVersionException(FileErrorCodeEnums errorCode, Object... args) {
        super(errorCode, args);
    }
}