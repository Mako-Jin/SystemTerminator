package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.OrganizationInfoPo;

/**
 * 组织机构Dao
 * @author: Jin-LiangBo
 * @date: 2025年10月14日 19:50
 */
public interface OrganizationInfoDao extends IService<OrganizationInfoPo> {

    /**
     * 根据租户id和组织id查询数据
     * @param tenantId 租户id
     * @param organizationId 组织id
     * @return OrganizationInfoPo
     */
    OrganizationInfoPo getById(String tenantId, String organizationId);
    /**
     * 根据租户id和组织Code查询数据
     * @param tenantId 租户id
     * @param organizationCode 组织Code
     * @return OrganizationInfoPo
     */
    OrganizationInfoPo getByOrganizationCode(String tenantId, String organizationCode);
    /**
     * 根据租户id和组织Name查询数据
     * @param tenantId 租户id
     * @param organizationName 组织Name
     * @return OrganizationInfoPo
     */
    OrganizationInfoPo getByOrganizationName(String tenantId, String organizationName);

}
