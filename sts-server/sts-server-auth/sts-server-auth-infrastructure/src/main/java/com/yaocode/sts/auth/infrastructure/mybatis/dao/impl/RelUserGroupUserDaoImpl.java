package com.yaocode.sts.auth.infrastructure.mybatis.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaocode.sts.auth.infrastructure.mybatis.dao.RelUserGroupUserDao;
import com.yaocode.sts.auth.infrastructure.mybatis.mapper.RelUserGroupUserMapper;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupMemberPo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:23
 */
@Repository
public class RelUserGroupUserDaoImpl extends ServiceImpl<RelUserGroupUserMapper, RelUserGroupMemberPo> implements RelUserGroupUserDao {

    @Resource
    private RelUserGroupUserMapper relUserGroupUserMapper;

    @Override
    public RelUserGroupMemberPo getByUserGroupIdAndUserId(String tenantId, String userGroupId, String userId) {
        LambdaQueryWrapper<RelUserGroupMemberPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelUserGroupMemberPo::getTenantId, tenantId);
        wrapper.eq(RelUserGroupMemberPo::getUserGroupId, userGroupId);
        wrapper.eq(RelUserGroupMemberPo::getMemberId, userId);
        return relUserGroupUserMapper.selectOne(wrapper);
    }

    @Override
    public List<String> getByUserId(String userId) {
        LambdaQueryWrapper<RelUserGroupMemberPo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RelUserGroupMemberPo::getMemberId, userId);
        List<RelUserGroupMemberPo> relList = relUserGroupUserMapper.selectList(wrapper);
        return relList.stream().map(RelUserGroupMemberPo::getMemberId).toList();
    }
}
