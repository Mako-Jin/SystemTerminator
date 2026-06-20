package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class EmploymentId extends Identifier<String> {
    private EmploymentId(String value) {
        super(value);
    }
}
