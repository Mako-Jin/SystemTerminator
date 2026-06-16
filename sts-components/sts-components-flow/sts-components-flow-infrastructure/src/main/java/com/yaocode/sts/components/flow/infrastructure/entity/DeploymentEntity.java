package com.yaocode.sts.components.flow.infrastructure.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 部署实体
 * 对应表: flow_tbl_deployment
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_deployment")
public class DeploymentEntity extends BaseEntity {

    @TableId
    private String deploymentId;

    private String deploymentName;

    private LocalDateTime deploymentTime;

    private Integer deploymentSource;
}
