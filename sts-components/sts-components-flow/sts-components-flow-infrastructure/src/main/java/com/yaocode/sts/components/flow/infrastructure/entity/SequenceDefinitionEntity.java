package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 连线定义实体
 * 对应表: flow_tbl_sequence_definition
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_sequence_definition")
public class SequenceDefinitionEntity extends BaseEntity {

    @TableId
    private String sequenceId;

    private String processId;

    private String processKey;

    private String sequenceKey;

    private String sequenceName;

    private String sourceNodeId;

    private String sourceNodeKey;

    private String targetNodeId;

    private String targetNodeKey;

    private String conditionExpression;

    private Integer isDefault;

    private Integer sequenceVersion;

    @Version
    private Integer rev;
}
