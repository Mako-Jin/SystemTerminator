package com.yaocode.sts.common.domain.model;

import java.util.Objects;

/**
 * 值对象基类
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:41
 */
public abstract class BaseValueObject implements ValueObject {

    /**
     * equals
     * @param obj 比较对象
     * @return boolean
     */
    @Override
    public abstract boolean equals(Object obj);

    /**
     * hashCode
     * @return int
     */
    @Override
    public abstract int hashCode();

    /**
     * toString
     * @return java.lang.String
     */
    @Override
    public abstract String toString();

    /**
     * 辅助方法：安全比较数组
     * @param array1 数组1
     * @param array2 数组2
     * @return boolean
     */
    protected boolean arraysEqual(Object[] array1, Object[] array2) {
        if (array1 == array2) {
            return true;
        }
        if (array1 == null || array2 == null) {
            return false;
        }
        if (array1.length != array2.length) {
            return false;
        }

        for (int i = 0; i < array1.length; i++) {
            if (!Objects.equals(array1[i], array2[i])) {
                return false;
            }
        }
        return true;
    }

}
