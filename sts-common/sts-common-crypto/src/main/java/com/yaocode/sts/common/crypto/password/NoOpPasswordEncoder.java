package com.yaocode.sts.common.crypto.password;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.logging.Logger;

/**
 * 无操作密码编码器（仅用于测试和遗留系统兼容）
 * <p><strong>WARNING: 此编码器不安全！不进行任何加密，直接返回明文密码！</strong></p>
 * <p>生产环境应使用 BCryptPasswordEncoder、SCryptPasswordEncoder 等安全编码器。</p>
 * @author: Jin-LiangBo
 * @date: 2026年06月02日
 */
public final class NoOpPasswordEncoder implements PasswordEncoder {

    private static final Logger logger = Logger.getLogger(NoOpPasswordEncoder.class.getName());

    /**
     * 警告标志，确保只警告一次
     */
    private static boolean warned = false;

    /**
     * 单例实例
     */
    private static final PasswordEncoder INSTANCE = new NoOpPasswordEncoder();

    /**
     * 私有构造函数，防止实例化
     */
    private NoOpPasswordEncoder() {
        warnOnce();
    }

    /**
     * 获取单例实例
     * @return NothingPasswordEncoder 实例
     */
    public static PasswordEncoder getInstance() {
        warnOnce();
        return INSTANCE;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        warnOnce();
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    /**
     * 仅警告一次
     */
    private static synchronized void warnOnce() {
        if (!warned) {
            logger.severe("===========================================");
            logger.severe("WARNING: 使用不安全的 NothingPasswordEncoder!");
            logger.severe("此编码器不进行任何加密，直接存储明文密码!");
            logger.severe("生产环境应使用 BCryptPasswordEncoder 或其他安全编码器!");
            logger.severe("===========================================");
            warned = true;
        }
    }
}