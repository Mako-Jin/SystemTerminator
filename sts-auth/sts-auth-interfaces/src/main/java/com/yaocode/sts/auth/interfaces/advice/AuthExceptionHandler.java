package com.yaocode.sts.auth.interfaces.advice;

import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import com.yaocode.sts.auth.application.exception.AuthenticationException;
import com.yaocode.sts.auth.application.exception.PermissionException;
import com.yaocode.sts.common.tools.messages.MessageUtils;
import com.yaocode.sts.common.web.advice.GlobalExceptionHandler;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 认证服务自定义异常
 * @author: Jin-LiangBo
 * @date: 2025年10月08日 14:29
 */
@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler extends GlobalExceptionHandler {

    @Resource
    private MessageUtils messageUtils;

    public AuthExceptionHandler(MessageUtils messageUtils) {
        super(messageUtils);
    }

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultModel<?> handleAuthenticationException(Exception exception) {
        log.error("认证异常 ==> {}", exception.getMessage());
        return handle(AuthErrorCodeEnums.AUTHENTICATION_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(PermissionException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultModel<?> handlePermissionException(Exception exception) {
        log.error("没权限异常 ==> {}", exception.getMessage());
        return handle(AuthErrorCodeEnums.NO_PERMISSION_ERROR, exception);
    }

    @ResponseBody
    @ExceptionHandler(AuthServerException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultModel<?> handleAuthServerException(AuthServerException exception) {
        log.error("认证服务自定义异常 ==> {} - {}", exception.getCode(), exception.getMessage());
        return handle(exception.getErrorEnums(), exception);
    }

    private ResultModel<?> handle(AuthErrorCodeEnums resultEnums, Exception exception) {
        String message = exception.getMessage();
        message = StringUtils.hasText(message) ? message : resultEnums.getMsg();
        // if (exception instanceof DataExistException dataExistException) {
        //     return ResultUtils.error(resultEnums.getCode(), message, dataExistException.getData());
        // }
        return super.handle(resultEnums.getCode(), message);
    }

}
