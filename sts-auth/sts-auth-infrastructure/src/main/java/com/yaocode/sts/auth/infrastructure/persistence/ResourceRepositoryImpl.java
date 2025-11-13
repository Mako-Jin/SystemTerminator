package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.infrastructure.converter.ResourceConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ResourceDao;
import com.yaocode.sts.auth.infrastructure.po.ResourcePo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:25
 */
@Repository
public class ResourceRepositoryImpl implements ResourceRepository {

    @Resource
    private ResourceDao resourceDao;

    @Resource
    private ResourceConverter resourceConverter;

    @Override
    public Optional<ResourceEntity> findById(ResourceId resourceId) {
        return Optional.empty();
    }

    @Override
    public ResourceId save(ResourceEntity aggregate) {
        ResourcePo resourcePo = resourceConverter.toPo(aggregate);
        resourceDao.save(resourcePo);
        return ResourceId.of(resourcePo.getResourceId());
    }

    @Override
    public void delete(ResourceEntity aggregate) {

    }
}
