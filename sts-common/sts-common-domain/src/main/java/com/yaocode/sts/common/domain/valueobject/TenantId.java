package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
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
            throw new IllegalArgumentException(DomainI18nKeyConstants.TENANT_ID_NULL);
        }
        return new TenantId(value);
    }

    public static TenantId nextId() {
        return new TenantId(IdFactory.generate().toString());
    }

}
