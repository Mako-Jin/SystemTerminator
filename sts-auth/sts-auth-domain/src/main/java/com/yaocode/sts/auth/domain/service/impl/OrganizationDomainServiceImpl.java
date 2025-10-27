package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.OrganizationRepository;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.OrganizationDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 组织机构领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class OrganizationDomainServiceImpl implements OrganizationDomainService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Resource
    private OrganizationRepository organizationRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean validateOrganizationId(TenantId tenantId, OrganizationId organizationId) {
        return organizationRepository.findById(tenantId, organizationId).isPresent();
    }

    @Override
    public boolean validateOrganizationId(List<OrganizationId> organizationIdList) {
        return false;
    }

    @Override
    public void associatedOrganizationUser(TenantId tenantId, OrganizationId organizationId, UserId userId) {
        // 验证当前租户存在不存在
        Optional<TenantInfoEntity> tenantInfoEntity = tenantInfoRepository.findById(tenantId);
        if (tenantInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 检查组织id存在不存在
        Optional<OrganizationInfoEntity> organizationInfoEntity = organizationRepository.findById(organizationId);
        if (organizationInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        // 检查用户存在不存在
        Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findById(userId);
        if (userInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        organizationRepository.saveRelOrganizationUser(tenantId, organizationId, userId);
    }

    @Override
    public boolean uniqueOrganizationCode(TenantId tenantId, OrganizationCode organizationCode) {
        return organizationRepository.findByOrganizationCode(tenantId, organizationCode).isPresent();
    }

    @Override
    public boolean uniqueOrganizationName(TenantId tenantId, String organizationName) {
        return organizationRepository.findByOrganizationName(tenantId, organizationName).isPresent();
    }
}
