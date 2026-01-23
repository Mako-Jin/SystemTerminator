package com.yaocode.sts.common.resources.services.handler.impl;

import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.resources.constants.IConstants;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import com.yaocode.sts.common.resources.model.ContactInfoModel;
import com.yaocode.sts.common.resources.model.ResourcesModel;
import com.yaocode.sts.common.resources.services.handler.ResourcesHandler;
import com.yaocode.sts.common.resources.utils.PropertyResolverUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 资源处理抽象实现类
 * @author: Jin-LiangBo
 * @date: 2026年01月20日 15:15
 */
public abstract class AbstractResourcesHandler<R extends Annotation, M extends ResourcesModel> implements ResourcesHandler<R, M> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractResourcesHandler.class);

    private ApplicationContext applicationContext;

    private PropertyResolverUtils propertyResolverUtils;

    public AbstractResourcesHandler(ApplicationContext applicationContext, PropertyResolverUtils propertyResolverUtils) {
        this.setApplicationContext(applicationContext);
        this.setPropertyResolverUtils(propertyResolverUtils);
    }

    public String getApplicationName() {
        return applicationContext.getEnvironment().getProperty(IConstants.APPLICATION_NAME_KEY);
    }

    @Override
    public String getDefaultResourceName() {
        return getApplicationName() + "_" + IConstants.DEFAULT_RESOURCE_NAME;
    }

    @Override
    public String getDefaultResourceCode() {
        return getApplicationName() + "_" + ResourceTypeEnums.createDefaultResourceCode(getSupportedAnnotation());
    }

    @Override
    public String getDefaultResourceDesc() {
        return getApplicationName() + "_" + IConstants.DEFAULT_RESOURCE_DESC;
    }

    @Override
    public String getDefaultResourceVersion() {
        return getApplicationName() + "_" + IConstants.DEFAULT_RESOURCE_VERSION;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<String, Object> scanResources() {
        return getApplicationContext().getBeansWithAnnotation(getSupportedAnnotation());
    }

    @Override
    public void buildDefaultResourcesModel(M model) {
        model.setCode(getDefaultResourceCode());
        model.setName(getDefaultResourceName());
        model.setDesc(getDefaultResourceDesc());
        model.setVersion(getDefaultResourceVersion());
        model.setIsEnabled(OppositeEnums.YES.getCode());
        model.setIsDeprecated(OppositeEnums.NO.getCode());
    }

    @Override
    public void buildResourcesModel(M resourcesModel, R resources) {
        this.convert(resourcesModel, resources);
        resolveProperty(resourcesModel::getCode, resourcesModel::setCode);
        resolveProperty(resourcesModel::getName, resourcesModel::setName);
        resolveProperty(resourcesModel::getDesc, resourcesModel::setDesc);
        resolveProperty(resourcesModel::getVersion, resourcesModel::setVersion);

        ContactInfoModel contactInfoModel = resourcesModel.getContactInfoModel();
        if (contactInfoModel != null) {
            resolveProperty(contactInfoModel::getName, contactInfoModel::setName);
            resolveProperty(contactInfoModel::getEmail, contactInfoModel::setEmail);
            resolveProperty(contactInfoModel::getDocsUrl, contactInfoModel::setDocsUrl);
            resourcesModel.setContactInfoModel(contactInfoModel);
        }
    }

    /**
     * 属性转换
     * @param getProperty 属性getter方法
     * @param setProperty 属性setter方法
     */
    protected void resolveProperty(
            Supplier<String> getProperty,
            Consumer<String> setProperty
    ) {
        resolveProperty(getProperty, setProperty, getPropertyResolverUtils());
    }

    /**
     * 属性转换
     * @param getProperty 属性getter方法
     * @param setProperty 属性setter方法
     * @param propertyResolverUtils 属性转换规则类
     */
    protected void resolveProperty(
            Supplier<String> getProperty,
            Consumer<String> setProperty,
            PropertyResolverUtils propertyResolverUtils
    ) {
        String value = getProperty.get();
        if (StringUtils.isNotBlank(value)) {
            setProperty.accept(propertyResolverUtils.resolve(value));
        }
    }

    @Override
    public PropertyResolverUtils getPropertyResolverUtils() {
        return propertyResolverUtils;
    }

    public void setPropertyResolverUtils(PropertyResolverUtils propertyResolverUtils) {
        this.propertyResolverUtils = propertyResolverUtils;
    }

    protected List<String> filterParentCode(List<String> parentCodeList, Set<String> systemCodeList) {
        if (parentCodeList.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> newParentCodeList = new ArrayList<>();
        for (String parentCode : parentCodeList) {
            if (StringUtils.isBlank(parentCode)) {
                continue;
            }
            String resolveParentCode = getPropertyResolverUtils().resolve(parentCode);
            if (systemCodeList.contains(resolveParentCode)) {
                newParentCodeList.add(resolveParentCode);
            } else {
                logger.warn("parent code {} not exist, pass it", resolveParentCode);
            }
        }
        return newParentCodeList;
    }

}
