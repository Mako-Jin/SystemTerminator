package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

@Data
@TableName("auth_tbl_client_info")
@EqualsAndHashCode(callSuper = true)
public class ClientInfoPo extends BasePo {

    /**
     * 客户端ID
     */
    @TableId
    private String clientId;
    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * web, mobile, desktop, mini_program
     */
    private Integer clientType;
    /**
     * 客户端版本
     */
    private String clientVersion;
    /**
     * 客户端密钥（OAuth2）
     */
    private String clientSecret;

    /**
     * 授权类型：password, authorization_code, client_credentials, refresh_token
     */
    private Integer grantTypes;

    /**
     * 重定向URI（OAuth2）
     */
    private String redirectUris;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 应用ID（第三方应用的标识）
     */
    private String appId;

    /**
     * 是否启用
     */
    private Integer enabled;

}
