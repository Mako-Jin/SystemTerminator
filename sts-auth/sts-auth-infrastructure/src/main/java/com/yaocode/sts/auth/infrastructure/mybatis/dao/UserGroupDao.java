package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.UserGroupPo;

/**
 * 用户组Dao
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:50
 */
public interface UserGroupDao extends IService<UserGroupPo> {

    /**
     * 根据id查询数据
     * @param tenantId 租户id
     * @param userGroupId 用户组id
     * @return UserGroupPo
     */
    UserGroupPo getById(String tenantId, String userGroupId);

    /**
     * 根据用户组编码查询数据
     * @param tenantId 租户id
     * @param userGroupCode 用户组编码
     * @return UserGroupPo
     */
    UserGroupPo getByUserGroupCode(String tenantId, String userGroupCode);

    /**
     * 根据用户组名查询数据
     * @param tenantId 租户id
     * @param userGroupName 用户组名
     * @return UserGroupPo
     */
    UserGroupPo getByUserGroupName(String tenantId, String userGroupName);

}
