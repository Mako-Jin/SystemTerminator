package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RoleInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RoleInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.RoleInfoPo;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
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

    @Override
    public RoleInfoPo getDefaultRole(String tenantId) {
        LambdaQueryWrapper<RoleInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleInfoPo::getTenantId, tenantId);
        wrapper.eq(RoleInfoPo::getIsDefault, OppositeEnums.YES.getCode());
        return roleInfoMapper.selectOne(wrapper);
    }

    @Override
    public RoleInfoPo getByRoleCode(String tenantId, String roleCode) {
        LambdaQueryWrapper<RoleInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleInfoPo::getTenantId, tenantId);
        wrapper.eq(RoleInfoPo::getRoleCode, roleCode);
        return roleInfoMapper.selectOne(wrapper);
    }

    @Override
    public RoleInfoPo getByRoleName(String tenantId, String roleName) {
        LambdaQueryWrapper<RoleInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleInfoPo::getTenantId, tenantId);
        wrapper.eq(RoleInfoPo::getRoleName, roleName);
        return roleInfoMapper.selectOne(wrapper);
    }
}
