package com.yaocode.sts.common.resources;

import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源自动初始化引擎
 * @author: Jin-LiangBo
 * @date: 2025年11月15日 21:12
 */
public class ResourcesAutoInitEngine {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesAutoInitEngine.class);

    public ResourcesAutoInitEngine(ResourcesConfigProperties resourcesConfigProperties) {
        logger.info("资源自动初始化引擎 == {}", resourcesConfigProperties.isEnabled());
    }
}
