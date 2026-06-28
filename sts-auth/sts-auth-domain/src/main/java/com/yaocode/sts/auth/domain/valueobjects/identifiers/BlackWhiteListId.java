package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class BlackWhiteListId extends Identifier<String> {
    private BlackWhiteListId(String value) {
        super(value);
    }

    public static BlackWhiteListId of(String value) {
        return new BlackWhiteListId(value);
    }

    public static BlackWhiteListId nextId() {
        return new BlackWhiteListId(IdFactory.generate());
    }

}
