package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 历史身份关联实体
 * 对应表: flow_tbl_hist_identitylink
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_hist_identitylink")
public class HistIdentityLinkEntity extends BaseEntity {

    @TableId
    private String identityLinkId;

    private Integer linkType;

    private String userId;

    private String userName;

    private String groupId;

    private String groupName;

    private String processInstanceId;

    private String rootProcessInstanceId;

    private String taskId;

    private String processId;

    private String processKey;

    private String operationType;

    private String assignerId;

    private String assignerName;

    private LocalDateTime assignedTime;

    private LocalDateTime revokeTime;

    private LocalDateTime removalTime;
}
