package com.yaocode.sts.common.crypto.password;

import com.yaocode.sts.common.crypto.Encoder;

public interface PasswordEncoder extends Encoder {

    /**
     * 验证原始密码与加密密码是否匹配
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

}
