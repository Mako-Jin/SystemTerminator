package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.JobDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 作业定义 Mapper
 * 对应表: flow_tbl_job_definition
 */
@Mapper
public interface JobDefinitionMapper extends BaseMapper<JobDefinitionEntity> {

    /**
     * 根据流程定义ID查询所有作业定义
     * XML: JobDefinitionMapper.xml
     */
    List<JobDefinitionEntity> selectByProcessId(@Param("processId") String processId);

    /**
     * 根据流程定义ID和节点KEY查询
     * XML: JobDefinitionMapper.xml
     */
    JobDefinitionEntity selectByProcessIdAndNodeKey(
            @Param("processId") String processId,
            @Param("nodeKey") String nodeKey
    );

    /**
     * 根据作业类型查询
     * XML: JobDefinitionMapper.xml
     */
    List<JobDefinitionEntity> selectByJobType(@Param("jobType") Integer jobType);

    /**
     * 批量插入作业定义
     * XML: JobDefinitionMapper.xml
     */
    int batchInsert(@Param("list") List<JobDefinitionEntity> definitions);

    /**
     * 根据部署ID删除作业定义
     * XML: JobDefinitionMapper.xml
     */
    int deleteByDeploymentId(@Param("deploymentId") String deploymentId);
}
