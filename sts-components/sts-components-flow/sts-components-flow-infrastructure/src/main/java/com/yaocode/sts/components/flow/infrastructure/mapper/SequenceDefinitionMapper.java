package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.SequenceDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 连线定义 Mapper
 * 对应表: flow_tbl_sequence_definition
 */
@Mapper
public interface SequenceDefinitionMapper extends BaseMapper<SequenceDefinitionEntity> {

    /**
     * 根据流程定义ID查询所有连线
     * XML: SequenceDefinitionMapper.xml
     */
    List<SequenceDefinitionEntity> selectByProcessId(@Param("processId") String processId);

    /**
     * 根据流程定义ID和源节点KEY查询出边
     * XML: SequenceDefinitionMapper.xml
     */
    List<SequenceDefinitionEntity> selectOutgoingBySource(
            @Param("processId") String processId,
            @Param("sourceNodeKey") String sourceNodeKey
    );

    /**
     * 根据流程定义ID和目标节点KEY查询入边
     * XML: SequenceDefinitionMapper.xml
     */
    List<SequenceDefinitionEntity> selectIncomingByTarget(
            @Param("processId") String processId,
            @Param("targetNodeKey") String targetNodeKey
    );

    /**
     * 查询流程的默认连线
     * XML: SequenceDefinitionMapper.xml
     */
    List<SequenceDefinitionEntity> selectDefaultFlows(@Param("processId") String processId);

    /**
     * 根据流程定义ID和连线KEY查询
     * XML: SequenceDefinitionMapper.xml
     */
    SequenceDefinitionEntity selectByProcessIdAndSequenceKey(
            @Param("processId") String processId,
            @Param("sequenceKey") String sequenceKey
    );

    /**
     * 批量插入连线定义
     * XML: SequenceDefinitionMapper.xml
     */
    int batchInsert(@Param("list") List<SequenceDefinitionEntity> sequences);

    /**
     * 根据流程定义ID和版本删除所有连线
     * XML: SequenceDefinitionMapper.xml
     */
    int deleteByProcessIdAndVersion(
            @Param("processId") String processId,
            @Param("version") Integer version
    );
}
