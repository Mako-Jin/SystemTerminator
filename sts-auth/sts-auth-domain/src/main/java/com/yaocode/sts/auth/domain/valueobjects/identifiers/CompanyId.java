package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class CompanyId extends Identifier<String> {
    private CompanyId(String value) {
        super(value);
    }
}
