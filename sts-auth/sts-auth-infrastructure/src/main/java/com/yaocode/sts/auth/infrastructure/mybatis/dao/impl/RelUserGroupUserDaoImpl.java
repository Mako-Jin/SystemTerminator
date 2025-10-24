package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelUserGroupUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelUserGroupUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupUserPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:23
 */
@Service
public class RelUserGroupUserDaoImpl extends ServiceImpl<RelUserGroupUserMapper, RelUserGroupUserPo> implements RelUserGroupUserDao {

    @Resource
    private RelUserGroupUserMapper relUserGroupUserMapper;

    @Override
    public RelUserGroupUserPo getByUserGroupIdAndUserId(String tenantId, String userGroupId, String userId) {
        LambdaQueryWrapper<RelUserGroupUserPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelUserGroupUserPo::getTenantId, tenantId);
        wrapper.eq(RelUserGroupUserPo::getUserGroupId, userGroupId);
        wrapper.eq(RelUserGroupUserPo::getUserId, userId);
        return relUserGroupUserMapper.selectOne(wrapper);
    }
}
