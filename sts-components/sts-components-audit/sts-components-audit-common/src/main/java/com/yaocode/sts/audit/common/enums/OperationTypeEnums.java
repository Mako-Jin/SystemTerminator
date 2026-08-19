package com.yaocode.sts.audit.common.enums;

import lombok.Getter;

@Getter
public enum OperationTypeEnums {
    LOGIN("登录"),
    LOGOUT("登出"),
    QUERY("查询"),
    ADD("新增"),
    UPDATE("修改"),
    DELETE("删除"),
    EXPORT("导出"),
    IMPORT("导入"),
    APPROVE("审批"),
    REJECT("驳回"),
    OTHER("其他");

    private final String desc;

    OperationTypeEnums(String desc) {
        this.desc = desc;
    }

}
