package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 安全状态VO
 */
@Data
@Builder
public class SecurityStatusVo {

    /**
     * 账户是否被锁定
     */
    private Boolean isLocked;

    /**
     * 锁定原因
     */
    private String lockReason;

    /**
     * 剩余尝试次数（如果已锁定，显示为0）
     */
    private Integer remainingAttempts;

    /**
     * 解锁时间（如果已锁定）
     */
    private String unlockTime;

    /**
     * 是否触发验证码
     */
    private Boolean captchaRequired;

    /**
     * 验证码类型：MATH-数学, TEXT-文本, SLIDER-滑块
     */
    private String captchaType;

    /**
     * 是否需要MFA验证（登录流程中）
     */
    private Boolean mfaRequired;

    /**
     * 是否已通过MFA验证
     */
    private Boolean mfaVerified;

    /**
     * 是否在信任设备上
     */
    private Boolean isTrustedDevice;
}
