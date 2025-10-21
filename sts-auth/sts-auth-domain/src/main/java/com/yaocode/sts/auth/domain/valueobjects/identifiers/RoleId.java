package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * RoleId值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class RoleId extends Identifier<String> {

    private RoleId(String value) {
        super(value);
    }

    public static RoleId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new RoleId(value);
    }

    public static RoleId nextId() {
        return new RoleId(IdFactory.generate());
    }

}
