package com.yaocode.sts.file.core.exception;

public class FileNotExistException extends FileException {
    public FileNotExistException(String message, Exception e) {
        super(message, e);
    }

    public FileNotExistException(String message) {
        super(message);
    }
}
