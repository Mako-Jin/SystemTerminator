package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.LoginRequestParams;
import com.yaocode.sts.common.web.model.ResultModel;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 认证鉴权接口
 * @author: Jin-LiangBo
 * @date: 2026年03月27日 16:09
 */
@RequestMapping("/auth")
public interface AuthApi {

    /**
     * 登录认证接口
     * @param params 请求参数
     * @return ResultModel
     */
    @PostMapping("/login")
    ResultModel<?> login(@RequestBody @Valid LoginRequestParams params);

}
