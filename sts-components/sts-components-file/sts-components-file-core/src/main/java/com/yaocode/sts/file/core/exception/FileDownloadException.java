package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 文件下载异常
 */
public class FileDownloadException extends FileException {

    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException(String message, Exception e) {
        super(message, e);
    }

    public FileDownloadException(String message, Object... args) {
        super(message, args);
    }

    public FileDownloadException(String code, String message) {
        super(code, message);
    }

    public FileDownloadException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileDownloadException(FileErrorCodeEnums errorCode) {
        super(errorCode);
    }

    public FileDownloadException(FileErrorCodeEnums errorCode, Object... args) {
        super(errorCode, args);
    }
}