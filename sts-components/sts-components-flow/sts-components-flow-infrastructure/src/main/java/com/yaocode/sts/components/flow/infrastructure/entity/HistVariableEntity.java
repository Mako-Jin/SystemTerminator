package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史变量实体
 * 对应表: flow_tbl_hist_variable
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_variable")
public class HistVariableEntity extends BaseEntity {

    @TableId
    private String historyId;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String executionId;

    private String taskId;

    private String processId;

    private String processKey;

    private String activityInstanceId;

    private String variableName;

    private Integer variableType;

    private String textValue;

    private Long longValue;

    private Double doubleValue;

    private String binaryStorageFileId;

    private String binaryType;

    private Integer state;

    private LocalDateTime removeTime;

    private LocalDateTime removalTime;
}
