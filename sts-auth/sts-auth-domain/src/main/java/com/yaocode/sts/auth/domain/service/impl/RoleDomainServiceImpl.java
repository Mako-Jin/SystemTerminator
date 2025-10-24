package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.RoleInfoRepository;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 角色领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class RoleDomainServiceImpl implements RoleDomainService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Resource
    private RoleInfoRepository roleInfoRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean validateRoleId(RoleId roleId) {
        return false;
    }

    @Override
    public boolean validateRoleId(List<RoleId> roleIdList) {
        return false;
    }

    @Override
    public void associatedRole(TenantId tenantId, UserId userId, List<RoleId> roleIdList) {
        // 验证当前租户存在不存在
        Optional<TenantInfoEntity> tenantInfoEntity = tenantInfoRepository.findById(tenantId);
        if (tenantInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 验证角色id存在不存在
        List<RoleInfoEntity> roleInfoList = roleInfoRepository.findByIdList(tenantId, roleIdList);
        if (roleInfoList.size() != roleIdList.size()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 检查用户存在不存在
        Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findById(userId);
        if (userInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        roleInfoRepository.saveRelRoleUser(tenantId, userId, roleIdList);
    }
}
