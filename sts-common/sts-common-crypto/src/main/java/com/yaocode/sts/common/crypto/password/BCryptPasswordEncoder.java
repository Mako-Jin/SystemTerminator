package com.yaocode.sts.common.crypto.password;


/**
 * BCrypt密码编码器
 * <p>参考Spring Security的BCryptPasswordEncoder设计</p>
 */
public class BCryptPasswordEncoder extends org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder implements PasswordEncoder {

}