package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.infrastructure.converter.TenantInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelTenantUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.TenantInfoDao;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

/**
 * 租户信息仓库实现类
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:00
 */
@Repository
public class TenantInfoRepositoryImpl implements TenantInfoRepository {

    @Resource
    private TenantInfoDao tenantInfoDao;

    @Resource
    private RelTenantUserDao relTenantUserDao;

    @Override
    public Optional<TenantInfoEntity> findById(TenantId tenantId) {
        TenantInfoPo tenantInfoPo = tenantInfoDao.getById(tenantId.getValue());
        return Optional.ofNullable(TenantInfoConverter.INSTANCE.toEntity(tenantInfoPo));
    }

    @Override
    public TenantId save(TenantInfoEntity aggregate) {
        TenantInfoPo tenantInfoPo = TenantInfoConverter.INSTANCE.toPo(aggregate);
        tenantInfoDao.save(tenantInfoPo);
        return TenantId.of(tenantInfoPo.getTenantId());
    }

    @Override
    public void delete(TenantInfoEntity aggregate) {

    }

    @Override
    public Optional<TenantInfoEntity> getByTenantCode(String tenantCode) {
        TenantInfoPo tenantInfoPo = tenantInfoDao.getByTenantCode(tenantCode);
        return Optional.ofNullable(TenantInfoConverter.INSTANCE.toEntity(tenantInfoPo));
    }

    @Override
    public Optional<TenantInfoEntity> getByTenantName(String tenantName) {
        TenantInfoPo tenantInfoPo = tenantInfoDao.getByTenantName(tenantName);
        return Optional.ofNullable(TenantInfoConverter.INSTANCE.toEntity(tenantInfoPo));
    }

    @Override
    public void saveRelTenantUser(TenantId tenantId, UserId userId) {
        RelTenantUserPo relTenantUserPo = relTenantUserDao.getByTenantIdAndUserId(
                tenantId.getValue(), userId.getValue()
        );
        if (Objects.nonNull(relTenantUserPo)) {
            // TODO 感觉这块应该记个什么日志
            return;
        }
        relTenantUserPo = new RelTenantUserPo();
        relTenantUserPo.setRelId(IdFactory.generate());
        relTenantUserPo.setTenantId(tenantId.getValue());
        relTenantUserPo.setUserId(userId.getValue());
        relTenantUserDao.save(relTenantUserPo);
    }
}
