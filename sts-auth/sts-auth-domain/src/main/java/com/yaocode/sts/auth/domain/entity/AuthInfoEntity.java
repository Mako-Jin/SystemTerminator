package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.AuthId;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜单信息实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 23:01
 */
@Getter
@Setter
public class AuthInfoEntity extends AbstractAggregate<AuthId> {
    protected AuthInfoEntity(AuthId authId) {
        super(authId);
    }
}
