package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 22:13
 */
@Data
@TableName("auth_tbl_rel_role_user")
public class RelRoleUserPo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;
    /**
     * 组织id
     */
    private String roleId;
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
