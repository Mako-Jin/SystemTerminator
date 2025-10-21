package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.service.OrganizationDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 组织机构领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class OrganizationDomainServiceImpl implements OrganizationDomainService {
    @Override
    public boolean validateOrganizationId(OrganizationId organizationId) {
        return false;
    }

    @Override
    public boolean validateOrganizationId(List<OrganizationId> organizationIdList) {
        return false;
    }
}
