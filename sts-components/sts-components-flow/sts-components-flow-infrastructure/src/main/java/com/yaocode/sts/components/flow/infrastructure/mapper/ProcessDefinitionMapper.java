package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.ProcessDefinitionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程定义 Mapper
 * 对应表: flow_tbl_process_definition
 *
 * 简单查询使用 MyBatis-Plus Lambda，复杂查询使用 XML
 */
@Mapper
public interface ProcessDefinitionMapper extends BaseMapper<ProcessDefinitionEntity> {

    /**
     * 分页查询流程定义（动态条件）
     * XML: ProcessDefinitionMapper.xml
     */
    List<ProcessDefinitionEntity> selectPageList(
            @Param("page") com.baomidou.mybatisplus.extension.plugins.pagination.Page<ProcessDefinitionEntity> page,
            @Param("processKey") String processKey,
            @Param("processName") String processName,
            @Param("status") Integer status,
            @Param("isLatest") Integer isLatest
    );

    /**
     * 统计流程定义数量（按状态）
     * XML: ProcessDefinitionMapper.xml
     */
    List<ProcessDefinitionEntity> countByStatus(@Param("processKey") String processKey);

    /**
     * 批量更新最新版本标记
     * XML: ProcessDefinitionMapper.xml
     */
    int batchUpdateLatestFlag(@Param("processKeyList") List<String> processKeyList);

}
