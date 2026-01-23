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
import com.yaocode.sts.common.basic.exception.DataExistsException;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 17:34
 */
@Service
public class OrganizationApplicationServiceImpl implements OrganizationApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationApplicationServiceImpl.class);

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

    @Override
    public OrganizationDto getById(String organizationId) {
        OrganizationId valueObjectId = OrganizationId.of(organizationId);
        // TODO 这块应该获取一下租户id，都不一定有自己租户id的权限，要么获取当前租户id，后端获取应该安全点
        Optional<OrganizationInfoEntity> organizationInfoEntity = organizationRepository.findById(valueObjectId);
        if (organizationInfoEntity.isEmpty()) {
            logger.warn("组织不存在, organizationId: {}", organizationId);
            throw new DataExistsException(String.format("组织不存在, ID: %s", organizationId));
        }
        // 转换为DTO
        return organizationApplicationConverter.toDto(organizationInfoEntity.get());
    }
}
