package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

@Value
@EqualsAndHashCode(callSuper = true)
public class DeviceId extends Identifier<String> {

    private DeviceId(String value) {
        super(value);
    }

    public static DeviceId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IDENTIFIER_VALUE_CANNOT_BE_NULL);
        }
        return new DeviceId(value);
    }

    public static DeviceId nextId() {
        return new DeviceId(IdFactory.generate());
    }

}