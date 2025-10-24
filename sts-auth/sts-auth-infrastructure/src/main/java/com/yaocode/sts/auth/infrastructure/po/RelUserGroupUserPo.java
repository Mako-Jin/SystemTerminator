package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:21
 */
@Data
@TableName("auth_tbl_rel_user_group_user")
public class RelUserGroupUserPo {

    /**
     * 关联id
     */
    @TableId
    private Long relId;
    /**
     * 用户组id
     */
    private String userGroupId;
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
