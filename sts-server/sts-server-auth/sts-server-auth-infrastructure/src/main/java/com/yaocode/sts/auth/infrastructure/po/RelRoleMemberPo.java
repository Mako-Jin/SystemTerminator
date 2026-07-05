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
@TableName("auth_tbl_rel_role_member")
public class RelRoleMemberPo {

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
     * 可指向用户或用户组
     */
    private String memberId;
    /**
     * 成员类型：USER（用户）, GROUP（用户组）
     */
    private Integer memberType;
    /**
     * 租户id
     */
    private String tenantId;
    /**
     * 生效时间
     */
    private LocalDateTime effectiveFrom;

    /**
     * 失效时间
     */
    private LocalDateTime effectiveTo;
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
