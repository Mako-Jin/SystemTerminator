package com.yaocode.sts.file.core.exception;


import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

public class FileStorageException extends FileException {
    public FileStorageException(String message, Exception e) {
        super(message, e);
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(FileErrorCodeEnums fileErrorCodeEnums) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg());
    }

    public FileStorageException(String message, Object... args) {
        super(message, args);
    }

    public FileStorageException(FileErrorCodeEnums fileErrorCodeEnums, Object... args) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg(), args);
    }

}
