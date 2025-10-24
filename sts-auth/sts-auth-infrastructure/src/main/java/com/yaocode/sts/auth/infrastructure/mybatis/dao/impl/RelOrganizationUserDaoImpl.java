package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelOrganizationUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelOrganizationUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelOrganizationUserPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:08
 */
@Service
public class RelOrganizationUserDaoImpl extends ServiceImpl<RelOrganizationUserMapper, RelOrganizationUserPo> implements RelOrganizationUserDao {

    @Resource
    private RelOrganizationUserMapper relOrganizationUserMapper;

    @Override
    public RelOrganizationUserPo getByOrgIdAndUserId(String tenantId, String orgId, String userId) {
        LambdaQueryWrapper<RelOrganizationUserPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelOrganizationUserPo::getTenantId, tenantId);
        wrapper.eq(RelOrganizationUserPo::getOrganizationId, orgId);
        wrapper.eq(RelOrganizationUserPo::getUserId, userId);
        return relOrganizationUserMapper.selectOne(wrapper);
    }
}
