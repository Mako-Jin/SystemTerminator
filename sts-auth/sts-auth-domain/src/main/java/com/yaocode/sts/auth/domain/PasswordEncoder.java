package com.yaocode.sts.auth.domain;

/**
 * 密码编码接口定义
 * @author: jin-LiangBo
 * @date: 2026年03月31日 9:31
 */
public interface PasswordEncoder {

    /**
     * 明文密码编码
     * @param plainPassword 明文密码
     * @return java.lang.String
     */
    String encode(String plainPassword);

    /**
     * 密码校验
     * @param plainPassword 原密码
     * @param encryptedPassword 加密密码
     * @return boolean
     */
    boolean matches(String plainPassword, String encryptedPassword);

}
