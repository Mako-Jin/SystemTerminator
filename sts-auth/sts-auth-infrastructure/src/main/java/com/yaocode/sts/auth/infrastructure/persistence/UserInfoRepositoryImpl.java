package com.yaocode.sts.auth.infrastructure.persistence;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.repository.UserInfoRepository;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.infrastructure.converter.UserInfoConverter;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserInfoDao;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

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

    @Override
    public Optional<UserInfoEntity> findById(UserId userId) {
        UserInfoPo userPo = userInfoDao.getById(userId.getValue());
        return Optional.ofNullable(UserInfoConverter.INSTANCE.toEntity(userPo));
    }

    @Override
    public Optional<UserInfoEntity> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public UserId save(UserInfoEntity aggregate) {
        return null;
    }

    @Override
    public void delete(UserInfoEntity aggregate) {

    }
}
