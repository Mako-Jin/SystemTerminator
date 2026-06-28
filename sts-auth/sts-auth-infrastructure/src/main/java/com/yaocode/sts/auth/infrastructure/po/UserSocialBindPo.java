package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 用户第三方身份绑定表
 */
@Data
@TableName("auth_tbl_user_social_bind")
@EqualsAndHashCode(callSuper = true)
public class UserSocialBindPo extends BasePo {

    @TableId
    private String bindId;

    /**
     * 用户ID（系统内部用户）
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 第三方平台：WECHAT-微信, ALIPAY-支付宝, DINGTALK-钉钉,
     * GITHUB, GOOGLE, APPLE, WECHAT_MINI-小程序
     */
    private String provider;

    /**
     * 第三方平台的用户唯一标识（OpenID/UnionID）
     */
    private String providerUserId;

    /**
     * 第三方平台的UnionID（微信等平台有）
     */
    private String providerUnionId;

    /**
     * 第三方昵称
     */
    private String providerNickname;

    /**
     * 第三方头像URL
     */
    private String providerAvatar;

    /**
     * 第三方邮箱（如有）
     */
    private String providerEmail;

    /**
     * Access Token（加密存储）
     */
    private String accessToken;

    /**
     * Refresh Token（加密存储）
     */
    private String refreshToken;

    /**
     * Token过期时间
     */
    private LocalDateTime expireTime;

    /**
     * 额外信息（JSON，存储平台特有字段）
     */
    private String extraInfo;

    /**
     * 绑定状态：0-已解绑, 1-已绑定
     */
    private Integer bindStatus;

    /**
     * 首次绑定时间
     */
    private LocalDateTime bindTime;

    /**
     * 最后登录时间（通过该方式）
     */
    private LocalDateTime lastLoginTime;
}
