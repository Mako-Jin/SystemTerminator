package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 文件上传异常
 */
public class FileUploadException extends FileException {

    public FileUploadException(String message) {
        super(message);
    }

    public FileUploadException(String message, Exception e) {
        super(message, e);
    }

    public FileUploadException(String message, Object... args) {
        super(message, args);
    }

    public FileUploadException(String code, String message) {
        super(code, message);
    }

    public FileUploadException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileUploadException(FileErrorCodeEnums errorCode) {
        super(errorCode);
    }

    public FileUploadException(FileErrorCodeEnums errorCode, Object... args) {
        super(errorCode, args);
    }
}