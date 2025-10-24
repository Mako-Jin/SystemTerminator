package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.RelTenantUserPo;

import java.util.List;

/**
 * 租户用户关联Dao
 * @author: Jin-LiangBo
 * @date: 2025年10月22日 23:28
 */
public interface RelTenantUserDao extends IService<RelTenantUserPo> {

    /**
     * 根据租户id和用户id查询关联关系
     * @param tenantId 租户id
     * @param userId 用户id
     * @return RelTenantUserPo
     */
    RelTenantUserPo getByTenantIdAndUserId(String tenantId, String userId);

    /**
     * 获取租户下的用户列表
     * @param tenantId 租户id
     * @return java.util.List<RelTenantUserPo>
     */
    List<RelTenantUserPo> getByTenantId(String tenantId);

}
