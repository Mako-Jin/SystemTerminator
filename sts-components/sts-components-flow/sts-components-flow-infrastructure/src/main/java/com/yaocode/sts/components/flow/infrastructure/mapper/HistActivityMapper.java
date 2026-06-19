package com.yaocode.sts.components.flow.infrastructure.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 历史活动 Mapper
 * 对应表: flow_tbl_hist_activity
 */
@Mapper
public interface HistActivityMapper extends BaseMapper<HistActivityEntity> {

    /**
     * 根据流程实例ID查询历史活动
     * XML: HistActivityMapper.xml
     */
    List<HistActivityEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 查询活动的执行路径（按时间顺序）
     * XML: HistActivityMapper.xml
     */
    List<HistActivityEntity> selectActivityPath(@Param("processInstanceId") String processInstanceId);

    /**
     * 批量清理历史活动
     * XML: HistActivityMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") LocalDateTime removalTime,
            @Param("limit") Integer limit
    );

}
