package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.valueobject.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:04
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class ResourceId extends Identifier<String> {

    public ResourceId(String value) {
        super(value);
    }

    public static ResourceId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new ResourceId(value);
    }

    public static ResourceId nextId() {
        return new ResourceId(IdFactory.generate().toString());
    }

}
