package com.yaocode.sts.auth.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 16:08
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.yaocode.sts.auth")
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
