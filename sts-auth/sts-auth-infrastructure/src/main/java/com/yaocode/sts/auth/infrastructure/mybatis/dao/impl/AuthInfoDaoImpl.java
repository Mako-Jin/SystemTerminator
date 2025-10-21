package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.AuthInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.AuthInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.AuthInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 菜单DaoImpl
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:53
 */
@Service
public class AuthInfoDaoImpl extends ServiceImpl<AuthInfoMapper, AuthInfoPo> implements AuthInfoDao {

    @Resource
    private AuthInfoMapper authInfoMapper;

}
