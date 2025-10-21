package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.repository.UserGroupRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserGroupDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户组仓库实现类
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:00
 */
@Repository
public class UserGroupRepositoryImpl implements UserGroupRepository {

    @Resource
    private UserGroupDao userGroupDao;

    @Override
    public Optional<UserGroupEntity> findById(UserGroupId userGroupId) {
        return Optional.empty();
    }

    @Override
    public UserGroupId save(UserGroupEntity aggregate) {
        return null;
    }

    @Override
    public void delete(UserGroupEntity aggregate) {

    }
}
