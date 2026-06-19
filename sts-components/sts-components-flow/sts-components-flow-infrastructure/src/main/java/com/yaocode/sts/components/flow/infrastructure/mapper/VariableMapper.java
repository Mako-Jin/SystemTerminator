package com.yaocode.sts.components.flow.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaocode.sts.components.flow.infrastructure.entity.VariableEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运行时变量 Mapper
 * 对应表: flow_tbl_variable
 */
@Mapper
public interface VariableMapper extends BaseMapper<VariableEntity> {

    /**
     * 批量插入变量（高性能）
     * XML: VariableMapper.xml
     */
    int batchInsert(@Param("list") List<VariableEntity> variables);

    /**
     * 批量更新变量
     * XML: VariableMapper.xml
     */
    int batchUpdate(@Param("list") List<VariableEntity> variables);

    /**
     * 批量删除流程实例变量
     * XML: VariableMapper.xml
     */
    int batchDeleteByProcessInstanceIds(@Param("processInstanceIds") List<String> processInstanceIds);

    /**
     * 查询变量值（按流程实例和变量名列表）
     * XML: VariableMapper.xml
     */
    List<VariableEntity> selectByProcessInstanceAndNames(
            @Param("processInstanceId") String processInstanceId,
            @Param("variableNames") List<String> variableNames
    );


}
