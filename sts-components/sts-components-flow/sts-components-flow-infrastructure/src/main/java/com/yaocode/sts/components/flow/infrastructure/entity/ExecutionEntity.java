package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 执行路径实体
 * 对应表: flow_tbl_execution
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_execution")
public class ExecutionEntity extends BaseEntity {

    @TableId
    private String executionId;

    private String processInstanceId;

    private String parentExecutionId;

    private String superExecutionId;

    private String nodeKey;

    private Integer isActive;

    private Integer isConcurrent;

    private Integer isScope;

    private Integer isEventScope;

    private Integer suspensionState;

    @Version
    private Integer rev;
}
