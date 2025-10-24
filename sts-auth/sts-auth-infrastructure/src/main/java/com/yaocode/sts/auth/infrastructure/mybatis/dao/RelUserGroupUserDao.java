package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.RelUserGroupUserPo;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:23
 */
public interface RelUserGroupUserDao extends IService<RelUserGroupUserPo> {

    /**
     * 根据各个id查询用户组用户关联关系
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @param userId 用户id
     * @return RelUserGroupUserPo
     */
    RelUserGroupUserPo getByUserGroupIdAndUserId(String tenantId, String userGroupId, String userId);

}
