package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.RoleInfoEntity;
import com.yaocode.sts.auth.domain.repository.RoleInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RoleInfoDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

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

    @Override
    public Optional<RoleInfoEntity> findById(RoleId roleId) {
        return Optional.empty();
    }

    @Override
    public RoleId save(RoleInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(RoleInfoEntity aggregate) {

    }
}
