package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class LoginAttemptId extends Identifier<String> {
    protected LoginAttemptId(String value) {
        super(value);
    }
}
