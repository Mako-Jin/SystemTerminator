package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.TenantInfoDto;

/**
 * 租户信息应用服务
 * @author: Jin-LiangBo
 * @date: 2025年10月16日 23:06
 */
public interface TenantInfoApplicationService {

    /**
     * 单个新增租户
     * @param tenantInfoDto 租户信息
     * @return java.lang.String
     */
    String singleAdd(TenantInfoDto tenantInfoDto);

}
