package com.yaocode.sts.common.web.advice;

import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.common.web.enums.ResultEnums;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 * @author: Jin-LiangBo
 * @date: 2025年10月08日 18:21
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageUtils messageUtils;

    public GlobalExceptionHandler(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultModel<?> handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("参数校验异常 ==> {}", exception.getMessage());
        return handle(ResultEnums.PARAM_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultModel<?> handleException(Exception exception) {
        logger.error("服务内部异常 ==> {}", exception.getMessage());
        return handle(ResultEnums.SYSTEM_ERROR, exception);
    }

    private ResultModel<?> handle(ResultEnums resultEnums, Exception exception) {
        String message = exception.getMessage();
        message = StringUtils.hasText(message) ? message : resultEnums.getMsg();
        // if (exception instanceof DataExistException dataExistException) {
        //     return ResultUtils.error(resultEnums.getCode(), message, dataExistException.getData());
        // }
        return handle(resultEnums.getCode(), message);
    }

    protected ResultModel<?> handle(String code, String message) {
        message = messageUtils.getMessage(message);
        return ResultUtils.error(code, message);
    }

}
