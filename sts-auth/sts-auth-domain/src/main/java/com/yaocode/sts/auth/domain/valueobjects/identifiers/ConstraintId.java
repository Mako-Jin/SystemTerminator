package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class ConstraintId extends Identifier<String> {
    private ConstraintId(String value) {
        super(value);
    }
}
