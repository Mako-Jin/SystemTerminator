package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.ExecutionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 执行路径 Mapper
 * 对应表: flow_tbl_execution
 */
@Mapper
public interface ExecutionMapper extends BaseMapper<ExecutionEntity> {

    /**
     * 根据流程实例ID查询所有执行路径
     * XML: ExecutionMapper.xml
     */
    List<ExecutionEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 根据父执行路径查询子路径
     * XML: ExecutionMapper.xml
     */
    List<ExecutionEntity> selectByParentExecutionId(@Param("parentExecutionId") String parentExecutionId);

    /**
     * 查询流程实例的活跃执行路径
     * XML: ExecutionMapper.xml
     */
    List<ExecutionEntity> selectActiveByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 查询流程实例的并发分支
     * XML: ExecutionMapper.xml
     */
    List<ExecutionEntity> selectConcurrentBranches(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量取消执行路径
     * XML: ExecutionMapper.xml
     */
    int batchCancel(@Param("executionIds") List<String> executionIds);

    /**
     * 查询执行路径树（含父子关系）
     * XML: ExecutionMapper.xml
     */
    List<ExecutionEntity> selectExecutionTree(@Param("processInstanceId") String processInstanceId);
}
