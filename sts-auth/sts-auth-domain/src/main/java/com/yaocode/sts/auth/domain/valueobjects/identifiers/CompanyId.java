package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;

public class CompanyId extends Identifier<String> {
    private CompanyId(String value) {
        super(value);
    }

    public static CompanyId nextId() {
        return new CompanyId(IdFactory.generate());
    }
}
