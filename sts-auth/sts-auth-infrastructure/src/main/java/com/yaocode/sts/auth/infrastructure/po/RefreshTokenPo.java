package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.yaocode.sts.common.infrastructure.po.BasePo;

import java.time.LocalDateTime;

/**
 * Refresh Token 持久化对象
 */
@Data
@TableName("auth_tbl_refresh_token")
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenPo extends BasePo {

    @TableId
    private String tokenId;
    /**
     * JWT ID（对应 JWT 中的 jti 声明）
     */
    private String jti;

    /**
     * Token 哈希值（不存储明文）
     */
    private String tokenHash;
    /**
     * 关联用户ID
     */
    private String userId;
    /**
     * 租户ID
     */
    private String tenantId;
    /**
     * 关联客户端ID
     */
    private String clientId;

    /**
     * 关联设备ID
     */
    private String deviceId;

    /**
     * 是否已撤销：0-否、1-是
     */
    private Integer revoked;

    /**
     * 撤销时间
     */
    private LocalDateTime revokedAt;

    /**
     * 撤销原因：LOGOUT, PASSWORD_CHANGE, REPLACED
     */
    private String revokedReason;

    /**
     * 被哪个新Token替换（刷新时使用）
     */
    private String replacedBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 最后使用时间
     */
    private LocalDateTime lastUsedAt;
    /**
     * 使用次数（用于检测异常）
     */
    private Integer useCount;
}
