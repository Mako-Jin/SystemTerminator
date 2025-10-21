package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * 用户组id值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class UserGroupId extends Identifier<String> {

    private UserGroupId(String value) {
        super(value);
    }

    public static UserGroupId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("auth.identifier.value.null");
        }
        return new UserGroupId(value);
    }

    public static UserGroupId nextId() {
        return new UserGroupId(IdFactory.generate());
    }

}
