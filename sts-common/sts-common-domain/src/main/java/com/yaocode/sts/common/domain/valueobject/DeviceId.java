package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
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
            throw new IllegalArgumentException(DomainI18nKeyConstants.COMMON_DOMAIN_IDENTIFIER_VALUE_NULL);
        }
        return new DeviceId(value);
    }

    public static DeviceId nextId() {
        return new DeviceId(IdFactory.generate());
    }

}