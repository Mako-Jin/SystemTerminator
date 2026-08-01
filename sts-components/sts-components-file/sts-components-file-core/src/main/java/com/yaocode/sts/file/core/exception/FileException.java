package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

public class FileException extends BusinessException {
    public FileException(String message, Exception e) {
        super(message, e);
    }

    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Object... args) {
        super(message, args);
    }

    public FileException(String code, String message) {
        super(code, message);
    }

    public FileException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileException(FileErrorCodeEnums fileErrorCodeEnums, Object... args) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg(), args);
    }
}
