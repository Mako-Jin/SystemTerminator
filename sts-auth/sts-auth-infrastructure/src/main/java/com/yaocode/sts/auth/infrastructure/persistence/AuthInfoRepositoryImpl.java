package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.AuthInfoEntity;
import com.yaocode.sts.auth.domain.repository.AuthInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.AuthId;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.AuthInfoDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 菜单仓库实现
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:44
 */
@Repository
public class AuthInfoRepositoryImpl implements AuthInfoRepository {

    @Resource
    private AuthInfoDao authInfoDao;

    @Override
    public Optional<AuthInfoEntity> findById(AuthId authId) {
        return Optional.empty();
    }

    @Override
    public AuthId save(AuthInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(AuthInfoEntity aggregate) {

    }
}
