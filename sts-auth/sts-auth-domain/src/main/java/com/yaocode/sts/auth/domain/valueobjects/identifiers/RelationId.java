package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.Objects;

@Value
@EqualsAndHashCode(callSuper = true)
public class RelationId extends Identifier<Long> {

    private RelationId(Long value) {
        super(value);
    }

    public static RelationId of(Long value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new RelationId(value);
    }

}