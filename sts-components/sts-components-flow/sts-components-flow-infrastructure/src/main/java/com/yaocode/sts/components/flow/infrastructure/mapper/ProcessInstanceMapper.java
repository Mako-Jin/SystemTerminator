package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.ProcessInstanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 流程实例 Mapper
 * 对应表: flow_tbl_process_instance
 */
@Mapper
public interface ProcessInstanceMapper extends BaseMapper<ProcessInstanceEntity> {

    /**
     * 分页查询流程实例（多条件动态查询）
     * XML: ProcessInstanceMapper.xml
     */
    List<ProcessInstanceEntity> selectPageList(
            @Param("page") Page<ProcessInstanceEntity> page,
            @Param("processKey") String processKey,
            @Param("businessKey") String businessKey,
            @Param("startUserId") String startUserId,
            @Param("status") Integer status,
            @Param("startTimeFrom") LocalDateTime startTimeFrom,
            @Param("startTimeTo") LocalDateTime startTimeTo,
            @Param("tenantId") String tenantId
    );

    /**
     * 批量更新流程实例状态
     * XML: ProcessInstanceMapper.xml
     */
    int batchUpdateStatus(
            @Param("processInstanceIds") List<String> processInstanceIds,
            @Param("status") Integer status,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * 查询超时流程实例（含父流程信息）
     * XML: ProcessInstanceMapper.xml
     */
    List<ProcessInstanceEntity> selectTimeoutInstancesWithParent(@Param("timeoutTime") LocalDateTime timeoutTime);

}
