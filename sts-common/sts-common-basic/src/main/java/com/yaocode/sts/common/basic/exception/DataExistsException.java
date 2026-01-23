package com.yaocode.sts.common.basic.exception;

/**
 * 数据存在性异常
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:06
 */
public class DataExistsException extends RuntimeException {
    public DataExistsException(String message) {
        super(message);
    }
}
