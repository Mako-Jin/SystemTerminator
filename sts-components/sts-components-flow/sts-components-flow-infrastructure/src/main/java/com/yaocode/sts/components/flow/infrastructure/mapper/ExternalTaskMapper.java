package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.ExternalTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 外部任务 Mapper
 * 对应表: flow_tbl_external_task
 */
@Mapper
public interface ExternalTaskMapper extends BaseMapper<ExternalTaskEntity> {

    /**
     * 查询可拉取的外部任务（Worker 拉取）
     * XML: ExternalTaskMapper.xml
     */
    List<ExternalTaskEntity> selectFetchableTasks(
            @Param("topicName") String topicName,
            @Param("currentTime") LocalDateTime currentTime,
            @Param("limit") Integer limit
    );

    /**
     * 锁定外部任务
     * XML: ExternalTaskMapper.xml
     */
    int lockTask(
            @Param("externalTaskId") String externalTaskId,
            @Param("workerId") String workerId,
            @Param("lockExpireTime") LocalDateTime lockExpireTime,
            @Param("rev") Integer rev
    );

    /**
     * 分页查询外部任务
     * XML: ExternalTaskMapper.xml
     */
    List<ExternalTaskEntity> selectPageList(
            @Param("page") Page<ExternalTaskEntity> page,
            @Param("topicName") String topicName,
            @Param("status") Integer status,
            @Param("workerId") String workerId,
            @Param("processInstanceId") String processInstanceId
    );

}