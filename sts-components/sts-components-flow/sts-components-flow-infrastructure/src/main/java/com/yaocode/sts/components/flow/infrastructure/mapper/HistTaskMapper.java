package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.HistTaskEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史任务 Mapper
 * 对应表: flow_tbl_hist_task
 */
@Mapper
public interface HistTaskMapper extends BaseMapper<HistTaskEntity> {

    /**
     * 分页查询历史任务
     * XML: HistTaskMapper.xml
     */
    List<HistTaskEntity> selectPageList(
            @Param("page") Page<HistTaskEntity> page,
            @Param("assignee") String assignee,
            @Param("processInstanceId") String processInstanceId,
            @Param("processKey") String processKey,
            @Param("status") Integer status,
            @Param("startTimeFrom") LocalDateTime startTimeFrom,
            @Param("startTimeTo") LocalDateTime startTimeTo
    );

    /**
     * 批量清理历史任务
     * XML: HistTaskMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
