package com.yaocode.sts.common.domain.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

/**
 * 领域服务异常
 * @author: Jin-LiangBo
 * @date: 2026年02月05日 18:26
 */
public class DomainException extends BusinessException {
    public DomainException(String msg) {
        super(msg);
    }
}
