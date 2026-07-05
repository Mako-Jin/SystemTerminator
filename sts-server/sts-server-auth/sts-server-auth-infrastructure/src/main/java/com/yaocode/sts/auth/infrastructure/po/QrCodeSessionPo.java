package com.yaocode.sts.auth.infrastructure.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaocode.sts.common.infrastructure.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 扫码登录会话表
 */
@Data
@TableName("auth_tbl_qrcode_session")
@EqualsAndHashCode(callSuper = true)
public class QrCodeSessionPo extends BasePo {

    @TableId
    private String sessionId;

    /**
     * 二维码唯一标识（展示在二维码中）
     */
    private String qrCodeId;

    /**
     * 租户ID（如果指定了租户登录）
     */
    private String tenantId;

    /**
     * 客户端ID（PC端发起）
     */
    private String clientId;

    /**
     * 设备ID（PC端）
     */
    private String deviceId;

    /**
     * 二维码状态：
     * PENDING-待扫码, SCANNED-已扫码待确认,
     * CONFIRMED-已确认, EXPIRED-已过期, CANCELLED-已取消
     */
    private Integer status;

    /**
     * 扫码用户ID（手机端扫码后填充）
     */
    private String scannedByUserId;

    /**
     * 扫码时间
     */
    private LocalDateTime scannedAt;

    /**
     * 确认时间（用户点击确认登录）
     */
    private LocalDateTime confirmedAt;

    /**
     * 二维码过期时间
     */
    private LocalDateTime expiresAt;

    /**
     * 确认后生成的Token（登录成功后返回）
     */
    private String generatedToken;

    /**
     * 扫码设备信息（手机端）
     */
    private String scannerDeviceInfo;

    /**
     * 扫码IP
     */
    private String scannerIp;
}
