package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class EmergencyContactId extends Identifier<String> {
    private EmergencyContactId(String value) {
        super(value);
    }

    public static EmergencyContactId nextId() {
        return new EmergencyContactId(IdFactory.generate());
    }
}
