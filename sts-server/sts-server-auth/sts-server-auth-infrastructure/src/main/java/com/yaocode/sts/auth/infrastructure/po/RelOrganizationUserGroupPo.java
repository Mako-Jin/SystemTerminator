package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("auth_tbl_rel_org_user_group")
public class RelOrganizationUserGroupPo {

    @TableId
    private Long relId;

    /**
     * 用户组ID
     */
    private String userGroupId;

    /**
     * 组织ID
     */
    private String organizationId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 关联类型：INHERIT（组织成员自动继承）, MANUAL（手动分配）
     */
    private Integer relationType;

    /**
     * 是否自动同步组织成员到用户组
     */
    private Integer autoSync;

}
