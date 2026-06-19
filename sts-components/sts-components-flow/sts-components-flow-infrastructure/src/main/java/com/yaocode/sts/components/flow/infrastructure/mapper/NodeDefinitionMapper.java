package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.NodeDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 节点定义 Mapper
 * 对应表: flow_tbl_node_definition
 */
@Mapper
public interface NodeDefinitionMapper extends BaseMapper<NodeDefinitionEntity> {

    /**
     * 根据流程定义ID查询所有节点（按顺序）
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectByProcessId(@Param("processId") String processId);

    /**
     * 根据流程定义ID和节点KEY查询（指定版本）
     * XML: NodeDefinitionMapper.xml
     */
    NodeDefinitionEntity selectByProcessIdAndNodeKey(
            @Param("processId") String processId,
            @Param("nodeKey") String nodeKey,
            @Param("version") Integer version
    );

    /**
     * 根据流程定义ID和节点类型查询
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectByProcessIdAndNodeType(
            @Param("processId") String processId,
            @Param("nodeType") Integer nodeType
    );

    /**
     * 查询流程的所有开始节点
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectStartNodes(@Param("processId") String processId);

    /**
     * 查询流程的所有结束节点
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectEndNodes(@Param("processId") String processId);

    /**
     * 查询流程的所有用户任务节点
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectUserTaskNodes(@Param("processId") String processId);

    /**
     * 查询流程的所有服务任务节点
     * XML: NodeDefinitionMapper.xml
     */
    List<NodeDefinitionEntity> selectServiceTaskNodes(@Param("processId") String processId);

    /**
     * 批量插入节点定义
     * XML: NodeDefinitionMapper.xml
     */
    int batchInsert(@Param("list") List<NodeDefinitionEntity> nodes);

    /**
     * 根据流程定义ID和版本删除所有节点
     * XML: NodeDefinitionMapper.xml
     */
    int deleteByProcessIdAndVersion(
            @Param("processId") String processId,
            @Param("version") Integer version
    );

}
