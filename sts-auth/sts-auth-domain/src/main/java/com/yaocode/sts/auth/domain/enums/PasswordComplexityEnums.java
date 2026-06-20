package com.yaocode.sts.auth.domain.enums;

import lombok.Getter;

/**
 * 密码复杂度
 */
@Getter
public enum PasswordComplexityEnums {

    LOW("LOW", "低"),
    MEDIUM("MEDIUM", "中"),
    HIGH("HIGH", "高");

    private final String code;
    private final String desc;

    PasswordComplexityEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PasswordComplexityEnums fromCode(String code) {
        for (PasswordComplexityEnums complexity : values()) {
            if (complexity.code.equals(code)) {
                return complexity;
            }
        }
        return MEDIUM;
    }

    public int getMinLength() {
        return switch (this) {
            case LOW -> 6;
            case HIGH -> 12;
            default -> 8;
        };
    }

    public boolean requireSpecialChar() {
        return this == HIGH;
    }

    public boolean requireUppercase() {
        return this == HIGH;
    }

    public boolean requireDigit() {
        return this != LOW;
    }

}
