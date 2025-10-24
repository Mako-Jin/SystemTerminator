package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.UserAddTypeEnums;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.service.TenantDomainService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.basic.exception.NotAllowedException;
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
        return tenantInfoRepository.getByTenantCode(tenantCode.getValue()).isPresent();
    }

    @Override
    public boolean existsByTenantName(String tenantName) {
        return tenantInfoRepository.getByTenantName(tenantName).isPresent();
    }

    @Override
    public void associatedTenantUser(TenantId tenantId, UserId userId, UserAddTypeEnums userAddType) {
        // 验证当前租户是否允许创建用户
        Optional<TenantInfoEntity> tenantInfoEntity = tenantInfoRepository.findById(tenantId);
        if (tenantInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        if (UserAddTypeEnums.REGISTER == userAddType
                && Objects.equals(tenantInfoEntity.get().getAllowRegister(), OppositeEnums.NO.getCode())
        ) {
            throw new NotAllowedException("当前租户不允许注册");
        }
        if (UserAddTypeEnums.ADD == userAddType
                && Objects.equals(tenantInfoEntity.get().getAllowAdd(), OppositeEnums.NO.getCode())
        ) {
            throw new NotAllowedException("当前租户不允许添加用户");
        }
        Optional<UserInfoEntity> userInfoEntity = userInfoRepository.findById(userId);
        if (userInfoEntity.isEmpty()) {
            throw new IllegalArgumentException("auth.params.data.not.exists");
        }
        tenantInfoRepository.saveRelTenantUser(tenantId, userId);
    }
}
