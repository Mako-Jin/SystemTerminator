package com.yaocode.sts.auth.infrastructure.mybatis.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaocode.sts.auth.infrastructure.po.TenantInfoPo;

/**
 * 租户信息dao
 * @author: Jin-LiangBo
 * @date: 2025年10月10日 21:01
 */
public interface TenantInfoDao extends IService<TenantInfoPo> {

    /**
     * 根据租户编码查询租户对象
     * @param tenantCode 租户编码
     * @return Optional<TenantInfoEntity>
     */
    TenantInfoPo getByTenantCode(String tenantCode);

    /**
     * 根据租户名称查询租户对象
     * @param tenantName 租户名
     * @return Optional<TenantInfoEntity>
     */
    TenantInfoPo getByTenantName(String tenantName);

}
