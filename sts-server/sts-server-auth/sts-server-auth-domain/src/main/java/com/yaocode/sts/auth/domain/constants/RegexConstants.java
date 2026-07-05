package com.yaocode.sts.auth.domain.constants;

import java.util.regex.Pattern;

/**
 * 正则表达式常量类
 * @author: Jin-LiangBo
 * @date: 2026年07月04日
 */
public interface RegexConstants {

    // ==================== 颜色相关正则 ====================

    /**
     * 十六进制颜色代码正则（#RRGGBB格式）
     */
    String COLOR_HEX_PATTERN = "^#[0-9a-fA-F]{6}$";
    Pattern COLOR_HEX_PATTERN_COMPILED = Pattern.compile(COLOR_HEX_PATTERN);

    // ==================== 手机号相关正则 ====================

    /**
     * 中国手机号正则（11位数字，以1开头，第二位为2-9）
     */
    String PHONE_CHINA_PATTERN = "^1[2-9]\\d{9}$";
    Pattern PHONE_CHINA_PATTERN_COMPILED = Pattern.compile(PHONE_CHINA_PATTERN);

    /**
     * 空白字符正则（用于移除空格）
     */
    String WHITESPACE_PATTERN = "\\s+";
    Pattern WHITESPACE_PATTERN_COMPILED = Pattern.compile(WHITESPACE_PATTERN);

    /**
     * 手机号脱敏正则（保留前3位和后4位）
     */
    String PHONE_MASK_PATTERN = "(\\d{3})\\d{4}(\\d{4})";
    String PHONE_MASK_REPLACEMENT = "$1****$2";

    // ==================== 邮箱相关正则 ====================

    /**
     * 邮箱地址正则
     */
    String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9+_-]+(\\.[A-Za-z0-9+_-]+)*@" +
            "[A-Za-z0-9][A-Za-z0-9-]*(\\.[A-Za-z0-9][A-Za-z0-9-]*)*\\.[A-Za-z]{2,}$";
    Pattern EMAIL_PATTERN_COMPILED = Pattern.compile(EMAIL_PATTERN);

    // ==================== 用户名相关正则 ====================

    /**
     * 用户名正则（只允许字母、数字、下划线）
     */
    String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
    Pattern USERNAME_PATTERN_COMPILED = Pattern.compile(USERNAME_PATTERN);

    // ==================== 密码相关正则 ====================

    /**
     * 密码正则（至少8-20位，包含大小写字母、数字和特殊字符）
     */
    String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$^+_()%*?&.\\-=]){8,20}$";
    Pattern PASSWORD_PATTERN_COMPILED = Pattern.compile(PASSWORD_PATTERN);

    /**
     * 中文字符正则
     */
    String CHINESE_PATTERN = "[\\u4e00-\\u9fa5]";
    Pattern CHINESE_PATTERN_COMPILED = Pattern.compile(CHINESE_PATTERN);

    /**
     * 密码复杂度 - 包含字母
     */
    String PASSWORD_LETTER_PATTERN = ".*[a-zA-Z].*";

    /**
     * 密码复杂度 - 包含数字
     */
    String PASSWORD_NUMBER_PATTERN = ".*\\d.*";

    /**
     * 密码复杂度 - 包含小写字母
     */
    String PASSWORD_LOWERCASE_PATTERN = ".*[a-z].*";

    /**
     * 密码复杂度 - 包含大写字母
     */
    String PASSWORD_UPPERCASE_PATTERN = ".*[A-Z].*";

    /**
     * 密码复杂度 - 包含特殊字符
     */
    String PASSWORD_SPECIAL_PATTERN = ".*[!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?].*";

    // ==================== IP地址相关正则 ====================

    /**
     * IPv4地址正则
     */
    String IPV4_PATTERN = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    Pattern IPV4_PATTERN_COMPILED = Pattern.compile(IPV4_PATTERN);

    /**
     * IPv6地址正则
     */
    String IPV6_PATTERN = "^([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$|" +
            "^::([0-9a-fA-F]{1,4}:){0,6}[0-9a-fA-F]{1,4}$|" +
            "^([0-9a-fA-F]{1,4}:){1,7}:$";
    Pattern IPV6_PATTERN_COMPILED = Pattern.compile(IPV6_PATTERN);

    // ==================== 验证码相关正则 ====================

    /**
     * 6位数字验证码正则
     */
    String VERIFY_CODE_PATTERN = "^\\d{6}$";
    Pattern VERIFY_CODE_PATTERN_COMPILED = Pattern.compile(VERIFY_CODE_PATTERN);

    // ==================== 版本号相关正则 ====================

    /**
     * 版本号正则（主.次.修.建格式）
     */
    String VERSION_PATTERN = "^(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)$";
    Pattern VERSION_PATTERN_COMPILED = Pattern.compile(VERSION_PATTERN);

    // ==================== 编码类正则 ====================

    /**
     * 编码正则（只允许字母、数字、中横线）
     */
    String CODE_PATTERN = "^[a-zA-Z0-9-]+$";
    Pattern CODE_PATTERN_COMPILED = Pattern.compile(CODE_PATTERN);

    /**
     * 设备指纹正则（32-128位十六进制字符串）
     */
    String DEVICE_FINGERPRINT_PATTERN = "^[a-fA-F0-9]{32,128}$";
    Pattern DEVICE_FINGERPRINT_PATTERN_COMPILED = Pattern.compile(DEVICE_FINGERPRINT_PATTERN);

    // ========== 正则表达式相关常量 ==========
    // 正则表达式中点号的转义形式（用于匹配字面意义的点号）
    String REGEX_DOT = "\\.";

}
