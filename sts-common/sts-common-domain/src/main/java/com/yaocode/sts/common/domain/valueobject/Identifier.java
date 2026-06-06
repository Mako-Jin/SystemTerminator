package com.yaocode.sts.common.domain.valueobject;

import com.yaocode.sts.common.domain.constants.DomainI18nKeyConstants;
import lombok.Getter;

import java.util.Objects;

/**
 * 身份识别器
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 11:43
 */
@Getter
public abstract class Identifier<T> implements ValueObject {
    private final T value;

    protected Identifier(T value) {
        validate(value);
        this.value = value;
    }

    public void validate(T value) {
        if (value == null) {
            throw new IllegalArgumentException(DomainI18nKeyConstants.COMMON_DOMAIN_IDENTIFIER_VALUE_NULL);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Identifier<?> that = (Identifier<?>) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + value + "]";
    }
}
