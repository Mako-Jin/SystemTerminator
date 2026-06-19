package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

import java.time.LocalDateTime;

/**
 * Remember Me Token 持久化对象
 */
@Data
@TableName("auth_tbl_remember_me_token")
@EqualsAndHashCode(callSuper = true)
public class RememberMeTokenPo extends BasePo {

    @TableId
    private String tokenId;

    /**
     * 关联用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 关联用户名
     */
    private String username;

    /**
     * 绑定的客户端ID
     */
    private String clientId;

    /**
     * 绑定的设备ID
     */
    private String deviceId;
    /**
     * Token 哈希值（不存储明文）
     */
    private String tokenHash;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 最后使用时间
     */
    private LocalDateTime lastUsedAt;
    /**
     * 最后使用IP
     */
    private String lastUsedIp;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 最后使用 UserAgent
     */
    private String lastUsedUserAgent;
    /**
     * 过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 系列号（用于Remember Me令牌序列检测）
     */
    private String series;

    /**
     * 是否已被撤销：0-否、1-是
     */
    private Integer revoked;

    /**
     * 撤销原因
     */
    private String revokedReason;

    /**
     * 撤销时间
     */
    private LocalDateTime revokedAt;
}
