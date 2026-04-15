package com.yaocode.sts.common.basic.exception;

/**
 * 数据未发现异常
 * @author: Jin-LiangBo
 * @date: 2026年04月15日 17:03
 */
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }

}
