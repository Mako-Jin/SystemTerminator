package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.TenantInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.TenantInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户信息Dao实现层
 * @author: Jin-LiangBo
 * @date: 2025年10月10日 21:01
 */
@Service
public class TenantInfoDaoImpl extends ServiceImpl<TenantInfoMapper, TenantInfoPo> implements TenantInfoDao {

    @Resource
    private TenantInfoMapper tenantInfoMapper;

    @Override
    public TenantInfoPo getByTenantCode(String tenantCode) {
        LambdaQueryWrapper<TenantInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TenantInfoPo::getTenantCode, tenantCode);
        return tenantInfoMapper.selectOne(wrapper);
    }

    @Override
    public TenantInfoPo getByTenantName(String tenantName) {
        LambdaQueryWrapper<TenantInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TenantInfoPo::getTenantName, tenantName);
        return tenantInfoMapper.selectOne(wrapper);
    }
}
