package com.yaocode.sts.auth.starter.controller;

import com.yaocode.sts.auth.interfaces.api.AuthApi;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
import com.yaocode.sts.common.resources.annotation.ModuleResources;
import com.yaocode.sts.common.web.annotation.SubRequestMapping;
import com.yaocode.sts.common.web.model.ResultModel;
import com.yaocode.sts.common.web.result.ResultUtils;
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

    @Override
    public ResultModel<?> login(LoginRequestParams loginRequestParams) {
        logger.info("收到登录请求, grantType={}",
                loginRequestParams.getCredential() != null ? loginRequestParams.getCredential().getGrantType() : "null");
        return ResultUtils.ok(loginRequestParams);
    }

}
