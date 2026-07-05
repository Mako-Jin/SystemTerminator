package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class CredentialId extends Identifier<String> {
    private CredentialId(String value) {
        super(value);
    }

    public static CredentialId nextId() {
        return new CredentialId(IdFactory.generate());
    }
}
