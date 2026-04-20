package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.auth.infrastructure.converter.UserInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelOrganizationUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelRoleUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelTenantUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelUserGroupUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserInfoDao;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    @Resource
    private RelOrganizationUserDao relOrganizationUserDao;

    @Resource
    private RelRoleUserDao relRoleUserDao;

    @Resource
    private RelUserGroupUserDao relUserGroupUserDao;

    @Override
    public Optional<UserInfoEntity> findById(UserId userId) {
        UserInfoPo userPo = userInfoDao.getById(userId.getValue());
        return fillRelData(userPo);
    }

    @Override
    public Optional<UserInfoEntity> findById(TenantId tenantId, UserId userId) {
        UserInfoPo userPo = userInfoDao.getById(tenantId.getValue(), userId.getValue());
        return fillRelData(userPo);
    }

    @Override
    public Optional<UserInfoEntity> findByUsername(List<UserId> userIdList, Username username) {
        List<String> userIdStrList = userIdList.stream().map(Identifier::getValue).toList();
        UserInfoPo userPo = userInfoDao.getByUsername(userIdStrList, username.getValue());
        return fillRelData(userPo);
    }

    private Optional<UserInfoEntity> fillRelData(UserInfoPo userPo) {
        // String userId = userPo.getUserId();
        // CompletableFuture<List<String>> tenants =
        //         CompletableFuture.supplyAsync(() -> relTenantUserDao.getByUserId(userId));
        // CompletableFuture<List<String>> organizations =
        //         CompletableFuture.supplyAsync(() -> relOrganizationUserDao.getByUserId(userId));
        // CompletableFuture<List<String>> roles =
        //         CompletableFuture.supplyAsync(() -> relRoleUserDao.getByUserId(userId));
        // CompletableFuture<List<String>> groups =
        //         CompletableFuture.supplyAsync(() -> relUserGroupUserDao.getByUserId(userId));
        //
        // CompletableFuture.allOf(tenants, organizations, roles, groups).join();
        // return Optional.ofNullable(UserInfoConverter.INSTANCE.toEntity(
        //         userPo, tenants.get(), organizations.get(),  roles.get(), groups.get()
        // ));
        List<String> tenantIdList = relTenantUserDao.getByUserId(userPo.getUserId());
        List<String> organizationIdList = relOrganizationUserDao.getByUserId(userPo.getUserId());
        List<String> roleIdList = relRoleUserDao.getByUserId(userPo.getUserId());
        List<String> userGroupIdList = relUserGroupUserDao.getByUserId(userPo.getUserId());
        return Optional.ofNullable(UserInfoConverter.INSTANCE.toEntity(
                userPo, tenantIdList, organizationIdList, roleIdList, userGroupIdList
        ));
    }

    @Override
    public Optional<UserInfoEntity> findByUsername(TenantId tenantId, Username username) {
        UserInfoPo userPo = userInfoDao.getByUsername(tenantId.getValue(), username.getValue());
        return fillRelData(userPo);
    }

    @Override
    public Optional<UserInfoEntity> findByUsernameInTenantIdList(List<TenantId> tenantIdList, Username username) {
        List<String> tenantIdStrList = tenantIdList.stream().map(TenantId::getValue).toList();
        UserInfoPo userPo = userInfoDao.getByUsernameInTenantIdList(tenantIdStrList, username.getValue());
        return fillRelData(userPo);
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
