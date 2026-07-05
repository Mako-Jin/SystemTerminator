package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.RegisterSourceEnums;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.basic.enums.AllowDenyEnums;
import com.yaocode.sts.common.basic.exception.NotAllowedException;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.common.tools.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 租户领域服务实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:17
 */
@Service
public class TenantDomainServiceImpl implements TenantDomainService {

    @Resource
    private TenantInfoRepository tenantInfoRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public boolean validateTenantId(List<TenantId> tenantIdList) {
        return false;
    }

    @Override
    public boolean validateTenantId(TenantId tenantId) {
        return tenantInfoRepository.findById(tenantId).isPresent();
    }

    @Override
    public boolean existsByTenantCode(TenantCode tenantCode) {
        return tenantInfoRepository.getByTenantCode(tenantCode).isPresent();
    }

    @Override
    public boolean existsByTenantName(String tenantName) {
        return tenantInfoRepository.getByTenantName(tenantName).isPresent();
    }

    @Override
    public void associatedTenantUser(TenantId tenantId, UserId userId, RegisterSourceEnums userAddType) {
        // 验证当前租户是否允许创建用户
        Optional<TenantInfoEntity> tenantInfoEntity = tenantInfoRepository.findById(tenantId);
        if (tenantInfoEntity.isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PARAMS_DATA_NOT_EXISTS);
        }
        if (RegisterSourceEnums.REGISTER == userAddType
                && Objects.equals(tenantInfoEntity.get().getAllowRegister(), AllowDenyEnums.DENY)
        ) {
            throw new NotAllowedException(AuthI18nKeyConstants.TENANT_NOT_ALLOW_REGISTER);
        }
        if (RegisterSourceEnums.ADMIN == userAddType
                && Objects.equals(tenantInfoEntity.get().getAllowAdd(), AllowDenyEnums.DENY)
        ) {
            throw new NotAllowedException(AuthI18nKeyConstants.TENANT_NOT_ALLOW_ADD_USER);
        }
        Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findById(userId);
        if (userInfoEntity.isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.PARAMS_DATA_NOT_EXISTS);
        }
        tenantInfoRepository.saveRelTenantUser(tenantId, userId);
    }

    /**
     * 解析租户ID
     */
    @Override
    public TenantId resolveTenantId(TenantId tenantId, TenantCode tenantCode, String domain) {
        // 1. 从tenantId解析
        if (Objects.nonNull(tenantId)) {
            Optional<TenantInfoEntity> tenant = tenantInfoRepository.findById(tenantId);
            if (tenant.isPresent()) {
                return tenant.get().getId();
            }
        }

        // 2. 从tenantCode解析
        if (Objects.nonNull(tenantCode)) {
            Optional<TenantInfoEntity> tenant = tenantInfoRepository.getByTenantCode(tenantCode);
            if (tenant.isPresent()) {
                return tenant.get().getId();
            }
        }

        // 3. 从domain解析
        if (StringUtils.hasText(domain)) {
            String code = resolveTenantCodeFromDomain(domain);
            if (StringUtils.hasText(code)) {
                Optional<TenantInfoEntity> tenant = tenantInfoRepository
                        .getByTenantCode(TenantCode.of(code));
                if (tenant.isPresent()) {
                    return tenant.get().getId();
                }
            }
        }

        // 4. 返回默认租户
        return tenantInfoRepository.getByTenantCode(TenantCode.of(AuthDomainConstants.DEFAULT_TENANT_CODE))
                .map(TenantInfoEntity::getId)
                .orElseThrow(() -> new RuntimeException(AuthI18nKeyConstants.DEFAULT_TENANT_NOT_EXIST));
    }

    /**
     * 从域名解析租户编码
     */
    public String resolveTenantCodeFromDomain(String domain) {
        if (!StringUtils.hasText(domain)) {
            return null;
        }
        // 示例：tenant.yourdomain.com → tenant
        String domainSuffix = AuthDomainConstants.DEFAULT_DOMAIN_SUFFIX;
        if (domain.endsWith(domainSuffix)) {
            String prefix = domain.substring(0, domain.length() - domainSuffix.length());
            if (!prefix.contains(SymbolConstants.DOT)) {
                return prefix;
            }
            String[] parts = prefix.split(RegexConstants.REGEX_DOT);
            return parts[parts.length - 1];
        }
        return null;
    }

    /**
     * 获取默认租户ID
     */
    public TenantId getDefaultTenantId() {
        return tenantInfoRepository.getByTenantCode(TenantCode.of(AuthDomainConstants.DEFAULT_TENANT_CODE))
                .map(TenantInfoEntity::getId)
                .orElseThrow(() -> new RuntimeException(AuthI18nKeyConstants.DEFAULT_TENANT_NOT_EXIST));
    }

}