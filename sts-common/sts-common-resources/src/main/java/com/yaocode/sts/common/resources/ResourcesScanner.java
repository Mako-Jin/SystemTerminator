package com.yaocode.sts.common.resources;

import com.yaocode.sts.common.resources.handler.ResourcesHandler;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.properties.ResourcesConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 资源扫描类
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 21:02
 */
public class ResourcesScanner<A extends Annotation, M extends ResourcesModel> {

    private static final Logger logger = LoggerFactory.getLogger(ResourcesScanner.class);

    @Autowired
    private ResourcesConfigProperties resourcesConfigProperties;

    private List<ResourcesHandler<A, M>> resourceHandlers;

    public ResourcesScanner(ResourcesConfigProperties resourcesConfigProperties, List<ResourcesHandler<A, M>> resourceHandlers) {
        this.resourcesConfigProperties = resourcesConfigProperties;
        this.resourceHandlers = resourceHandlers;
    }

    public void scanResources() {
        logger.info(String.valueOf(resourceHandlers.size()));
        for (ResourcesHandler<A, M> handler : resourceHandlers) {
            try {
                List<A> resources = handler.scanResources();
                handler.convert(resources);
                logger.info("扫描到 {} 资源: {}", handler.getClass().getSimpleName(), resources.size());
            } catch (Exception e) {
                logger.error("资源扫描失败 - Handler: {}", handler.getClass().getSimpleName(), e);
            }
        }
    }

    public ResourcesConfigProperties getResourcesConfigProperties() {
        return resourcesConfigProperties;
    }

    public void setResourcesConfigProperties(ResourcesConfigProperties resourcesConfigProperties) {
        this.resourcesConfigProperties = resourcesConfigProperties;
    }

    public List<ResourcesHandler<A, M>> getResourceHandlers() {
        return resourceHandlers;
    }

    public void setResourceHandlers(List<ResourcesHandler<A, M>> resourceHandlers) {
        this.resourceHandlers = resourceHandlers;
    }
}
