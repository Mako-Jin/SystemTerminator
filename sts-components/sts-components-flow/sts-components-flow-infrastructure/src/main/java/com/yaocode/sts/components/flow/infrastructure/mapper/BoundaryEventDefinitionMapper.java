package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.BoundaryEventDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 边界事件定义 Mapper
 * 对应表: flow_tbl_boundary_event_definition
 */
@Mapper
public interface BoundaryEventDefinitionMapper extends BaseMapper<BoundaryEventDefinitionEntity> {

    /**
     * 根据流程定义ID查询所有边界事件
     * XML: BoundaryEventDefinitionMapper.xml
     */
    List<BoundaryEventDefinitionEntity> selectByProcessId(@Param("processId") String processId);

    /**
     * 根据挂载节点查询边界事件
     * XML: BoundaryEventDefinitionMapper.xml
     */
    List<BoundaryEventDefinitionEntity> selectByAttachedNode(
            @Param("processId") String processId,
            @Param("attachedNodeKey") String attachedNodeKey
    );

    /**
     * 根据事件类型查询边界事件
     * XML: BoundaryEventDefinitionMapper.xml
     */
    List<BoundaryEventDefinitionEntity> selectByEventType(
            @Param("processId") String processId,
            @Param("eventType") Integer eventType
    );

    /**
     * 查询中断性边界事件
     * XML: BoundaryEventDefinitionMapper.xml
     */
    List<BoundaryEventDefinitionEntity> selectInterruptingEvents(@Param("processId") String processId);

    /**
     * 批量插入边界事件定义
     * XML: BoundaryEventDefinitionMapper.xml
     */
    int batchInsert(@Param("list") List<BoundaryEventDefinitionEntity> events);
}
