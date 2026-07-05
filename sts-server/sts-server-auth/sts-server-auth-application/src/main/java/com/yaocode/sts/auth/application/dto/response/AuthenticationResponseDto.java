package com.yaocode.sts.auth.application.dto.response;

import com.yaocode.sts.auth.application.dto.LoginSuccessDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 认证结果参数
 * @author: Jin-LiangBo
 * @date: 2026年04月14日 10:45
 */
@Data
public class AuthenticationResponseDto {

    private LoginSuccessDto loginSuccessDto;

    /**
     * 令牌类型（Bearer）
     */
    private String tokenType;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户角色列表
     */
    private List<String> roles;

    /**
     * 用户权限列表
     */
    private List<String> permissions;

    /**
     * 扩展信息
     */
    private Map<String, Object> extraInfo;

    /**
     * 认证时间
     */
    private LocalDateTime authenticationTime;

    /**
     * 认证方式
     */
    private String authenticationMethod;

    /**
     * 是否需要修改密码
     */
    private Boolean needChangePassword;

    /**
     * 是否已绑定MFA
     */
    private Boolean mfaBound;

    /**
     * 如果MFA未绑定，返回绑定URL
     */
    private String mfaBindUrl;

}
