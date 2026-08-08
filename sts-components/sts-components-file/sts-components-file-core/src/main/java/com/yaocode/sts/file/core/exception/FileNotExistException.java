package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 文件不存在异常
 */
public class FileNotExistException extends FileException {

    public FileNotExistException(String message) {
        super(message);
    }

    public FileNotExistException(String message, Exception e) {
        super(message, e);
    }

    public FileNotExistException(String message, Object... args) {
        super(message, args);
    }

    public FileNotExistException(String code, String message) {
        super(code, message);
    }

    public FileNotExistException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileNotExistException(FileErrorCodeEnums errorCode) {
        super(errorCode);
    }

    public FileNotExistException(FileErrorCodeEnums errorCode, Object... args) {
        super(errorCode, args);
    }
}