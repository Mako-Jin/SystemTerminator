package com.yaocode.sts.common.crypto.exception;

import com.yaocode.sts.common.basic.exception.BusinessException;

public class CryptoException extends BusinessException {

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException() {
    }

    public CryptoException(Exception e) {
        super(e);
    }

    public CryptoException(String message, Throwable cause) {
        super(message, cause);
    }
}
