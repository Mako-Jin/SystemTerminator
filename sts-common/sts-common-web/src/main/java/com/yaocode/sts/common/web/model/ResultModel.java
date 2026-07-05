package com.yaocode.sts.common.web.model;

import com.yaocode.sts.common.web.enums.ResultEnums;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:01
 */
@Setter
@Getter
public class ResultModel<T> {

    private String code;
    private String desc;

    private T data;

    public ResultModel() {
    }

    public ResultModel(ResultEnums resultEnums) {
        this.code = resultEnums.getCode();
        this.desc = resultEnums.getMsg();
    }

    public ResultModel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public ResultModel(ResultEnums resultEnums, T data) {
        this.code = resultEnums.getCode();
        this.desc = resultEnums.getMsg();
        this.data = data;
    }

}
