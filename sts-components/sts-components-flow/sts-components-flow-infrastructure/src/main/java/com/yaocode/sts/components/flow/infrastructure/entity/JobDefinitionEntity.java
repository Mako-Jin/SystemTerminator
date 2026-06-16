package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * 作业定义实体
 * 对应表: flow_tbl_job_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "flow_tbl_job_definition", autoResultMap = true)
public class JobDefinitionEntity extends BaseEntity {

    @TableId
    private String jobDefinitionId;

    private String processId;

    private String processKey;

    private String nodeId;

    private String nodeKey;

    private Integer jobType;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> jobConfiguration;

    private Integer priority;

    private Integer suspensionState;

    private String deploymentId;
}
