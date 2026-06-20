package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;

public class SecretQuestionId extends Identifier<String> {
    private SecretQuestionId(String value) {
        super(value);
    }
}
