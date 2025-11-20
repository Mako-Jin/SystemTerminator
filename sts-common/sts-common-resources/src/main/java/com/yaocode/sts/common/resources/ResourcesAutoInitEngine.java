package com.yaocode.sts.common.resources;

import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 资源自动初始化引擎
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 21:12
 */
public class ResourcesAutoInitEngine implements InitializingBean, ApplicationListener<ContextRefreshedEvent>,  ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesAutoInitEngine.class);

    private ApplicationContext applicationContext;

    private ResourcesScanner resourcesScanner;

    public ResourcesAutoInitEngine(ResourcesConfigProperties resourcesConfigProperties, ResourcesScanner resourcesScanner) {
        logger.info("资源自动初始化引擎 == {}", resourcesConfigProperties.isEnabled());
        this.resourcesScanner = resourcesScanner;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("资源自动初始化引擎 == 开始扫描资源");
        this.resourcesScanner.scanResources();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
