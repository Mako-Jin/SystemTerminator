package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaocode.sts.components.flow.infrastructure.entity.HistProcessInstanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史流程实例 Mapper
 * 对应表: flow_tbl_hist_process_instance
 */
@Mapper
public interface HistProcessInstanceMapper extends BaseMapper<HistProcessInstanceEntity> {

    /**
     * 分页查询历史流程实例
     * XML: HistProcessInstanceMapper.xml
     */
    List<HistProcessInstanceEntity> selectPageList(
            @Param("page") Page<HistProcessInstanceEntity> page,
            @Param("processKey") String processKey,
            @Param("businessKey") String businessKey,
            @Param("startUserId") String startUserId,
            @Param("status") Integer status,
            @Param("startTimeFrom") LocalDateTime startTimeFrom,
            @Param("startTimeTo") LocalDateTime startTimeTo
    );

    /**
     * 批量清理历史数据
     * XML: HistProcessInstanceMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
