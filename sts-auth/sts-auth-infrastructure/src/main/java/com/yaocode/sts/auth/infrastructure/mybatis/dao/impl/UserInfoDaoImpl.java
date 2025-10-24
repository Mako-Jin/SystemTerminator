package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.UserInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户信息Dao实现层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:59
 */
@Service
public class UserInfoDaoImpl extends ServiceImpl<UserInfoMapper, UserInfoPo> implements UserInfoDao {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfoPo getByUsername(List<String> userIdList, String username) {
        LambdaQueryWrapper<UserInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(!CollectionUtils.isEmpty(userIdList), UserInfoPo::getUserId, userIdList);
        wrapper.eq(UserInfoPo::getUsername, username);
        List<UserInfoPo> userInfoPoList = userInfoMapper.selectList(wrapper);
        return userInfoPoList.isEmpty() ? null : userInfoPoList.getFirst();
    }
}
