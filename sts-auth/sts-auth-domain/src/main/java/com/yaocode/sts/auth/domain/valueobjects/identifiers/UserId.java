package com.yaocode.sts.auth.domain.valueobjects.identifiers;

import com.yaocode.sts.common.domain.model.Identifier;
import com.yaocode.sts.common.tools.id.IdFactory;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

/**
 * UserId值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class UserId extends Identifier<String> {

    private UserId(String value) {
        super(value);
    }

    public static UserId of(String value) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("用户ID不能为空！");
        }
        return new UserId(value);
    }

    public static UserId nextId() {
        return new UserId(IdFactory.generate());
    }

}
