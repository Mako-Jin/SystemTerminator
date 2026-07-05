package com.yaocode.sts.auth.domain.repository;

import com.yaocode.sts.auth.domain.entity.UserProfileEntity;
import com.yaocode.sts.common.domain.valueobject.UserId;

import java.util.Optional;

public interface UserProfileRepository {

    Optional<UserProfileEntity> findByUserId(UserId userId);

}
