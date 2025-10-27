package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.OrganizationInfoDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.OrganizationInfoMapper;
import com.yaocode.sts.auth.infrastructure.po.OrganizationInfoPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 组织机构DaoImpl
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:53
 */
@Service
public class OrganizationInfoDaoImpl extends ServiceImpl<OrganizationInfoMapper, OrganizationInfoPo> implements OrganizationInfoDao {

    @Resource
    private OrganizationInfoMapper organizationInfoMapper;

    @Override
    public OrganizationInfoPo getById(String tenantId, String organizationId) {
        LambdaQueryWrapper<OrganizationInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationInfoPo::getTenantId, tenantId);
        wrapper.eq(OrganizationInfoPo::getOrganizationId, organizationId);
        return organizationInfoMapper.selectOne(wrapper);
    }

    @Override
    public OrganizationInfoPo getByOrganizationCode(String tenantId, String organizationCode) {
        LambdaQueryWrapper<OrganizationInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationInfoPo::getTenantId, tenantId);
        wrapper.eq(OrganizationInfoPo::getOrganizationCode, organizationCode);
        return organizationInfoMapper.selectOne(wrapper);
    }

    @Override
    public OrganizationInfoPo getByOrganizationName(String tenantId, String organizationName) {
        LambdaQueryWrapper<OrganizationInfoPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrganizationInfoPo::getTenantId, tenantId);
        wrapper.eq(OrganizationInfoPo::getOrganizationName, organizationName);
        return organizationInfoMapper.selectOne(wrapper);
    }
}
