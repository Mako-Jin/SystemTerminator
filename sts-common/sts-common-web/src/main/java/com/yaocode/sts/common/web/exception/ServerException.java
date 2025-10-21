package com.yaocode.sts.common.web.exception;

import com.yaocode.sts.common.web.enums.ResultEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义服务异常
 * @author: Jin-LiangBo
 * @date: 2025年10月09日 19:54
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class ServerException extends RuntimeException {

    private String code;

    public ServerException() {
        super();
    }

    public ServerException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public ServerException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

}
