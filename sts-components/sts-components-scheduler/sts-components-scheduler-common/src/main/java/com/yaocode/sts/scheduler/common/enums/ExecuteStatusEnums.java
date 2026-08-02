package com.yaocode.sts.scheduler.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 执行状态枚举
 */
@Getter
@AllArgsConstructor
public enum ExecuteStatusEnums {

    RUNNING(0, "进行中"),
    SUCCESS(1, "成功"),
    FAILED(2, "失败"),
    TIMEOUT(3, "超时"),
    CANCELED(4, "取消"),
    SKIPPED(5, "跳过");

    private final Integer code;
    private final String desc;

    public static ExecuteStatusEnums getByCode(Integer code) {
        for (ExecuteStatusEnums value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }
}
