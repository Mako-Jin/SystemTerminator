package com.yaocode.sts.file.core.exception;

import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;

/**
 * 权限异常
 */
public class FilePermissionException extends FileException {
    public FilePermissionException(String message, Exception e) {
        super(message, e);
    }

    public FilePermissionException(FileErrorCodeEnums fileErrorCodeEnums, Object... args) {
        super(fileErrorCodeEnums.getCode(), fileErrorCodeEnums.getMsg(), args);
    }
}
