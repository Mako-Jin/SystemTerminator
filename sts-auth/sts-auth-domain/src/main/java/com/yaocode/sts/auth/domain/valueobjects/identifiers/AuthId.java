package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * AuthId值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AuthId extends Identifier<String> {

    private AuthId(String value) {
        super(value);
    }

    public static AuthId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new AuthId(value);
    }

    public static AuthId nextId() {
        return new AuthId(IdFactory.generate());
    }

}
