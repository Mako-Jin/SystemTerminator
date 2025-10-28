package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.constants.CommonConstants;
import com.yaocode.sts.auth.domain.constants.RoleConstants;
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
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
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
    public boolean validateRoleId(TenantId tenantId, RoleId roleId) {
        return roleInfoRepository.findById(tenantId, roleId).isPresent();
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

    @Override
    public void createDefaultRole(TenantInfoEntity tenantInfoEntity) {
        String tenantCode = tenantInfoEntity.getTenantCode().getValue();
        String defaultRoleCode = tenantCode
                .concat(CommonConstants.SYMBOL_HYPHEN)
                .concat(CommonConstants.DEFAULT_EN_STR);
        RoleCode roleCode = RoleCode.of(defaultRoleCode);
        // 校验角色编码不可重复
        Optional<RoleInfoEntity> roleCodeEntity = roleInfoRepository
                .findByRoleCode(tenantInfoEntity.getId(), roleCode);
        if (roleCodeEntity.isPresent()) {
            throw new IllegalArgumentException("auth.data.is.exists");
        }
        String roleName = tenantInfoEntity.getTenantName().concat(RoleConstants.DEFAULT_ROLE_NAME);
        // 校验角色名称不可重复
        Optional<RoleInfoEntity> roleNameEntity = roleInfoRepository
                .findByRoleName(tenantInfoEntity.getId(), roleName);
        if (roleNameEntity.isPresent()) {
            throw new IllegalArgumentException("auth.data.is.exists");
        }
        RoleId roleId = RoleId.nextId();
        RoleInfoEntity roleInfoEntity = new RoleInfoEntity(roleId);
        roleInfoEntity.setRoleCode(roleCode);
        roleInfoEntity.setRoleName(roleName);
        roleInfoEntity.setRoleDesc(roleName);
        roleInfoEntity.setTenantId(tenantInfoEntity.getId());
        roleInfoEntity.setIsDefault(OppositeEnums.YES.getCode());
        roleInfoRepository.save(roleInfoEntity);
    }

    @Override
    public boolean uniqueRoleCode(TenantId tenantId, RoleCode roleCode) {
        return roleInfoRepository.findByRoleCode(tenantId, roleCode).isPresent();
    }

    @Override
    public boolean uniqueRoleName(TenantId tenantId, String roleName) {
        return roleInfoRepository.findByRoleName(tenantId, roleName).isPresent();
    }
}
