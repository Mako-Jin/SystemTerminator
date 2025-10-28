package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.repository.RoleInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.RoleCode;
import com.yaocode.sts.auth.infrastructure.converter.RoleInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelRoleUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RoleInfoDao;
import com.yaocode.sts.auth.infrastructure.po.RelRoleUserPo;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import com.yaocode.sts.common.tools.id.IdFactory;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 角色信息仓库实现类
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 20:00
 */
@Repository
public class RoleInfoRepositoryImpl implements RoleInfoRepository {

    @Resource
    private RoleInfoDao roleInfoDao;

    @Resource
    private RelRoleUserDao relRoleUserDao;

    @Override
    public Optional<RoleInfoEntity> findById(RoleId roleId) {
        return Optional.empty();
    }


    @Override
    public RoleId save(RoleInfoEntity aggregate) {
        RoleInfoPo roleInfoPo = RoleInfoConverter.INSTANCE.toPo(aggregate);
        roleInfoDao.save(roleInfoPo);
        return aggregate.getId();
    }

    @Override
    public void delete(RoleInfoEntity aggregate) {

    }

    @Override
    public Optional<RoleInfoEntity> findById(TenantId tenantId, RoleId roleId) {
        RoleInfoPo roleInfoPo = roleInfoDao.getByRoleId(tenantId.getValue(), roleId.getValue());
        return Optional.ofNullable(RoleInfoConverter.INSTANCE.toEntity(roleInfoPo));
    }

    @Override
    public List<RoleInfoEntity> findByIdList(TenantId tenantId, List<RoleId> roleIdList) {
        return null;
    }

    @Override
    public Optional<RoleInfoEntity> findByRoleCode(TenantId tenantId, RoleCode roleCode) {
        RoleInfoPo roleInfoPo = roleInfoDao.getByRoleCode(tenantId.getValue(), roleCode.getValue());
        return Optional.ofNullable(RoleInfoConverter.INSTANCE.toEntity(roleInfoPo));
    }

    @Override
    public Optional<RoleInfoEntity> findByRoleName(TenantId tenantId, String roleName) {
        RoleInfoPo roleInfoPo = roleInfoDao.getByRoleName(tenantId.getValue(), roleName);
        return Optional.ofNullable(RoleInfoConverter.INSTANCE.toEntity(roleInfoPo));
    }

    @Override
    public void saveRelRoleUser(TenantId tenantId, UserId userId, List<RoleId> roleIdList) {
        List<String> roleIdStrList = roleIdList.stream().map(RoleId::getValue).toList();
        List<RelRoleUserPo> existRelList = relRoleUserDao.getByUserIdAndRoleIdList(
                tenantId.getValue(), userId.getValue(), roleIdStrList
        );
        List<String> existRoleIdList = existRelList.stream().map(RelRoleUserPo::getRoleId).toList();
        List<String> nonExistList = roleIdStrList.stream().filter(e -> !existRoleIdList.contains(e)).toList();
        if (CollectionUtils.isEmpty(nonExistList)) {
            return;
        }
        List<RelRoleUserPo> relRoleUserPoList = new ArrayList<>();
        nonExistList.forEach(e -> {
            RelRoleUserPo relRoleUserPo = new RelRoleUserPo();
            relRoleUserPo.setRelId(IdFactory.generate());
            relRoleUserPo.setTenantId(tenantId.getValue());
            relRoleUserPo.setRoleId(e);
            relRoleUserPo.setUserId(userId.getValue());
            relRoleUserPoList.add(relRoleUserPo);
        });
        relRoleUserDao.saveBatch(relRoleUserPoList);
    }

    @Override
    public Optional<RoleId> getDefaultRole(TenantId tenantId) {
        RoleInfoPo roleInfoPo = roleInfoDao.getDefaultRole(tenantId.getValue());
        if (Objects.isNull(roleInfoPo)) {
            return Optional.empty();
        }
        return Optional.of(RoleId.of(roleInfoPo.getRoleId()));
    }
}
