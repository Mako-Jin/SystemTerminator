package com.yaocode.sts.auth.domain.valueobjects.composites;


import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Value;

/**
 * 认证令牌值对象
 * 封装认证请求的所有参数
 *
 * @author: Jin-LiangBo
 * @date: 2026-06-08
 */
@Value
@Builder
public class AuthenticationToken {

    UserId userId;

    // ========== 认证类型 ==========
    String grantType;

    String accessToken;

    String refreshToken;

    String rememberMeToken;

    String stateToken;

    Boolean authenticated;

}
