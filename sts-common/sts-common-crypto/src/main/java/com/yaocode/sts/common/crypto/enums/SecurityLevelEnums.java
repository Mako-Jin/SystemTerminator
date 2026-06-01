package com.yaocode.sts.common.crypto.enums;

public enum SecurityLevelEnums {

    /** 高安全 */
    HIGH("高安全", "推荐使用"),
    /** 较弱 */
    WEAK("较弱", "不推荐新系统使用"),
    /** 不安全 */
    INSECURE("不安全", "仅兼容旧系统");

    private final String level;
    private final String advice;

    SecurityLevelEnums(String level, String advice) {
        this.level = level;
        this.advice = advice;
    }

    public String getLevel() {
        return level;
    }

    public String getAdvice() {
        return advice;
    }

}
