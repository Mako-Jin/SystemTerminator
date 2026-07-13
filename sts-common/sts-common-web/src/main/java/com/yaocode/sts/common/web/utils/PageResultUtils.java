package com.yaocode.sts.common.web.utils;

import com.yaocode.sts.common.web.enums.ResultEnums;
import com.yaocode.sts.common.web.model.PageResultModel;
import com.yaocode.sts.common.web.model.ResultModel;

import java.util.List;

public class PageResultUtils {

    public static PageResultModel<String> ok () {
        return new PageResultModel<>(ResultEnums.SUCCESS);
    }

    public static <T> PageResultModel<T> ok (Long totalCount, List<T> data) {
        return new PageResultModel<>(ResultEnums.SUCCESS, totalCount, data);
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
