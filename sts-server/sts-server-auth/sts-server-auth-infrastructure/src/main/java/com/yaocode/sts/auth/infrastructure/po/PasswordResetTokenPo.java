package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 密码重置令牌表
 */
@Data
@TableName("auth_tbl_password_reset_token")
@EqualsAndHashCode(callSuper = true)
public class PasswordResetTokenPo extends BasePo {

    @TableId
    private String tokenId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 重置令牌（哈希存储）
     */
    private String tokenHash;

    /**
     * 令牌类型：EMAIL-邮箱重置, SMS-短信重置, ADMIN-管理员重置
     */
    private Integer tokenType;

    /**
     * 目标地址（邮箱/手机号）
     */
    private String targetAddress;

    /**
     * 是否已使用
     */
    private Integer isUsed;

    /**
     * 使用时间
     */
    private LocalDateTime usedAt;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 请求IP
     */
    private String requestIp;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 请求次数（防止暴力破解）
     */
    private Integer requestCount;
}
