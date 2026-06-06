package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

@Value
@EqualsAndHashCode(callSuper = true)
public class ClientId extends Identifier<String> {

    private ClientId(String value) {
        super(value);
    }

    public static ClientId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new ClientId(value);
    }

    public static ClientId nextId() {
        return new ClientId(IdFactory.generate());
    }

}
