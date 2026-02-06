package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.ContactInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.common.domain.Repository;

/**
 * 联系人信息仓库接口
 * @author: Jin-LiangBo
 * @date: 2026年01月30日 17:11
 */
public interface ContactInfoRepository extends Repository<ContactInfoEntity, OrganizationId> {
}
