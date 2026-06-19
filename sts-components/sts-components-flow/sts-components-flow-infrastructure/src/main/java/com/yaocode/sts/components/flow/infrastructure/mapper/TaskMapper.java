package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 任务 Mapper
 * 对应表: flow_tbl_task
 */
@Mapper
public interface TaskMapper extends BaseMapper<TaskEntity> {

    // ==================== XML 复杂查询 ====================

    /**
     * 分页查询任务（多条件）
     * XML: TaskMapper.xml
     */
    List<TaskEntity> selectPageList(
            @Param("page") Page<TaskEntity> page,
            @Param("assignee") String assignee,
            @Param("owner") String owner,
            @Param("processInstanceId") String processInstanceId,
            @Param("processKey") String processKey,
            @Param("status") Integer status,
            @Param("taskName") String taskName,
            @Param("dueDateFrom") LocalDateTime dueDateFrom,
            @Param("dueDateTo") LocalDateTime dueDateTo,
            @Param("tenantId") String tenantId
    );

    /**
     * 查询用户待办任务（含候选人信息）
     * XML: TaskMapper.xml
     */
    List<TaskEntity> selectUserTodoTasks(
            @Param("userId") String userId,
            @Param("userGroups") List<String> userGroups
    );

    /**
     * 批量更新任务状态
     * XML: TaskMapper.xml
     */
    int batchUpdateStatus(
            @Param("taskIds") List<String> taskIds,
            @Param("status") Integer status,
            @Param("finishTime") LocalDateTime finishTime
    );

}
