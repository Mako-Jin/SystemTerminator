package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

@Value
@EqualsAndHashCode(callSuper = true)
public class TokenId extends Identifier<String> {

    private TokenId(String value) {
        super(value);
    }

    public static TokenId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new TokenId(value);
    }

    public static TokenId nextId() {
        return new TokenId(IdFactory.generate());
    }

}
