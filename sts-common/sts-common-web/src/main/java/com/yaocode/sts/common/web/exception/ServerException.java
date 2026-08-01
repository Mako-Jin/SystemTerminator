package com.yaocode.sts.common.web.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;
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
public class ServerException extends BusinessException {

    public ServerException() {
        super();
    }

    public ServerException(String code, String msg) {
        super(code, msg);
    }

    public ServerException(ResultEnums resultEnums) {
        super(resultEnums.getCode(), resultEnums.getMsg());
    }

}
