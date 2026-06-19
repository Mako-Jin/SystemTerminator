package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.HistVariableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 历史变量 Mapper
 * 对应表: flow_tbl_hist_variable
 */
@Mapper
public interface HistVariableMapper extends BaseMapper<HistVariableEntity> {

    /**
     * 根据流程实例ID查询历史变量
     * XML: HistVariableMapper.xml
     */
    List<HistVariableEntity> selectByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    /**
     * 根据流程实例ID和变量名查询
     * XML: HistVariableMapper.xml
     */
    HistVariableEntity selectByProcessInstanceAndName(
            @Param("processInstanceId") String processInstanceId,
            @Param("variableName") String variableName
    );

    /**
     * 批量清理历史变量
     * XML: HistVariableMapper.xml
     */
    int batchCleanup(
            @Param("removalTime") java.time.LocalDateTime removalTime,
            @Param("limit") Integer limit
    );
}
