package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.UserGroupDto;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月28日 21:42
 */
public interface UserGroupApplicationService {

    /**
     * 单个新增用户组
     * @param userGroupDto 参数传输对象
     * @return java.lang.String
     */
    String singleAdd(UserGroupDto userGroupDto);

}
