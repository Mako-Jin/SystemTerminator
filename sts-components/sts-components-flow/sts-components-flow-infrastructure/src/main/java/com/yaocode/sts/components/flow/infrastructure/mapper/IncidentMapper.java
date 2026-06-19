package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.IncidentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运行时异常 Mapper
 * 对应表: flow_tbl_incident
 */
@Mapper
public interface IncidentMapper extends BaseMapper<IncidentEntity> {

    /**
     * 分页查询异常
     * XML: IncidentMapper.xml
     */
    List<IncidentEntity> selectPageList(
            @Param("page") Page<IncidentEntity> page,
            @Param("status") Integer status,
            @Param("incidentType") Integer incidentType,
            @Param("processInstanceId") String processInstanceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * 查询异常链
     * XML: IncidentMapper.xml
     */
    List<IncidentEntity> selectIncidentChain(@Param("rootIncidentId") String rootIncidentId);

    /**
     * 批量解决异常
     * XML: IncidentMapper.xml
     */
    int batchResolve(
            @Param("incidentIds") List<String> incidentIds,
            @Param("resolvedBy") String resolvedBy,
            @Param("resolvedTime") LocalDateTime resolvedTime
    );

}
