package com.yaocode.sts.auth.domain.valueobjects.primitives;

import lombok.Data;

@Data
public class ClientInfo {

    /**
     * 客户端ID
     */
    private String clientId;
    /**
     * 客户端密钥
     */
    private String clientSecret;
    /**
     * web, mobile, desktop, mini_program
     */
    private String clientType;
    /**
     * 客户端版本
     */
    private String clientVersion;
    /**
     * 应用ID（第三方）
     */
    private String appId;

}
