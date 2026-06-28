package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelRoleUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelRoleUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelRoleMemberPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 22:16
 */
@Repository
public class RelRoleUserDaoImpl extends ServiceImpl<RelRoleUserMapper, RelRoleMemberPo> implements RelRoleUserDao {

    @Resource
    private RelRoleUserMapper relRoleUserMapper;

    @Override
    public List<RelRoleMemberPo> getByUserIdAndRoleIdList(String tenantId, String userId, List<String> roleIdlIst) {
        LambdaQueryWrapper<RelRoleMemberPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelRoleMemberPo::getTenantId, tenantId);
        wrapper.eq(RelRoleMemberPo::getMemberId, userId);
        wrapper.in(RelRoleMemberPo::getRoleId, roleIdlIst);
        return relRoleUserMapper.selectList(wrapper);
    }

    @Override
    public List<String> getByUserId(String userId) {
        LambdaQueryWrapper<RelRoleMemberPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelRoleMemberPo::getMemberId, userId);
        List<RelRoleMemberPo> relList = relRoleUserMapper.selectList(wrapper);
        return relList.stream().map(RelRoleMemberPo::getMemberId).toList();
    }
}
