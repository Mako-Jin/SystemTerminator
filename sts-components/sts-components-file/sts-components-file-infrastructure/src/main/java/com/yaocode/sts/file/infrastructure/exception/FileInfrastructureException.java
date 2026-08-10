package com.yaocode.sts.file.infrastructure.exception;

import com.yaocode.sts.file.core.exception.FileException;
import com.yaocode.sts.file.infrastructure.enums.FileInfrastructureErrorCodeEnums;

/**
 * 基础设施层基础异常
 *
 * @author yaocode
 * @since 1.0.0
 */
public class FileInfrastructureException extends FileException {

    public FileInfrastructureException(String message) {
        super(message);
    }

    public FileInfrastructureException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileInfrastructureException(String code, String message) {
        super(code, message);
    }

    public FileInfrastructureException(String code, String message, Object... args) {
        super(code, message, args);
    }

    public FileInfrastructureException(String message, Object... args) {
        super(message, args);
    }

    public FileInfrastructureException(FileInfrastructureErrorCodeEnums errorCodeEnums) {
        super(errorCodeEnums.getCode(), errorCodeEnums.getMsg());
    }

    public FileInfrastructureException(FileInfrastructureErrorCodeEnums errorCodeEnums, Object... args) {
        super(errorCodeEnums.getCode(), errorCodeEnums.getMsg(), args);
    }
}
