package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.RelOrganizationUserPo;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月23日 21:07
 */
public interface RelOrganizationUserDao extends IService<RelOrganizationUserPo> {

    /**
     * 根据各个id查询租户用户关联关系
     * @param tenantId 租户id
     * @param orgId 组织id
     * @param userId 用户id
     * @return RelOrganizationUserPo
     */
    RelOrganizationUserPo getByOrgIdAndUserId(String tenantId, String orgId, String userId);

}
