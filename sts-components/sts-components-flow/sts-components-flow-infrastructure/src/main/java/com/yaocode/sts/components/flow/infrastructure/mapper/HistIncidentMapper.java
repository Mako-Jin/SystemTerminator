package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistIncidentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史异常 Mapper
 * 对应表: flow_tbl_hist_incident
 */
@Mapper
public interface HistIncidentMapper extends BaseMapper<HistIncidentEntity> {

    /**
     * 根据流程实例ID查询历史异常
     * XML: HistIncidentMapper.xml
     */
    List<HistIncidentEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量清理历史异常
     * XML: HistIncidentMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
