package com.yaocode.sts.file.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatisConfig
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:29
 */
@Configuration
@MapperScan("com.yaocode.sts.file.infrastructure.mapper")
public class MyBatisPlusConfig {
}
