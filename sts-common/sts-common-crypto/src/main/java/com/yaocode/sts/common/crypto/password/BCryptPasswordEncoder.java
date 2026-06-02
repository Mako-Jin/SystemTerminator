package com.yaocode.sts.common.crypto.password;


import com.password4j.BcryptFunction;


public class BCryptPasswordEncoder implements PasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

    }

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        String salt = getSalt();
        return BcryptFunction.getInstance(10).hash(rawPassword.toString(), salt).getResult();
    }
}
