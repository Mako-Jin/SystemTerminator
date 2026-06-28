package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class PasswordHistoryId extends Identifier<String> {
    private PasswordHistoryId(String value) {
        super(value);
    }

    public static PasswordHistoryId of(String value) {
        return new PasswordHistoryId(value);
    }

    public static PasswordHistoryId nextId() {
        return new PasswordHistoryId(IdFactory.generate());
    }
}
