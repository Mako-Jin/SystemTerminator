package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.RoleInfoApplicationConverter;
import com.yaocode.sts.auth.application.dto.RoleInfoDto;
import com.yaocode.sts.auth.application.service.RoleInfoApplicationService;
import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.repository.RoleInfoRepository;
import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:00
 */
@Service
public class RoleInfoApplicationServiceImpl implements RoleInfoApplicationService {

    @Resource
    private RoleDomainService roleDomainService;

    @Resource
    private RoleInfoRepository roleInfoRepository;

    @Resource
    private RoleInfoApplicationConverter roleInfoApplicationConverter;

    @Resource
    private TenantDomainService tenantDomainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(RoleInfoDto roleInfoDto) {
        TenantId tenantId = TenantId.of(roleInfoDto.getTenantId());
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        RoleCode roleCode = RoleCode.of(roleInfoDto.getRoleCode());
        if (roleDomainService.uniqueRoleCode(tenantId, roleCode)) {
            throw new IllegalArgumentException("角色编码已存在");
        }
        if (roleDomainService.uniqueRoleName(tenantId, roleInfoDto.getRoleName())) {
            throw new IllegalArgumentException("角色名称已存在");
        }
        if (Objects.nonNull(roleInfoDto.getParentId())) {
            RoleId parentId = RoleId.of(roleInfoDto.getParentId());
            if (!roleDomainService.validateRoleId(tenantId, parentId)) {
                throw new IllegalArgumentException("父角色不存在");
            }
        }
        roleInfoDto.setRoleId(IdFactory.generate().toString());
        RoleInfoEntity entity = roleInfoApplicationConverter.toEntity(roleInfoDto);
        return roleInfoRepository.save(entity).getValue();
    }

}
