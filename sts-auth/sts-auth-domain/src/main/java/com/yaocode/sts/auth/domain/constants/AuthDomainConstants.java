package com.yaocode.sts.auth.domain.constants;

import java.util.regex.Pattern;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 11:29
 */
public interface AuthDomainConstants {

    String DEFAULT_EN_STR = "DEFAULT";

    Pattern IDENTIFIER_CODE_REGEX = Pattern.compile("^[a-zA-Z0-9-]+$");

    // 锁定策略
    String LOCK_STRATEGY_FIXED = "FIXED";

    // 区域设置和时区
    String DEFAULT_LOCALE = "zh-CN";

    String DEFAULT_TIME_ZONE = "Asia/Shanghai";

    // ========== 验证码规则常量 ==========
    
    // 图形验证码正则：4-6位字母+数字
    Pattern CAPTCHA_CODE_PATTERN = Pattern.compile("^[A-Za-z\\d]{4,6}$");
    // 图形验证码默认长度
    int CAPTCHA_DEFAULT_LENGTH = 5;
    // 图形验证码字符集
    String CAPTCHA_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 邮箱验证码正则：6-8位字母+数字组合，至少包含一个字母和一个数字
    Pattern EMAIL_CODE_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,8}$");
    // 邮箱验证码默认长度
    int EMAIL_CODE_DEFAULT_LENGTH = 8;
    // 邮箱验证码字符集（与图形验证码相同）
    String EMAIL_CODE_CHARS = CAPTCHA_CHARS;

    // 短信验证码正则：6位纯数字
    Pattern SMS_CODE_PATTERN = Pattern.compile("^\\d{6}$");
    // 短信验证码长度
    int SMS_CODE_LENGTH = 6;
    // 短信验证码格式化模板（6位数字，前面补零）
    String SMS_CODE_FORMAT_PATTERN = "%06d";

    // ========== 安全相关常量 ==========
    // 默认最大登录尝试次数
    int DEFAULT_MAX_LOGIN_ATTEMPTS = 5;
    // 触发验证码的失败次数阈值
    int CAPTCHA_THRESHOLD = 3;

    // ========== 租户相关常量 ==========
    // 默认租户编码
    String DEFAULT_TENANT_CODE = "default";
    // 默认域名后缀（用于解析租户）
    String DEFAULT_DOMAIN_SUFFIX = ".yourdomain.com";

    // 邮箱最大长度
    int EMAIL_MAX_LENGTH = 254;
    // 用户名最小长度
    int USERNAME_MIN_LENGTH = 3;
    // 用户名最大长度
    int USERNAME_MAX_LENGTH = 30;
    // 密码过期提醒天数（提前7天提醒）
    int PASSWORD_EXPIRY_WARNING_DAYS = 7;
    // 默认密码版本号
    int DEFAULT_PASSWORD_VERSION = 1;
    // 验证码掩码保留位数
    int VERIFY_CODE_MASK_KEEP_LENGTH = 2;
    // 默认中国区号
    String DEFAULT_COUNTRY_CODE = "+86";

    /**
     * 国际区号前缀（+86）
     */
    String COUNTRY_CODE_PLUS = "+86";

    /**
     * 国际区号前缀（0086）
     */
    String COUNTRY_CODE_DOUBLE_ZERO = "0086";

    /**
     * 国际区号前缀（86）
     */
    String COUNTRY_CODE_SIMPLE = "86";

    /**
     * 默认主题色（蓝色）
     */
    String DEFAULT_PRIMARY_COLOR = "#1890ff";

    /**
     * 初始主版本号
     */
    int INITIAL_MAJOR = 0;

    /**
     * 初始次版本号
     */
    int INITIAL_MINOR = 0;

    /**
     * 初始修订版本号
     */
    int INITIAL_PATCH = 0;

    /**
     * 初始构建版本号
     */
    int INITIAL_BUILD = 1;

    /**
     * 默认验证码有效期（5分钟）
     */
    long DEFAULT_TTL_SECONDS = 300;

    /**
     * 脱敏显示时的掩码字符串
     */
    String MASK_STRING = "****";

    /**
     * 验证码掩码保留的前后位数
     */
    int MASK_KEEP_LENGTH = 2;

    /**
     * 验证码最小长度（用于判断是否需要脱敏）
     */
    int MIN_LENGTH_FOR_MASK = 4;
}