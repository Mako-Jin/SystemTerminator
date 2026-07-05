package com.yaocode.sts.auth.interfaces.model.params.login;

import jakarta.validation.Valid;
import lombok.Data;

/**
 * 登录请求参数
 * @author: Jin-LiangBo
 * @date: 2026年03月27日 16:41
 */
@Data
public class LoginRequestParams {

    /**
     * 认证类型
     */
    @Valid
    private AbstractLoginCredential credential;

}
