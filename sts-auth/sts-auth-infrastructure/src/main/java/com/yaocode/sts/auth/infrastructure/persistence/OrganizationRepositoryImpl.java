package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.repository.OrganizationRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.OrganizationInfoDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 组织结构仓库实现类
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:00
 */
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    @Resource
    private OrganizationInfoDao organizationInfoDao;

    @Override
    public Optional<OrganizationInfoEntity> findById(OrganizationId organizationId) {
        return Optional.empty();
    }

    @Override
    public OrganizationId save(OrganizationInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(OrganizationInfoEntity aggregate) {

    }
}
