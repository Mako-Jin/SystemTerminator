package com.yaocode.sts.common.db.exception;

/**
 * 不支持的db类型异常
 * @author: Jin-LiangBo
 * @date: 2026年04月22日 11:20
 */
public class UnsupportedDbTypeException extends DbMigrationException {

    public UnsupportedDbTypeException(String message) {
        super(message);
    }

    public UnsupportedDbTypeException(String message, Exception e) {
        super(message, e);
    }

}
