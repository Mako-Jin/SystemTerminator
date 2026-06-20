package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class LoginHistoryId extends Identifier<String> {
    private LoginHistoryId(String value) {
        super(value);
    }

    public static LoginHistoryId of(String value) {
        return new LoginHistoryId(value);
    }

    public static LoginHistoryId nextId() {
        return new LoginHistoryId(IdFactory.generate());
    }
}
