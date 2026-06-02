package com.yaocode.sts.common.crypto.password;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;


public class ArgonPasswordEncoder extends Argon2PasswordEncoder implements PasswordEncoder {
    /**
     * Constructs an Argon2 password encoder with the provided parameters.
     *
     * @param saltLength  the salt length (in bytes)
     * @param hashLength  the hash length (in bytes)
     * @param parallelism the parallelism
     * @param memory      the memory cost
     * @param iterations  the number of iterations
     */
    public ArgonPasswordEncoder(int saltLength, int hashLength, int parallelism, int memory, int iterations) {
        super(saltLength, hashLength, parallelism, memory, iterations);
    }
}
