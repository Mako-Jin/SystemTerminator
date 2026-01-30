package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import org.springframework.util.StringUtils;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:04
 */
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
