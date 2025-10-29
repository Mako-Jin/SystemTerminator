package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.repository.UserGroupRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.UserGroupCode;
import com.yaocode.sts.auth.infrastructure.converter.UserGroupConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelUserGroupUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserGroupDao;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupUserPo;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Objects;
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

    @Resource
    private RelUserGroupUserDao relUserGroupUserDao;

    @Resource
    private UserGroupConverter userGroupConverter;

    @Override
    public Optional<UserGroupEntity> findById(UserGroupId userGroupId) {
        return Optional.empty();
    }

    @Override
    public UserGroupId save(UserGroupEntity aggregate) {
        UserGroupPo userGroupPo = userGroupConverter.toPo(aggregate);
        userGroupDao.save(userGroupPo);
        return UserGroupId.of(userGroupPo.getUserGroupId());
    }

    @Override
    public void delete(UserGroupEntity aggregate) {

    }

    @Override
    public void saveRelUserGroupUser(TenantId tenantId, UserGroupId userGroupId, UserId userId) {
        RelUserGroupUserPo relUserGroupUserPo = relUserGroupUserDao.getByUserGroupIdAndUserId(
                tenantId.getValue(), userGroupId.getValue(), userId.getValue()
        );
        if (Objects.nonNull(relUserGroupUserPo)) {
            // TODO 感觉这块应该记个什么日志
            return;
        }
        relUserGroupUserPo = new RelUserGroupUserPo();
        relUserGroupUserPo.setRelId(IdFactory.generate());
        relUserGroupUserPo.setTenantId(tenantId.getValue());
        relUserGroupUserPo.setUserGroupId(userGroupId.getValue());
        relUserGroupUserPo.setUserId(userId.getValue());
        relUserGroupUserDao.save(relUserGroupUserPo);
    }

    @Override
    public Optional<UserGroupEntity> findById(TenantId tenantId, UserGroupId userGroupId) {
        UserGroupPo userGroupPo = userGroupDao.getById(tenantId.getValue(), userGroupId.getValue());
        return Optional.ofNullable(userGroupConverter.toEntity(userGroupPo));
    }

    @Override
    public Optional<UserGroupEntity> findByUserGroupCode(TenantId tenantId, UserGroupCode userGroupCode) {
        UserGroupPo userGroupPo = userGroupDao.getByUserGroupCode(tenantId.getValue(), userGroupCode.getValue());
        return Optional.ofNullable(userGroupConverter.toEntity(userGroupPo));
    }

    @Override
    public Optional<UserGroupEntity> findUserGroupName(TenantId tenantId, String userGroupName) {
        UserGroupPo userGroupPo = userGroupDao.getByUserGroupName(tenantId.getValue(), userGroupName);
        return Optional.ofNullable(userGroupConverter.toEntity(userGroupPo));
    }
}
