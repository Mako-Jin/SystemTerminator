package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.common.domain.Repository;

import java.util.Optional;

/**
 * db层接口
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:24
 */
public interface UserInfoRepository extends Repository<UserInfoEntity, UserId> {

    Optional<UserInfoEntity> findByUsername(String username);

}
