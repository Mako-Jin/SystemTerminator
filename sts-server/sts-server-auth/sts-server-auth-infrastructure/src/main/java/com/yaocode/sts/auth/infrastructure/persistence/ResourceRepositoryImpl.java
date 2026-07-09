package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.ResourceInfoEntity;
import com.yaocode.sts.auth.domain.repository.ResourceRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.infrastructure.converter.ResourceConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.ResourceDao;
import com.yaocode.sts.auth.infrastructure.po.ResourceInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
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
    public Optional<ResourceInfoEntity> findById(ResourceId resourceId) {
        return Optional.empty();
    }

    @Override
    public ResourceId save(ResourceInfoEntity aggregate) {
        ResourceInfoPo resourcePo = resourceConverter.toPo(aggregate);
        resourceDao.save(resourcePo);
        return ResourceId.of(resourcePo.getResourceId());
    }

    @Override
    public void delete(ResourceInfoEntity aggregate) {

    }

    @Override
    public List<ResourceId> batchSave(List<ResourceInfoEntity> resourceEntityList) {
        if (resourceEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ResourceInfoPo> resourcePoList = resourceConverter.toPoList(resourceEntityList);
        resourceDao.saveBatch(resourcePoList);
        return resourcePoList.stream().map(e -> ResourceId.of(e.getResourceId())).toList();
    }

    @Override
    public Optional<ResourceInfoEntity> findByEntity(ResourceInfoEntity resourceEntity) {
        ResourceInfoPo resourcePo = resourceConverter.toPo(resourceEntity);
        ResourceInfoPo po = resourceDao.getByPo(resourcePo);
        return Optional.ofNullable(resourceConverter.toEntity(po));
    }

    @Override
    public List<ResourceInfoEntity> findByEntityList(List<ResourceInfoEntity> resourceEntityList) {
        List<ResourceInfoPo> resourcePoList = resourceConverter.toPoList(resourceEntityList);
        List<ResourceInfoPo> dbResourcePoList = resourceDao.getByPoList(resourcePoList);
        return resourceConverter.toEntityList(dbResourcePoList);
    }

    @Override
    public ResourceId update(ResourceInfoEntity aggregate) {
        ResourceInfoPo resourcePo = resourceConverter.toPo(aggregate);
        resourceDao.updateById(resourcePo);
        return ResourceId.of(resourcePo.getResourceId());
    }

    @Override
    public List<ResourceId> batchUpdate(List<ResourceInfoEntity> resourceEntityList) {
        if (resourceEntityList.isEmpty()) {
            return Collections.emptyList();
        }
        List<ResourceInfoPo> resourcePoList = resourceConverter.toPoList(resourceEntityList);
        resourceDao.updateBatchById(resourcePoList);
        return resourcePoList.stream().map(e -> ResourceId.of(e.getResourceId())).toList();
    }
}