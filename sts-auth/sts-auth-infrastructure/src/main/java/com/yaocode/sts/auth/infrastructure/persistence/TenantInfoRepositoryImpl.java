package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.TenantInfoEntity;
import com.yaocode.sts.auth.domain.repository.TenantInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.infrastructure.converter.TenantInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.TenantInfoDao;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

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

    @Override
    public Optional<TenantInfoEntity> findById(TenantId tenantId) {
        return Optional.empty();
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
}
