package com.yaocode.sts.auth.application.constants;

/**
 * 常量接口
 * @author: Jin-LiangBo
 * @date: 2026年04月20日 11:07
 */
public interface AuthApplicationConstants {

    // ==================== 登录配置常量 ====================
    /**
     * 默认最大登录尝试次数
     */
    int DEFAULT_MAX_LOGIN_ATTEMPTS = 5;

    /**
     * 颜色格式正则表达式（#RRGGBB）
     */
    String COLOR_FORMAT_REGEX = "^#[0-9a-fA-F]{6}$";

    /**
     * 默认主题色（蓝色）
     */
    String DEFAULT_PRIMARY_COLOR = "#1890ff";

}
