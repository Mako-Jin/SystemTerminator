package com.yaocode.sts.scheduler.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务状态枚举
 */
@Getter
@AllArgsConstructor
public enum JobStatusEnums {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    PAUSED(2, "暂停"),
    DEPRECATED(3, "废弃");

    private final Integer code;
    private final String desc;

    public static JobStatusEnums getByCode(Integer code) {
        for (JobStatusEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
