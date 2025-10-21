package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.UserGroupDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.UserGroupMapper;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户组DaoImpl
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:53
 */
@Service
public class UserGroupDaoImpl extends ServiceImpl<UserGroupMapper, UserGroupPo> implements UserGroupDao {

    @Resource
    private UserGroupMapper userGroupMapper;

}
