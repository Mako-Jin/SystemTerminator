package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.FilterEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 过滤器 Mapper
 * 对应表: flow_tbl_filter
 */
@Mapper
public interface FilterMapper extends BaseMapper<FilterEntity> {

    /**
     * 根据所有者查询过滤器
     * XML: FilterMapper.xml
     */
    List<FilterEntity> selectByOwner(@Param("owner") String owner);

    /**
     * 查询公共过滤器
     * XML: FilterMapper.xml
     */
    List<FilterEntity> selectPublicFilters(@Param("resourceType") Integer resourceType);

    /**
     * 根据用户和组查询有权限的过滤器
     * XML: FilterMapper.xml
     */
    List<FilterEntity> selectAuthorizedFilters(
            @Param("userId") String userId,
            @Param("userGroups") List<String> userGroups,
            @Param("resourceType") Integer resourceType
    );

    /**
     * 更新使用次数
     * XML: FilterMapper.xml
     */
    int incrementUseCount(@Param("filterId") String filterId);

    /**
     * 批量删除过滤器
     * XML: FilterMapper.xml
     */
    int batchDelete(@Param("filterIds") List<String> filterIds);
}
