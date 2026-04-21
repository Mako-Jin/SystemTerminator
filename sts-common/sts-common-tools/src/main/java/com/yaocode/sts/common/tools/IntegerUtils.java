package com.yaocode.sts.common.tools;

import com.yaocode.sts.common.basic.constants.BasicI18nKeyConstants;
import com.yaocode.sts.common.tools.exception.EmptyValueException;
import com.yaocode.sts.common.tools.exception.OutRangeException;

import java.util.Objects;

/**
 * 整型数规则类
 * @author: Jin-LiangBo
 * @date: 2026年02月12日 11:32
 */
public class IntegerUtils {

    public static Integer requireBetween(Integer value, Integer min, Integer max, String message) {
        if (Objects.isNull(value) || Objects.isNull(min) || Objects.isNull(max)) {
            throw new EmptyValueException(BasicI18nKeyConstants.EMPTY_VALUE);
        }
        if (value < min || value > max) {
            throw new OutRangeException(message);
        }
        return value;
    }

}
