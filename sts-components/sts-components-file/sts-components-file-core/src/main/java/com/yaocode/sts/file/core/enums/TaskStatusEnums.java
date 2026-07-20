package com.yaocode.sts.file.core.enums;

import lombok.Getter;

/**
 * 任务状态枚举
 */
@Getter
public enum TaskStatusEnums {

    QUEUED(0, "排队"),
    PROCESSING(1, "处理中"),
    COMPLETED(2, "已完成"),
    FAILED(3, "失败"),
    CANCELLED(4, "已取消"),
    TIMEOUT(5, "超时");

    private final Integer code;
    private final String desc;

    TaskStatusEnums(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
