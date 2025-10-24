package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 租户用户关联表
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:28
 */
@Data
@TableName("auth_tbl_rel_tenant_users")
public class RelTenantUserPo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 用户id
     */
    private String userId;

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
