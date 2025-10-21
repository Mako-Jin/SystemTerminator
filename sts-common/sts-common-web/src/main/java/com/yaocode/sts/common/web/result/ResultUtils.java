package com.yaocode.sts.common.web.result;

import com.yaocode.sts.common.web.enums.ResultEnums;
import com.yaocode.sts.common.web.model.ResultModel;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:00
 */
public class ResultUtils {

    public static ResultModel<String> ok () {
        return new ResultModel<>(ResultEnums.SUCCESS);
    }

    public static <T> ResultModel<T> ok (T data) {
        return new ResultModel<>(ResultEnums.SUCCESS, data);
    }

    public static ResultModel<String> error () {
        return new ResultModel<>(ResultEnums.SYSTEM_ERROR);
    }

    public static ResultModel<String> error (ResultEnums resultEnums) {
        return new ResultModel<>(resultEnums.getCode(), resultEnums.getMsg());
    }

    public static ResultModel<String> error (String code, String msg) {
        return new ResultModel<>(code, msg);
    }

}
