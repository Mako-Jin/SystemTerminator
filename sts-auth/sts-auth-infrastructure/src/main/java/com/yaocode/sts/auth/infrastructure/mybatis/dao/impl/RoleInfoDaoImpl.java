package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RoleInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RoleInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 角色DaoImpl
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:53
 */
@Service
public class RoleInfoDaoImpl extends ServiceImpl<RoleInfoMapper, RoleInfoPo> implements RoleInfoDao {

    @Resource
    private RoleInfoMapper roleInfoMapper;

}
