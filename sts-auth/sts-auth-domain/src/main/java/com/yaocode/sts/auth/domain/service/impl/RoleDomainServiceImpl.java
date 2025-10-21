package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class RoleDomainServiceImpl implements RoleDomainService {
    @Override
    public boolean validateRoleId(RoleId roleId) {
        return false;
    }

    @Override
    public boolean validateRoleId(List<RoleId> roleIdList) {
        return false;
    }
}
