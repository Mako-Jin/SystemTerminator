package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class CredentialId extends Identifier<String> {
    private CredentialId(String value) {
        super(value);
    }
}
