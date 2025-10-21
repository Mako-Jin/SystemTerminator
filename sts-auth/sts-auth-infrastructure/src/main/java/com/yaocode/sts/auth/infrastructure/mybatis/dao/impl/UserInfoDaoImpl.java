package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.UserInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.UserInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户信息Dao实现层
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:59
 */
@Service
public class UserInfoDaoImpl extends ServiceImpl<UserInfoMapper, UserInfoPo> implements UserInfoDao {

    @Resource
    private UserInfoMapper userInfoMapper;

}
