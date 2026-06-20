package com.yaocode.sts.auth.domain.valueobjects.identifiers;


import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class TenantConfigId extends Identifier<String> {

    private TenantConfigId(String value) {
        super(value);
    }

    public static TenantConfigId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new TenantConfigId(value);
    }

    public static TenantConfigId nextId() {
        return new TenantConfigId(IdFactory.generate());
    }

}
