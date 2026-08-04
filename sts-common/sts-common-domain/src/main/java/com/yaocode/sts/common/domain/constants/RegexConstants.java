package com.yaocode.sts.common.domain.constants;

import java.util.regex.Pattern;

public interface RegexConstants {

    // ==================== 用户名相关正则 ====================

    /**
     * 用户名正则（只允许字母、数字、下划线）
     */
    String USERNAME_PATTERN = "^[a-zA-Z0-9_]+$";
    Pattern USERNAME_PATTERN_COMPILED = Pattern.compile(USERNAME_PATTERN);

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

    // ==================== 编码类正则 ====================

    /**
     * 编码正则（只允许字母、数字、中横线）
     */
    String CODE_PATTERN = "^[a-zA-Z0-9-]+$";
    Pattern CODE_PATTERN_COMPILED = Pattern.compile(CODE_PATTERN);

    // ========== 正则表达式相关常量 ==========
    // 正则表达式中点号的转义形式（用于匹配字面意义的点号）
    String REGEX_DOT = "\\.";


}
