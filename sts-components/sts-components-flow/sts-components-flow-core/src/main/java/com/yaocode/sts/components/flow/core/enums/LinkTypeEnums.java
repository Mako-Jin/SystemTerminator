package com.yaocode.sts.components.flow.core.enums;

import lombok.Getter;

/**
 * 身份关联类型
 */
@Getter
public enum LinkTypeEnums {

    CANDIDATE_USER(1, "候选人"),
    CANDIDATE_GROUP(2, "候选组"),
    DELEGATED(3, "被委派人"),
    DELEGATOR(4, "委派人"),
    PROCESS_STARTER(5, "流程启动人"),
    PROCESS_ADMIN(6, "流程管理员"),
    TASK_ASSIGNEE(7, "任务处理人");

    private final int code;
    private final String description;

    LinkTypeEnums(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public static LinkTypeEnums fromCode(int code) {
        for (LinkTypeEnums type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
