package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

    @Override
    public UserGroupPo getById(String tenantId, String userGroupId) {
        LambdaQueryWrapper<UserGroupPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupPo::getTenantId, tenantId);
        wrapper.eq(UserGroupPo::getUserGroupId, userGroupId);
        return userGroupMapper.selectOne(wrapper);
    }

    @Override
    public UserGroupPo getByUserGroupCode(String tenantId, String userGroupCode) {
        LambdaQueryWrapper<UserGroupPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupPo::getTenantId, tenantId);
        wrapper.eq(UserGroupPo::getUserGroupCode, userGroupCode);
        return userGroupMapper.selectOne(wrapper);
    }

    @Override
    public UserGroupPo getByUserGroupName(String tenantId, String userGroupName) {
        LambdaQueryWrapper<UserGroupPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserGroupPo::getTenantId, tenantId);
        wrapper.eq(UserGroupPo::getUserGroupName, userGroupName);
        return userGroupMapper.selectOne(wrapper);
    }
}
