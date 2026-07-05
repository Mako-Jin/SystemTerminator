package com.yaocode.sts.auth.domain.constants;

/**
 * JWT相关常量
 * @author: Jin-LiangBo
 * @date: 2026年07月04日
 */
public interface JwtTokenConstants {

    // ==================== JWT Payload Key 常量 ====================

    /**
     * 用户ID
     */
    String PAYLOAD_USER_ID = "userId";

    /**
     * 客户端ID
     */
    String PAYLOAD_CLIENT_ID = "clientId";

    /**
     * 设备ID
     */
    String PAYLOAD_DEVICE_ID = "deviceId";

    /**
     * Token类型
     */
    String PAYLOAD_TOKEN_TYPE = "tokenType";

    /**
     * JWT ID
     */
    String PAYLOAD_JTI = "jti";

    /**
     * 刷新Token ID
     */
    String PAYLOAD_REFRESH_TOKEN_ID = "refreshTokenId";

    /**
     * RememberMe系列标识
     */
    String PAYLOAD_SERIES = "series";

    /**
     * State参数
     */
    String PAYLOAD_STATE = "state";

    /**
     * 重定向URI
     */
    String PAYLOAD_REDIRECT_URI = "redirectUri";
}
