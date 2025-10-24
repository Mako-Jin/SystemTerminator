package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelRoleUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelRoleUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelRoleUserPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 22:16
 */
@Service
public class RelRoleUserDaoImpl extends ServiceImpl<RelRoleUserMapper, RelRoleUserPo> implements RelRoleUserDao {

    @Resource
    private RelRoleUserMapper relRoleUserMapper;

    @Override
    public List<RelRoleUserPo> getByUserIdAndRoleIdList(String tenantId, String userId, List<String> roleIdlIst) {
        LambdaQueryWrapper<RelRoleUserPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelRoleUserPo::getTenantId, tenantId);
        wrapper.eq(RelRoleUserPo::getUserId, userId);
        wrapper.in(RelRoleUserPo::getRoleId, roleIdlIst);
        return relRoleUserMapper.selectList(wrapper);
    }
}
