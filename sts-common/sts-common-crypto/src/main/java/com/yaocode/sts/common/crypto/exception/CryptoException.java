package com.yaocode.sts.common.crypto.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

public class CryptoException extends BusinessException {

    public CryptoException(Exception e) {
        super(e);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
