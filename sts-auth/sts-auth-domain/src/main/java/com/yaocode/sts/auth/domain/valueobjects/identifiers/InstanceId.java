package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class InstanceId extends Identifier<String> {
    private InstanceId(String value) {
        super(value);
    }

    public static InstanceId nextId() {
        return new InstanceId(IdFactory.generate());
    }
}
