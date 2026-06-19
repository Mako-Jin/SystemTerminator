package com.yaocode.sts.components.flow.infrastructure.dao.impl;

import com.yaocode.sts.components.flow.infrastructure.dao.PropertyDao;
import com.yaocode.sts.components.flow.infrastructure.entity.PropertyEntity;
import com.yaocode.sts.components.flow.infrastructure.mapper.PropertyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDaoImpl extends BaseFlowDaoImpl<PropertyMapper, PropertyEntity> implements PropertyDao {

    @Resource
    private PropertyMapper propertyMapper;

}
