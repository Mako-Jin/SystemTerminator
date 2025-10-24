package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserGroupEntity;
import com.yaocode.sts.auth.domain.repository.UserGroupRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelUserGroupUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserGroupDao;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupUserPo;
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
}
