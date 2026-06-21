package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.enums.PasswordComplexityEnums;
import lombok.Getter;

import java.util.Objects;

/**
 * 密码策略（值对象）
 * 封装密码复杂度、过期策略等配置
 */
@Getter
public class PasswordPolicy {

    private final boolean expiryEnabled;
    private final int expiryDays;
    private final int minLength;
    private final PasswordComplexityEnums complexity;
    private final int historyCount;

    private PasswordPolicy(Builder builder) {
        this.expiryEnabled = builder.expiryEnabled;
        this.expiryDays = builder.expiryDays;
        this.minLength = builder.minLength;
        this.complexity = builder.complexity;
        this.historyCount = builder.historyCount;
    }

    public static class Builder {
        private boolean expiryEnabled = false;
        private int expiryDays = 90;
        private int minLength = 8;
        private PasswordComplexityEnums complexity = PasswordComplexityEnums.MEDIUM;
        private int historyCount = 5;

        public Builder expiryEnabled(boolean expiryEnabled) {
            this.expiryEnabled = expiryEnabled;
            return this;
        }

        public Builder expiryDays(int expiryDays) {
            if (expiryDays < 1) throw new IllegalArgumentException("密码过期天数至少为1天");
            this.expiryDays = expiryDays;
            return this;
        }

        public Builder minLength(int minLength) {
            if (minLength < 6) throw new IllegalArgumentException("密码最小长度至少为6位");
            this.minLength = minLength;
            return this;
        }

        public Builder complexity(PasswordComplexityEnums complexity) {
            this.complexity = complexity;
            return this;
        }

        public Builder historyCount(int historyCount) {
            if (historyCount < 0) throw new IllegalArgumentException("密码历史记录数不能为负数");
            this.historyCount = historyCount;
            return this;
        }

        public PasswordPolicy build() {
            return new PasswordPolicy(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 获取默认策略
     */
    public static PasswordPolicy defaultPolicy() {
        return builder().build();
    }

    /**
     * 验证密码是否满足复杂度要求
     */
    public boolean validate(String password) {
        if (password == null || password.length() < minLength) {
            return false;
        }

        return switch (complexity) {
            case MEDIUM -> password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
            case HIGH -> password.matches(".*[a-z].*") &&
                    password.matches(".*[A-Z].*") &&
                    password.matches(".*\\d.*") &&
                    password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*");
            default -> true;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordPolicy that = (PasswordPolicy) o;
        return expiryEnabled == that.expiryEnabled &&
                expiryDays == that.expiryDays &&
                minLength == that.minLength &&
                historyCount == that.historyCount &&
                complexity == that.complexity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expiryEnabled, expiryDays, minLength, complexity, historyCount);
    }

    @Override
    public String toString() {
        return "PasswordPolicy{" +
                "minLength=" + minLength +
                ", complexity=" + complexity +
                ", historyCount=" + historyCount +
                ", expiryDays=" + expiryDays +
                '}';
    }
}
