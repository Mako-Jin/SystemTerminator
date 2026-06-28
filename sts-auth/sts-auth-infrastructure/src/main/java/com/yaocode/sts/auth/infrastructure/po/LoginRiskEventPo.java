package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 登录风控事件表
 */
@Data
@TableName("auth_tbl_login_risk_event")
@EqualsAndHashCode(callSuper = true)
public class LoginRiskEventPo extends BasePo {

    @TableId
    private String eventId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户名（可能未登录，用用户名标识）
     */
    private String username;

    /**
     * 用户ID（如果已识别）
     */
    private String userId;

    /**
     * 客户端IP
     */
    private String clientIp;

    /**
     * 设备指纹
     */
    private String deviceFingerprint;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 事件类型：
     * PASSWORD_FAILED-密码错误, CAPTCHA_FAILED-验证码错误,
     * SUSPICIOUS_IP-可疑IP, BRUTE_FORCE-暴力破解,
     * UNUSUAL_TIME-非常规时间登录, UNUSUAL_LOCATION-非常规地点登录
     */
    private Integer eventType;

    /**
     * 风险等级：LOW-低, MEDIUM-中, HIGH-高, CRITICAL-严重
     */
    private Integer riskLevel;

    /**
     * 事件时间
     */
    private LocalDateTime eventTime;

    /**
     * 是否触发锁定
     */
    private Integer triggeredLock;

    /**
     * 锁定时长（秒）
     */
    private Integer lockDuration;

    /**
     * 是否触发验证码
     */
    private Integer triggeredCaptcha;

    /**
     * 事件详情（JSON格式，存储更多上下文）
     */
    private String eventDetails;

    /**
     * 是否已处理（如已人工审核）
     */
    private Integer isHandled;

    /**
     * 处理人
     */
    private String handlerId;

    /**
     * 处理备注
     */
    private String handleRemark;
}
