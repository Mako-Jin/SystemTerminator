package com.yaocode.sts.scheduler.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行器状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExecutorStatusEnums {

    OFFLINE(0, "离线"),
    ONLINE(1, "在线"),
    BUSY(2, "繁忙"),
    MAINTENANCE(3, "维护中");

    private final Integer code;
    private final String desc;

    public static ExecutorStatusEnums getByCode(Integer code) {
        for (ExecutorStatusEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
