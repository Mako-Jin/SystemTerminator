package com.yaocode.sts.components.file.core.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

public class FileException extends BusinessException {
    public FileException(String message, Exception e) {
        super(message, e);
    }
}
