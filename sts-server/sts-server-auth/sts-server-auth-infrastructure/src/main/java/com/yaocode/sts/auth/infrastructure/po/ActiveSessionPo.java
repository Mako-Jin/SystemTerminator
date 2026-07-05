package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 活跃会话表
 * 管理用户当前在线的会话
 */
@Data
@TableName("auth_tbl_active_session")
@EqualsAndHashCode(callSuper = true)
public class ActiveSessionPo extends BasePo {

    @TableId
    private String sessionId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 登录历史ID（关联LoginHistoryPo）
     */
    private String historyId;

    /**
     * Access Token（或JTI，用于关联）
     */
    private String accessTokenId;

    /**
     * Refresh Token ID
     */
    private String refreshTokenId;

    /**
     * 会话状态：ACTIVE-活跃, IDLE-空闲, FORCE_LOGOUT-强制下线, EXPIRED-过期
     */
    private Integer status;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 会话创建时间（登录时间）
     */
    private LocalDateTime loginTime;

    /**
     * 最后活动时间
     */
    private LocalDateTime lastAccessTime;

    /**
     * 会话过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 是否被踢出
     */
    private Integer isKicked;

    /**
     * 踢出时间
     */
    private LocalDateTime kickedAt;

    /**
     * 踢出原因：ADMIN_KICK, PASSWORD_CHANGE, CONCURRENT_LOGIN
     */
    private String kickReason;
}