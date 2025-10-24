package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.auth.infrastructure.converter.UserInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelTenantUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserInfoDao;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import com.yaocode.sts.common.domain.model.Identifier;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 仓库接口
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 23:35
 */
@Repository
public class UserInfoRepositoryImpl implements UserInfoRepository {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RelTenantUserDao relTenantUserDao;

    @Override
    public Optional<UserInfoEntity> findById(UserId userId) {
        UserInfoPo userPo = userInfoDao.getById(userId.getValue());
        return Optional.ofNullable(UserInfoConverter.INSTANCE.toEntity(userPo));
    }

    @Override
    public Optional<UserInfoEntity> findByUsername(List<UserId> userIdList, Username username) {
        List<String> userIdStrList = userIdList.stream().map(Identifier::getValue).toList();
        UserInfoPo userPo = userInfoDao.getByUsername(userIdStrList, username.getValue());
        return Optional.ofNullable(UserInfoConverter.INSTANCE.toEntity(userPo));
    }

    @Override
    public List<UserId> getByTenantId(TenantId tenantId) {
        List<RelTenantUserPo> relList = relTenantUserDao.getByTenantId(tenantId.getValue());
        return relList.stream().map(RelTenantUserPo::getUserId).map(UserId::of).toList();
    }

    @Override
    public UserId save(UserInfoEntity aggregate) {
        UserInfoPo userPo = UserInfoConverter.INSTANCE.toPo(aggregate);
        userInfoDao.save(userPo);
        return aggregate.getId();
    }

    @Override
    public void delete(UserInfoEntity aggregate) {

    }
}
