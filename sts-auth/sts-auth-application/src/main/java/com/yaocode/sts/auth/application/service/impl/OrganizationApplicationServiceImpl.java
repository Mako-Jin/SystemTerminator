package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.OrganizationApplicationConverter;
import com.yaocode.sts.auth.application.dto.OrganizationDto;
import com.yaocode.sts.auth.application.service.OrganizationApplicationService;
import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.repository.OrganizationRepository;
import com.yaocode.sts.auth.domain.service.OrganizationDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 17:34
 */
@Service
public class OrganizationApplicationServiceImpl implements OrganizationApplicationService {

    @Resource
    private OrganizationRepository organizationRepository;

    @Resource
    private OrganizationDomainService organizationDomainService;

    @Resource
    private OrganizationApplicationConverter organizationApplicationConverter;

    @Resource
    private TenantDomainService tenantDomainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(OrganizationDto organizationDto) {
        TenantId tenantId = TenantId.of(organizationDto.getTenantId());
        if (!tenantDomainService.validateTenantId(tenantId)) {
            throw new IllegalArgumentException("租户不存在");
        }
        OrganizationCode organizationCode = OrganizationCode.of(organizationDto.getOrganizationCode());
        if (organizationDomainService.uniqueOrganizationCode(tenantId, organizationCode)) {
            throw new IllegalArgumentException("组织编码已存在");
        }
        if (organizationDomainService.uniqueOrganizationName(tenantId, organizationDto.getOrganizationName())) {
            throw new IllegalArgumentException("组织名称已存在");
        }
        if (Objects.nonNull(organizationDto.getParentId())) {
            OrganizationId parentId = OrganizationId.of(organizationDto.getParentId());
            if (!organizationDomainService.validateOrganizationId(tenantId, parentId)) {
                throw new IllegalArgumentException("父组织不存在");
            }
        }
        organizationDto.setOrganizationId(IdFactory.generate().toString());
        OrganizationInfoEntity entity = organizationApplicationConverter.toEntity(organizationDto);
        return organizationRepository.save(entity).getValue();
    }
}
