package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class SecretQuestionId extends Identifier<String> {
    private SecretQuestionId(String value) {
        super(value);
    }

    public static SecretQuestionId nextId() {
        return new SecretQuestionId(IdFactory.generate());
    }
}
