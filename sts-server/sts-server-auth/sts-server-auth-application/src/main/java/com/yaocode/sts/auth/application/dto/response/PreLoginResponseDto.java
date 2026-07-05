package com.yaocode.sts.auth.application.dto.response;

import com.yaocode.sts.auth.application.dto.LoginSuccessDto;
import com.yaocode.sts.auth.application.dto.TenantConfigDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * 登录成功响应 DTO
 * 一次性返回用户在所有租户下的完整信息
 *
 * @author yaocode
 * @since 0.0.1
 */
@Data
@Builder
public class PreLoginResponseDto {

    /**
     * 是否认证成功
     */
    private Boolean isAuthenticated = false;

    private String userId;
    private String username;
    private String displayName;
    private String avatar;

    private LoginSuccessDto loginSuccessDto;

    private String stateToken;
    /**
     * 状态令牌有效期（秒）
     */
    Instant stateTokenExpiresAt;
    private String email;
    private String mobile;
    private String sessionId;

    // ===== 多租户支持 =====
    /**
     * 是否需要选择租户
     * true: 前端展示"选择企业"页面
     * false: 直接展示登录页
     */
    private Boolean needSelectTenant;

    /**
     * 租户登录配置列表
     * 如果用户只有一个租户，列表只有一个元素，needSelectTenant=false
     * 如果用户有多个租户，列表有多个元素，needSelectTenant=true
     */
    private List<TenantConfigDto> tenantLoginConfigs;

    // ===== 服务器时间 =====
    private Long serverTime;

}