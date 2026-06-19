package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.AuditLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 审计日志 Mapper
 * 对应表: flow_tbl_audit_log
 */
@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLogEntity> {

    /**
     * 分页查询审计日志
     * XML: AuditLogMapper.xml
     */
    List<AuditLogEntity> selectPageList(
            @Param("page") Page<AuditLogEntity> page,
            @Param("userId") String userId,
            @Param("operationType") Integer operationType,
            @Param("processInstanceId") String processInstanceId,
            @Param("taskId") String taskId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    /**
     * 查询某实体的操作日志
     * XML: AuditLogMapper.xml
     */
    List<AuditLogEntity> selectByEntity(
            @Param("entityType") Integer entityType,
            @Param("entityId") String entityId
    );

    /**
     * 查询某操作ID下的所有变更
     * XML: AuditLogMapper.xml
     */
    List<AuditLogEntity> selectByOperationId(@Param("operationId") String operationId);

    /**
     * 批量清理审计日志
     * XML: AuditLogMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );
}
