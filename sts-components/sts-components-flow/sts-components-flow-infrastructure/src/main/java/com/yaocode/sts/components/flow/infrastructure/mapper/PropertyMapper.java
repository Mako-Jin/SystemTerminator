package com.yaocode.sts.components.flow.infrastructure.mapper;


import com.yaocode.sts.components.flow.infrastructure.entity.PropertyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统属性 Mapper
 * 对应表: flow_tbl_property
 */
@Mapper
public interface PropertyMapper extends BaseFlowMapper<PropertyEntity> {

    /**
     * 根据属性名称查询
     */
    PropertyEntity selectByPropertyName(@Param("propertyName") String propertyName);

    /**
     * 根据属性名称更新值
     */
    int updateValueByName(
            @Param("propertyName") String propertyName,
            @Param("propertyValue") String propertyValue
    );
}
