package com.yaocode.sts.components.file.core.enums;

import lombok.Getter;

/**
 * 存储策略类型枚举
 * <p>
 * 定义存储选择策略的类型
 * </p>
 *
 * @author yaocode
 * @since 1.0.0
 */
@Getter
public enum StrategyTypeEnums {

    /**
     * 自动策略 - 根据文件大小、类型自动选择最优存储
     */
    AUTO("auto", "自动策略", "根据文件大小、类型自动选择最优存储"),

    /**
     * 优先级策略 - 按用户指定的优先级顺序选择
     */
    PRIORITY("priority", "优先级策略", "按用户指定的优先级顺序选择"),

    /**
     * 成本策略 - 选择成本最低的存储
     */
    COST("cost", "成本策略", "选择成本最低的存储"),

    /**
     * 性能策略 - 选择性能最好的存储
     */
    PERFORMANCE("performance", "性能策略", "选择性能最好的存储"),

    /**
     * 指定策略 - 直接使用用户指定的存储类型
     */
    SPECIFIED("specified", "指定策略", "直接使用用户指定的存储类型"),

    /**
     * 就近策略 - 选择地域最近的存储
     */
    GEO("geo", "就近策略", "选择地域最近的存储"),

    /**
     * 轮询策略 - 轮询选择存储（负载均衡）
     */
    ROUND_ROBIN("round_robin", "轮询策略", "轮询选择存储，实现负载均衡"),

    /**
     * 随机策略 - 随机选择存储
     */
    RANDOM("random", "随机策略", "随机选择存储"),

    /**
     * 一致性哈希策略 - 基于文件MD5选择存储
     */
    CONSISTENT_HASH("consistent_hash", "一致性哈希策略", "基于文件MD5选择存储");

    /** 策略代码 */
    private final String code;

    /** 策略名称 */
    private final String name;

    /** 策略描述 */
    private final String description;

    StrategyTypeEnums(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * 根据代码获取枚举
     *
     * @param code 策略代码
     * @return 策略枚举，如果不存在返回null
     */
    public static StrategyTypeEnums fromCode(String code) {
        if (code == null || code.isEmpty()) {
            return null;
        }
        for (StrategyTypeEnums strategy : values()) {
            if (strategy.code.equalsIgnoreCase(code)) {
                return strategy;
            }
        }
        return null;
    }

    /**
     * 根据代码获取枚举，不存在时返回默认值
     *
     * @param code 策略代码
     * @param defaultStrategy 默认策略
     * @return 策略枚举
     */
    public static StrategyTypeEnums fromCodeOrDefault(String code, StrategyTypeEnums defaultStrategy) {
        StrategyTypeEnums strategy = fromCode(code);
        return strategy != null ? strategy : defaultStrategy;
    }

    /**
     * 根据名称获取枚举
     *
     * @param name 策略名称
     * @return 策略枚举，如果不存在返回null
     */
    public static StrategyTypeEnums fromName(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        for (StrategyTypeEnums strategy : values()) {
            if (strategy.name.equalsIgnoreCase(name)) {
                return strategy;
            }
        }
        return null;
    }

    /**
     * 检查是否为有效的策略代码
     *
     * @param code 策略代码
     * @return 是否有效
     */
    public static boolean isValid(String code) {
        return fromCode(code) != null;
    }

    /**
     * 获取所有策略代码列表
     *
     * @return 策略代码列表
     */
    public static String[] getCodes() {
        StrategyTypeEnums[] values = values();
        String[] codes = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            codes[i] = values[i].getCode();
        }
        return codes;
    }

    /**
     * 获取所有策略名称列表
     *
     * @return 策略名称列表
     */
    public static String[] getNames() {
        StrategyTypeEnums[] values = values();
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].getName();
        }
        return names;
    }

    /**
     * 判断是否为自动选择策略（不依赖用户指定）
     */
    public boolean isAutoStrategy() {
        return this == AUTO || this == COST || this == PERFORMANCE
                || this == GEO || this == ROUND_ROBIN || this == RANDOM
                || this == CONSISTENT_HASH;
    }

    /**
     * 判断是否需要用户输入参数
     */
    public boolean requiresUserInput() {
        return this == PRIORITY || this == SPECIFIED;
    }

    /**
     * 判断是否为负载均衡策略
     */
    public boolean isLoadBalanceStrategy() {
        return this == ROUND_ROBIN || this == RANDOM || this == CONSISTENT_HASH;
    }

    /**
     * 获取策略代码（便捷方法）
     *
     * @param strategy 策略枚举
     * @return 策略代码
     */
    public static String getCode(StrategyTypeEnums strategy) {
        return strategy != null ? strategy.getCode() : null;
    }

    /**
     * 获取默认策略
     *
     * @return 默认策略（AUTO）
     */
    public static StrategyTypeEnums getDefault() {
        return AUTO;
    }
}
