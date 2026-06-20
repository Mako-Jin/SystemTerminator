package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class InstanceId extends Identifier<String> {
    private InstanceId(String value) {
        super(value);
    }
}
