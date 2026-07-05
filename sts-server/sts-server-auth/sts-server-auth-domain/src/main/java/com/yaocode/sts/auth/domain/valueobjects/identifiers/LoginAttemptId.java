package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class LoginAttemptId extends Identifier<String> {
    protected LoginAttemptId(String value) {
        super(value);
    }

    public static LoginAttemptId nextId() {
        return new LoginAttemptId(IdFactory.generate());
    }

}
