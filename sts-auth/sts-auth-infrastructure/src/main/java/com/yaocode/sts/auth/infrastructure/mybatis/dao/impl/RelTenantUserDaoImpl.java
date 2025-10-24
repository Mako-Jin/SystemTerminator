package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelTenantUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelTenantUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 租户用户关联DaoImpl
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:29
 */
@Service
public class RelTenantUserDaoImpl extends ServiceImpl<RelTenantUserMapper, RelTenantUserPo> implements RelTenantUserDao {

    @Resource
    private RelTenantUserMapper relTenantUserMapper;

    @Override
    public RelTenantUserPo getByTenantIdAndUserId(String tenantId, String userId) {
        LambdaQueryWrapper<RelTenantUserPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelTenantUserPo::getTenantId, tenantId);
        wrapper.eq(RelTenantUserPo::getUserId, userId);
        return relTenantUserMapper.selectOne(wrapper);
    }

    @Override
    public List<RelTenantUserPo> getByTenantId(String tenantId) {
        LambdaQueryWrapper<RelTenantUserPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelTenantUserPo::getTenantId, tenantId);
        return relTenantUserMapper.selectList(wrapper);
    }
}
