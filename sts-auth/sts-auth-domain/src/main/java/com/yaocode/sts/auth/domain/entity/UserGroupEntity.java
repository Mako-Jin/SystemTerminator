package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
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
    protected UserGroupEntity(UserGroupId userGroupId) {
        super(userGroupId);
    }

    private TenantId tenantId;

}
