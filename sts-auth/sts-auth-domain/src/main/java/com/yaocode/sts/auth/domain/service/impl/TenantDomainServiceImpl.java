package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class TenantDomainServiceImpl implements TenantDomainService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Override
    public boolean validateTenantId(List<TenantId> tenantIdList) {
        return false;
    }

    @Override
    public boolean validateTenantId(TenantId tenantId) {
        return false;
    }

    @Override
    public boolean existsByTenantCode(TenantCode tenantCode) {
        return tenantInfoRepository.getByTenantCode(tenantCode.getValue()).isPresent();
    }

    @Override
    public boolean existsByTenantName(String tenantName) {
        return tenantInfoRepository.getByTenantName(tenantName).isPresent();
    }
}
