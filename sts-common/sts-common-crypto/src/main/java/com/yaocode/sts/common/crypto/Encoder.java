package com.yaocode.sts.common.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface Encoder {

    /**
     * 对原始密码进行加密
     * @param rawPassword 原始密码
     * @return 加密后的密码字符串
     */
    String encode(CharSequence rawPassword);

}
