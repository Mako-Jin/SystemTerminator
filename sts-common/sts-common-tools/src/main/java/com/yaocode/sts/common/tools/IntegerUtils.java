package com.yaocode.sts.common.tools;

import java.util.Objects;

/**
 * 整型数规则类
 * @author: Jin-LiangBo
 * @date: 2026年02月12日 11:32
 */
public class IntegerUtils {

    public static Integer requireBetween(Integer value, Integer min, Integer max, String message) {
        if (Objects.isNull(value) || Objects.isNull(min) || Objects.isNull(max)) {
            throw new IllegalArgumentException("值不能为空！");
        }
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

}
