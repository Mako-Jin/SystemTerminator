package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.application.dto.request.AuthenticationRequestDto;
import com.yaocode.sts.auth.application.dto.request.PreLoginRequestDto;
import com.yaocode.sts.auth.application.dto.response.AuthenticationResponseDto;
import com.yaocode.sts.auth.application.dto.response.PreLoginResponseDto;
import com.yaocode.sts.auth.application.service.AuthApplicationService;
import com.yaocode.sts.auth.interfaces.api.AuthApi;
import com.yaocode.sts.auth.interfaces.assembler.AuthenticationAssembler;
import com.yaocode.sts.auth.interfaces.model.params.PreLoginParams;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import com.yaocode.sts.auth.interfaces.model.vo.PreLoginVo;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证授权控制器
 * @author: Jin-LiangBo
 * @date: 2026年04月02日 18:19
 */
@RestController
@SubRequestMapping("/v1")
@ModuleResources(code = "000000000005", name = "认证授权接口", desc = "认证授权接口控制器", parent = { "000000000"})
public class AuthController implements AuthApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthenticationAssembler authenticationAssembler;

    @Resource
    private AuthApplicationService authenticationService;

    @Override
    public ResultModel<PreLoginVo> preLogin(PreLoginParams params) {
        PreLoginRequestDto preLoginDto = authenticationAssembler.toPreLoginDto(params);
        PreLoginResponseDto preLoginResponseDto = authenticationService.preLogin(preLoginDto);
        PreLoginVo preLoginVo = authenticationAssembler.toPreLoginVo(preLoginResponseDto);
        return ResultUtils.ok(preLoginVo);
    }

    @Override
    public ResultModel<?> login(LoginRequestParams loginRequestParams) {
        logger.info("收到登录请求, grantType={}", loginRequestParams.getCredential() != null ? loginRequestParams.getCredential().getGrantType() : "null");
        AuthenticationRequestDto authenticationDto = authenticationAssembler.toAuthenticationDto(loginRequestParams);
        AuthenticationResponseDto authenticationResultDto = authenticationService.authentication(authenticationDto);
        return ResultUtils.ok(authenticationResultDto);
    }

}
