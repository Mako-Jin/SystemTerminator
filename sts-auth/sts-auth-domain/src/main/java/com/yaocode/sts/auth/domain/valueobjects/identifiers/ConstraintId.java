package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class ConstraintId extends Identifier<String> {
    private ConstraintId(String value) {
        super(value);
    }

    public static ConstraintId nextId() {
        return new ConstraintId(IdFactory.generate());
    }

}
