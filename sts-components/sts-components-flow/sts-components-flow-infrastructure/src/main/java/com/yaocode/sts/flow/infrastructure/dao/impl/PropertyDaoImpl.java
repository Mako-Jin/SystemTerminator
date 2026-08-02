package com.yaocode.sts.flow.infrastructure.dao.impl;

import com.yaocode.sts.flow.infrastructure.dao.PropertyDao;
import com.yaocode.sts.flow.infrastructure.entity.PropertyEntity;
import com.yaocode.sts.flow.infrastructure.mapper.PropertyMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDaoImpl extends BaseFlowDaoImpl<PropertyMapper, PropertyEntity> implements PropertyDao {

    @Resource
    private PropertyMapper propertyMapper;

}
