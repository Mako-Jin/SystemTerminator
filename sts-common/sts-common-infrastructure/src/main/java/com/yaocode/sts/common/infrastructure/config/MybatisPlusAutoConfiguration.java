package com.yaocode.sts.common.infrastructure.config;

import com.yaocode.sts.common.infrastructure.persistence.AuditMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusAutoConfiguration {

    @Bean
    public AuditMetaObjectHandler auditMetaObjectHandler() {
        return new AuditMetaObjectHandler();
    }

}
