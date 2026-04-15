package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.AuthenticationDto;
import com.yaocode.sts.auth.application.dto.AuthenticationResultDto;

/**
 * 认证服务接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:09
 */
public interface AuthenticationService {

    /**
     * 认证接口
     * @param authenticationDto 认证参数
     * @return AuthenticationResultDto
     */
    AuthenticationResultDto authentication(AuthenticationDto authenticationDto);

}
