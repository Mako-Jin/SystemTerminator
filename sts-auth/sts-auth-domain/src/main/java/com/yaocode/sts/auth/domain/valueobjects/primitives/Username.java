package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.domain.model.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * UserName值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Username extends Identifier<String> {

    /**
     * TODO 这些应该改到租户配置里面去
     */
    private static final int USERNAME_MIN_LENGTH = 3;
    private static final int USERNAME_MAX_LENGTH = 30;
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_]+$";

    protected Username(String value) {
        super(value);
    }

    public static Username of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (value.length() < USERNAME_MIN_LENGTH || value.length() > USERNAME_MAX_LENGTH) {
            throw new IllegalArgumentException("auth.username.rule.check.error");
        }
        if (!value.matches(USERNAME_REGEX)) {
            throw new IllegalArgumentException("auth.username.rule.check.error");
        }
        return new Username(value);
    }

}
