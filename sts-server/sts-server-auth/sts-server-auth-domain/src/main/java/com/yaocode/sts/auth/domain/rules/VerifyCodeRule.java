package com.yaocode.sts.auth.domain.rules;

import java.security.SecureRandom;

/**
 * 验证码规则接口
 * <p>
 * 定义验证码的生成、校验、格式描述等规则
 * </p>
 *
 * @author: Jin-LiangBo
 * @date: 2026年06月06日
 */
public interface VerifyCodeRule {

    /**
     * 校验验证码格式
     *
     * @param code 待校验的验证码
     * @return 是否符合规则
     */
    boolean validate(String code);

    /**
     * 获取规则描述（用于错误提示）
     */
    String getDescription();

    /**
     * 生成验证码
     */
    String generate();

    /**
     * 获取验证码长度
     */
    int getLength();

    /**
     * 是否区分大小写
     */
    boolean isCaseSensitive();

    /**
     * 安全随机数生成器（工具方法）
     */
    default SecureRandom getSecureRandom() {
        return SecureRandomHolder.INSTANCE;
    }

    /**
     * 懒加载 Holder（避免重复初始化）
     */
    class SecureRandomHolder {
        static final SecureRandom INSTANCE = new SecureRandom();
    }
}
