package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.AuthInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.AuthId;
import com.yaocode.sts.common.domain.Repository;

/**
 * 菜单信息仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:56
 */
public interface AuthInfoRepository extends Repository<AuthInfoEntity, AuthId> {
}
