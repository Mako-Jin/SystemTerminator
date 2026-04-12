package com.yaocode.sts.common.i18n.config;

import com.yaocode.sts.common.i18n.DatabaseMessageSource;
import com.yaocode.sts.common.i18n.properties.I18nMessageProperties;
import com.yaocode.sts.common.i18n.repository.I18nMessageRepository;
import com.yaocode.sts.common.i18n.repository.impl.JdbcTemplateI18nMessageRepositoryImpl;
import com.yaocode.sts.common.i18n.repository.impl.JpaI18nMessageRepositoryImpl;
import com.yaocode.sts.common.i18n.repository.impl.MyBatisI18nMessageRepositoryImpl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 数据库存储国际化消息自动配置类
 * @author: Jin-LiangBo
 * @date: 2026年04月07日 18:45
 */
@Configuration
@EnableConfigurationProperties(I18nMessageProperties.class)
public class DatabaseI18nAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseI18nAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean
    public MessageSource messageSource(I18nMessageRepository repository, I18nMessageProperties properties) {
        return new DatabaseMessageSource(repository, properties);
    }

    /**
     * JdbcTemplate查询方法
     * @param jdbcTemplate JdbcTemplate
     * @return com.yaocode.sts.common.i18n.repository.I18nMessageRepository
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JdbcTemplate.class)
    public I18nMessageRepository jdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        return new JdbcTemplateI18nMessageRepositoryImpl(jdbcTemplate);
    }

    /**
     * MyBatis查询方法
     * @param sqlSessionFactory SqlSessionFactory
     * @return com.yaocode.sts.common.i18n.repository.I18nMessageRepository
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(SqlSessionFactory.class)
    public I18nMessageRepository myBatisRepository(SqlSessionFactory sqlSessionFactory) {
        return new MyBatisI18nMessageRepositoryImpl(sqlSessionFactory);
    }

    /**
     * JPA查询方法
     * @param entityManagerFactory EntityManagerFactory
     * @return com.yaocode.sts.common.i18n.repository.I18nMessageRepository
     */
    // @Bean
    // @ConditionalOnMissingBean
    // // @ConditionalOnBean(EntityManagerFactory.class)
    // @ConditionalOnClass(name = "org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean")
    // public I18nMessageRepository jpaRepository(EntityManagerFactory entityManagerFactory) {
    //     return new JpaI18nMessageRepositoryImpl(entityManagerFactory);
    // }

    /**
     * 最后的兜底方案：基于 DataSource 创建 JdbcTemplate
     * @param dataSource DataSource
     * @return com.yaocode.sts.common.i18n.repository.I18nMessageRepository
     */
    @Bean
    @ConditionalOnMissingBean
    public I18nMessageRepository fallbackRepository(DataSource dataSource) {
        logger.info("No specific ORM detected, using JdbcTemplate with DataSource");
        return new JdbcTemplateI18nMessageRepositoryImpl(new JdbcTemplate(dataSource));
    }

}
