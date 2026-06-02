package com.yaocode.sts.common.crypto.utils;

import com.password4j.BcryptFunction;

/**
 * BCrypt 工具类
 * <p>提供简洁的静态方法调用 BCrypt 哈希算法</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月02日
 */
public final class BcryptUtils {

    /**
     * 默认成本因子（2^10 = 1024次迭代）
     */
    public static final int DEFAULT_COST = 10;

    /**
     * 最小成本因子
     */
    public static final int MIN_COST = 4;

    /**
     * 最大成本因子
     */
    public static final int MAX_COST = 31;

    /**
     * 私有构造函数，防止实例化
     */
    private BcryptUtils() {
    }

    // ==================== 简化的加密方法 ====================

    /**
     * BCrypt 哈希加密（使用默认成本因子）
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String hash(String password) {
        return BcryptFunction.getInstance(DEFAULT_COST).hash(password).getResult();
    }

    /**
     * BCrypt 哈希加密（指定成本因子）
     * @param password 明文密码
     * @param cost 成本因子（4-31）
     * @return 加密后的密码
     */
    public static String hash(String password, int cost) {
        validateCost(cost);
        return BcryptFunction.getInstance(cost).hash(password).getResult();
    }

    // ==================== 简化的验证方法 ====================

    /**
     * 验证密码是否匹配
     * @param rawPassword 明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verify(String rawPassword, String encodedPassword) {
        return BcryptFunction.getInstance(DEFAULT_COST).check(rawPassword, encodedPassword);
    }

    // ==================== 其他工具方法 ====================

    /**
     * 检查加密后的密码是否需要升级
     * @param encodedPassword 加密后的密码
     * @param targetCost 目标成本因子
     * @return 是否需要升级
     */
    public static boolean needsUpgrade(String encodedPassword, int targetCost) {
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return true;
        }
        int currentCost = extractCost(encodedPassword);
        return currentCost < targetCost;
    }

    /**
     * 从加密后的密码中提取成本因子
     * @param encodedPassword 加密后的密码
     * @return 成本因子
     */
    public static int extractCost(String encodedPassword) {
        if (encodedPassword != null && encodedPassword.startsWith("$2")) {
            int index = encodedPassword.indexOf("$", 3);
            if (index > 3) {
                try {
                    return Integer.parseInt(encodedPassword.substring(3, index));
                } catch (NumberFormatException e) {
                    // ignore
                }
            }
        }
        return 0;
    }

    // ==================== 辅助方法 ====================

    /**
     * 验证成本因子范围
     * @param cost 成本因子
     */
    private static void validateCost(int cost) {
        if (cost < MIN_COST || cost > MAX_COST) {
            throw new IllegalArgumentException(
                String.format("成本因子必须在 %d-%d 之间，当前值: %d", MIN_COST, MAX_COST, cost));
        }
    }
}
