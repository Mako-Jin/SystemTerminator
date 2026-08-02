package com.yaocode.sts.scheduler.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务类型枚举
 */
@Getter
@AllArgsConstructor
public enum JobTypeEnums {

    BEAN(1, "Bean方法"),
    REST(2, "REST调用"),
    RPC(3, "RPC调用"),
    SCRIPT(4, "脚本任务"),
    SQL(5, "SQL任务"),
    MQ(6, "消息队列");

    private final Integer code;
    private final String desc;

    public static JobTypeEnums getByCode(Integer code) {
        for (JobTypeEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
