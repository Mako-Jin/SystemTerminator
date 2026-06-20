package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class EmergencyContactId extends Identifier<String> {
    private EmergencyContactId(String value) {
        super(value);
    }
}
