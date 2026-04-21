package com.yaocode.sts.common.domain.exception;

import com.yaocode.sts.common.basic.exception.DataNotFoundException;

/**
 * 租户信息未找到异常
 * @author: Jin-LiangBo
 * @date: 2026年04月21日 19:00
 */
public class TenantNotFoundException extends DataNotFoundException {
    public TenantNotFoundException(String message) {
        super(message);
    }
}
