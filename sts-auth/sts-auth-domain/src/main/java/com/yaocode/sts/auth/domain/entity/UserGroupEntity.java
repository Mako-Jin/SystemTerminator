package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户组领域对象
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:57
 */
@Getter
@Setter
public class UserGroupEntity extends AbstractAggregate<UserGroupId> {

    public UserGroupEntity(UserGroupId userGroupId) {
        super(userGroupId);
    }

    /**
     * 用户组编码
     */
    private UserGroupCode userGroupCode;

    /**
     * 用户组名
     */
    private String userGroupName;
    /**
     * 用户组描述
     */
    private String userGroupDesc;
    /**
     * 父id
     */
    private String parentId;

    /**
     * 租户id
     */
    private TenantId tenantId;

}
