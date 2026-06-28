package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class EducationId extends Identifier<String> {
    private EducationId(String value) {
        super(value);
    }

    public static EducationId nextId() {
        return new EducationId(IdFactory.generate());
    }
}
