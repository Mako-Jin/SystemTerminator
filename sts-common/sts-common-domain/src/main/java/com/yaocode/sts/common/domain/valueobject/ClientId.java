package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
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
            throw new IllegalArgumentException(DomainI18nKeyConstants.COMMON_DOMAIN_IDENTIFIER_VALUE_NULL);
        }
        return new ClientId(value);
    }

    public static ClientId nextId() {
        return new ClientId(IdFactory.generate());
    }

}