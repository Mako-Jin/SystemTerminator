package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.OrganizationInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.Repository;

/**
 * 组织结构仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:55
 */
public interface OrganizationRepository extends Repository<OrganizationInfoEntity, OrganizationId> {
}
