package com.yaocode.sts.auth.interfaces.api;

import com.yaocode.sts.auth.interfaces.model.params.PreLoginParams;
import com.yaocode.sts.auth.interfaces.model.params.login.LoginRequestParams;
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
     * 预登录接口
     * <p>
     * 功能：
     * 1. 尝试Remember Me自动登录
     * 2. 根据identifier（用户名/手机号/邮箱）查询用户关联的租户列表
     * 3. 返回每个租户的登录配置（品牌、登录方式、安全状态）
     * 4. 如果用户只有一个租户，直接返回该租户配置
     * 5. 如果用户有多个租户，返回needSelectTenant=true，由前端展示租户选择页
     *
     * @param params 预登录参数
     * @return 预登录响应
     */
    @PostMapping("/preLogin")
    ResultModel<?> preLogin(@RequestBody @Valid PreLoginParams params);

    /**
     * 登录认证接口
     * @param params 请求参数
     * @return ResultModel
     */
    @PostMapping("/login")
    ResultModel<?> login(@RequestBody @Valid LoginRequestParams params);

}
