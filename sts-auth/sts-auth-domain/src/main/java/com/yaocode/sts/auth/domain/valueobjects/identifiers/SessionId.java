package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class SessionId extends Identifier<String> {

    private SessionId(String value) {
        super(value);
    }

    public static SessionId of(String value) {
        return new SessionId(value);
    }

    public static SessionId nextId() {
        return new SessionId(IdFactory.generate());
    }

}
