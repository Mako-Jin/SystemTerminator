package com.yaocode.sts.file.interfaces.advices;

import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.common.web.advice.GlobalExceptionHandler;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.file.core.enums.FileErrorCodeEnums;
import com.yaocode.sts.file.core.exception.StorageException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@Slf4j
@RestControllerAdvice
public class FileExceptionHandler extends GlobalExceptionHandler {

    @Resource
    private MessageUtils messageUtils;

    public FileExceptionHandler(MessageUtils messageUtils) {
        super(messageUtils);
    }

    /**
     * 文件大小超限异常
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultModel<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("文件大小超限 ==> {}", e.getMessage());
        return handle(FileErrorCodeEnums.FILE_TOO_LARGE, e);
    }

    /**
     * 文件上传异常
     */
    @ExceptionHandler(MultipartException.class)
    public ResultModel<?> handleMultipartException(MultipartException e) {
        log.warn("文件上传异常 ==> {}", e.getMessage());
        return handle(FileErrorCodeEnums.FILE_UPLOAD_ERROR, e);
    }

    /**
     * 存储异常
     */
    @ExceptionHandler(StorageException.class)
    public ResultModel<?> handleStorageException(StorageException e) {
        log.error("存储异常 ==> {}", e.getMessage());
        return handle(FileErrorCodeEnums.STORAGE_ERROR, e);
    }

    private ResultModel<?> handle(FileErrorCodeEnums resultEnums, Exception exception) {
        String message = exception.getMessage();
        message = StringUtils.hasText(message) ? message : resultEnums.getMessage();
        // if (exception instanceof DataExistException dataExistException) {
        //     return ResultUtils.error(resultEnums.getCode(), message, dataExistException.getData());
        // }
        return super.handle(resultEnums.getCode(), message);
    }

}
