package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

public class FileValidationException extends FileException{
    public FileValidationException(String message, Exception e) {
        super(message, e);
    }

    public FileValidationException(String message) {
        super(message);
    }

    public FileValidationException(FileErrorCodeEnums fileErrorCodeEnums) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg());
    }

    public FileValidationException(String message, Object... args) {
        super(message, args);
    }

    public FileValidationException(FileErrorCodeEnums fileErrorCodeEnums, Object... args) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg(), args);
    }

}
