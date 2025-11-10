package com.yaocode.sts.common.db.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.yaocode.sts.common.db.DbMigrationManager;
import com.yaocode.sts.common.db.listener.DbMigrationInitListener;
import com.yaocode.sts.common.db.properties.DbMigrationProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月02日 15:47
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties({DbMigrationProperties.class})
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, MybatisPlusAutoConfiguration.class})
public class DbMigrationAutoConfiguration implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DbMigrationAutoConfiguration.class);

    private final DbMigrationProperties properties;

    public DbMigrationAutoConfiguration(DbMigrationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Bean
    @ConditionalOnMissingBean
    public DbMigrationManager databaseVersionManager(DataSource dataSource) {
        return new DbMigrationManager(dataSource, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public DbMigrationInitListener dbMigrationInitListener() {
        return new DbMigrationInitListener();
    }

}
