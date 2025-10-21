package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * 租户id值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:29
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class TenantId extends Identifier<String> {

    private TenantId(String value) {
        super(value);
    }

    public static TenantId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new TenantId(value);
    }

    public static TenantId nextId() {
        return new TenantId(IdFactory.generate().toString());
    }

}
