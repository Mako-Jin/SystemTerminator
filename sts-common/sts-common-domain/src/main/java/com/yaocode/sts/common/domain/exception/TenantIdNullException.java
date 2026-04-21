package com.yaocode.sts.common.domain.exception;

/**
 * 租户id空异常
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 18:51
 */
public class TenantIdNullException extends DomainException {
    public TenantIdNullException(String msg) {
        super(msg);
    }
}
