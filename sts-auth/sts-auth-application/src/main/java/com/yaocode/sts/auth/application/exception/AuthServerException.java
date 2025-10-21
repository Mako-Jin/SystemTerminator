package com.yaocode.sts.auth.application.exception;

import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.common.web.exception.ServerException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 认证服务服务异常
 * @author: Jin-LiangBo
 * @date: 2025年10月09日 19:55
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthServerException extends ServerException {

    private AuthErrorCodeEnums errorEnums;

    public AuthServerException(AuthErrorCodeEnums errorEnums) {
        super(errorEnums.getCode(), errorEnums.getMsg());
        this.errorEnums = errorEnums;
    }

}
