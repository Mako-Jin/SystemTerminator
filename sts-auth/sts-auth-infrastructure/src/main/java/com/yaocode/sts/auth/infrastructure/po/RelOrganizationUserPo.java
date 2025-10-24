package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:05
 */
@Data
@TableName("auth_tbl_rel_org_user")
public class RelOrganizationUserPo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;
    /**
     * 组织id
     */
    private String organizationId;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 创建者id
     */
    private String createUserId;
    /**
     * 创建者名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
