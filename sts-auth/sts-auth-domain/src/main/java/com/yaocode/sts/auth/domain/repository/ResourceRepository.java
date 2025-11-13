package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.ResourceEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.common.domain.Repository;

/**
 * 资源信息仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:00
 */
public interface ResourceRepository extends Repository<ResourceEntity, ResourceId> {
}
