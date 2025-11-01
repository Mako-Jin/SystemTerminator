package com.yaocode.sts.auth.starter;

import com.yaocode.sts.common.web.annotation.EnableRestApiElapsed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 16:08
 */
@EnableRestApiElapsed
@SpringBootApplication
@ComponentScan(basePackages = "com.yaocode.sts")
public class AuthApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AuthApplication.class, args);
    }

}
