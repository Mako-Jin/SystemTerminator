package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;


/**
 * 操作类型
 */
@Getter
public enum OperationTypeEnums {

    START(1, "启动"),
    COMPLETE(2, "提交"),
    CLAIM(3, "认领"),
    DELEGATE(4, "委派"),
    TRANSFER(5, "转交"),
    REJECT(6, "驳回"),
    TERMINATE(7, "终止"),
    SUSPEND(8, "挂起"),
    RESUME(9, "恢复"),
    MODIFY_VARIABLE(10, "变量修改"),
    DEPLOY(11, "部署"),
    DELETE(12, "删除"),
    MIGRATE(13, "迁移"),
    MODIFY_PRIORITY(14, "修改优先级"),
    ADD_CANDIDATE(15, "添加候选人"),
    REMOVE_CANDIDATE(16, "删除候选人"),
    SET_ASSIGNEE(17, "设置处理人");

    private final int code;
    private final String description;

    OperationTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static OperationTypeEnums fromCode(int code) {
        for (OperationTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
