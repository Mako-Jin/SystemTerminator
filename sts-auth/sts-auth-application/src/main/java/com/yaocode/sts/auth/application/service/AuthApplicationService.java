package com.yaocode.sts.auth.application.service;

import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.response.AuthenticationResponseDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;

/**
 * 认证服务接口
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 14:09
 */
public interface AuthApplicationService {

    PreLoginResponseDto preLogin(PreLoginRequestDto preLoginDto);


    /**
     * 认证接口
     * @param authenticationDto 认证参数
     * @return AuthenticationResultDto
     */
    AuthenticationResponseDto authentication(AuthenticationRequestDto authenticationDto);

}
