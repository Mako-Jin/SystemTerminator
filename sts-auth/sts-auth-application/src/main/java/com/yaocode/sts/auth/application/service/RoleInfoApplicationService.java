package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.RoleInfoDto;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 20:49
 */
public interface RoleInfoApplicationService {

    /**
     * 单个新增角色
     * @param roleInfoDto 数据传输对象
     * @return java.lang.String
     */
    String singleAdd(RoleInfoDto roleInfoDto);

}
