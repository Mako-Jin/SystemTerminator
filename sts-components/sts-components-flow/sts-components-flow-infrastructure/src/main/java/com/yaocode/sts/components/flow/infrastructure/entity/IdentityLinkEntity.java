package com.yaocode.sts.components.flow.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 身份关联实体
 * 对应表: flow_tbl_identitylink
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flow_tbl_identity_link")
public class IdentityLinkEntity extends BaseEntity {

    @TableId
    private String identityLinkId;

    private Integer linkType;

    private String userId;

    private String userName;

    private String groupId;

    private String groupName;

    private String processInstanceId;

    private String taskId;

    private String processId;

    private String processKey;

    private String processInstanceKey;

    private String delegatedBy;

    private String delegatedByName;

    private LocalDateTime assignedTime;

    private LocalDateTime revokeTime;

    private Integer status;

    @Version
    private Integer rev;
}
