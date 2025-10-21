package com.yaocode.sts.auth.starter.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisConfig
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:29
 */
@Configuration
@MapperScan("com.yaocode.sts.auth.infrastructure.mybatis.mapper")
public class MyBatisConfig {
}
