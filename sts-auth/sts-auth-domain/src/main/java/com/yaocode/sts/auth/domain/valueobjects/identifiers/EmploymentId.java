package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class EmploymentId extends Identifier<String> {
    private EmploymentId(String value) {
        super(value);
    }

    public static EmploymentId nextId() {
        return new EmploymentId(IdFactory.generate());
    }
}
