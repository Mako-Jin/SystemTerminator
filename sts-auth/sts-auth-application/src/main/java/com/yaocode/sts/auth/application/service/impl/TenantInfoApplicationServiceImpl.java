package com.yaocode.sts.auth.application.service.impl;

import com.yaocode.sts.auth.application.converter.TenantInfoApplicationConverter;
import com.yaocode.sts.auth.application.dto.TenantInfoDto;
import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import com.yaocode.sts.auth.application.service.TenantInfoApplicationService;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.service.RoleDomainService;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 租户应用层
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 23:13
 */
@Service
public class TenantInfoApplicationServiceImpl implements TenantInfoApplicationService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Resource
    private TenantInfoApplicationConverter tenantInfoApplicationConverter;

    @Resource
    private TenantDomainService tenantDomainService;

    @Resource
    private RoleDomainService roleDomainService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String singleAdd(TenantInfoDto tenantInfoDto) {
        // 租户编码肯定不能重复，租户名称也不让重复
        TenantCode tenantCode = TenantCode.of(tenantInfoDto.getTenantCode());
        if (tenantDomainService.existsByTenantCode(tenantCode)) {
            throw new AuthServerException(AuthErrorCodeEnums.TENANT_CODE_EXISTS);
        }
        if (tenantDomainService.existsByTenantName(tenantInfoDto.getTenantName())) {
            throw new AuthServerException(AuthErrorCodeEnums.TENANT_NAME_EXISTS);
        }
        TenantInfoEntity tenantInfoEntity = tenantInfoApplicationConverter.toEntityForAdd(tenantInfoDto);
        // 4. 设置默认值/初始化状态
        tenantInfoEntity.initDefault();
        tenantInfoRepository.save(tenantInfoEntity);
        // 新建一个默认权限
        roleDomainService.createDefaultRole(tenantInfoEntity);

        return tenantInfoEntity.getId().getValue();
    }
}