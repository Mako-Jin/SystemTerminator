package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.BlackWhiteListEntity;
import com.yaocode.sts.auth.domain.entity.DeviceInfoEntity;
import com.yaocode.sts.auth.domain.entity.LoginAttemptEntity;
import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.enums.BlackWhiteListTypeEnums;
import com.yaocode.sts.auth.domain.repository.BlackWhiteListRepository;
import com.yaocode.sts.auth.domain.repository.DeviceInfoRepository;
import com.yaocode.sts.auth.domain.repository.LoginAttemptRepository;
import com.yaocode.sts.auth.domain.repository.TenantConfigRepository;
import com.yaocode.sts.auth.domain.service.AuthSecurityDomainService;
import com.yaocode.sts.auth.domain.valueobjects.composites.SecurityStatus;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.common.tools.StringUtils;
import jakarta.annotation.Resource;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthSecurityDomainServiceImpl implements AuthSecurityDomainService {

    @Resource
    private BlackWhiteListRepository blackWhiteListRepository;
    @Resource
    private LoginAttemptRepository loginAttemptRepository;
    @Resource
    private DeviceInfoRepository deviceInfoRepository;
    @Resource
    private TenantConfigRepository tenantConfigRepository;

    /**
     * 检查用户安全状态
     */
    @Override
    public SecurityStatus checkSecurityStatus(
            UserId userId, TenantId tenantId, DeviceId deviceId, IpAddress ipAddress
    ) {

        // 1. 获取登录尝试记录
        LoginAttemptEntity attempt = loginAttemptRepository
                .findByUserIdAndTenantId(userId, tenantId);

        // 2. 检查账户是否锁定
        if (attempt != null && attempt.getIsLocked() != null
                && Objects.equals(attempt.getIsLocked(), OppositeEnums.YES)) {
            return SecurityStatus.builder()
                    .locked(true)
                    .lockReason(attempt.getLockReason())
                    .unlockTime(attempt.getUnlockTime() != null
                            ? attempt.getUnlockTime().toString() : null)
                    .remainingAttempts(0)
                    .captchaRequired(true)
                    .trustedDevice(false)
                    .build();
        }

        // 3. 获取最大尝试次数
        int maxAttempts = getMaxLoginAttempts(tenantId);
        int failedCount = attempt != null && attempt.getFailedAttempts() != null
                ? attempt.getFailedAttempts() : 0;
        int remaining = maxAttempts - failedCount;

        // 4. 检查是否需要验证码（失败次数 >= 3）
        boolean captchaRequired = failedCount >= 3;

        // 5. 检查设备是否可信
        boolean trustedDevice = false;
        if (Objects.nonNull(deviceId)) {
            Optional<DeviceInfoEntity> device = deviceInfoRepository.findByDeviceIdAndTenantId(deviceId, tenantId);
            if (device.isPresent() && device.get().getIsTrusted() != null
                    && Objects.equals(device.get().getIsTrusted(), OppositeEnums.YES)) {
                trustedDevice = true;
            }
        }
        // 6. 黑白名单检查
        BlackWhiteCheckResult blackWhiteResult = checkBlackWhiteList(ipAddress, deviceId, null, userId.getValue(), tenantId);
        if (blackWhiteResult.isBlocked()) {
            return SecurityStatus.builder()
                    .locked(true)
                    .lockReason("黑名单拦截: " + blackWhiteResult.getReason())
                    .remainingAttempts(0)
                    .captchaRequired(true)
                    .trustedDevice(trustedDevice)
                    .build();
        }

        return SecurityStatus.builder()
                .locked(false)
                .remainingAttempts(Math.max(0, remaining))
                .captchaRequired(captchaRequired)
                .trustedDevice(trustedDevice)
                .build();
    }

    /**
     * 检查登录尝试是否允许
     */
    public boolean isLoginAllowed(UserId userId, TenantId tenantId) {
        LoginAttemptEntity attempt = loginAttemptRepository
                .findByUserIdAndTenantId(userId, tenantId);

        if (attempt == null) {
            return true;
        }

        // 检查是否锁定
        if (Objects.equals(attempt.getIsLocked(), OppositeEnums.YES)) {
            return false;
        }

        // 检查尝试次数是否达到上限
        int maxAttempts = getMaxLoginAttempts(tenantId);
        int failedCount = attempt.getFailedAttempts() != null ? attempt.getFailedAttempts() : 0;
        return failedCount < maxAttempts;
    }

    /**
     * 记录登录失败
     */
    public void recordLoginFailure(UserId userId, TenantId tenantId, IpAddress ipAddress) {
        LoginAttemptEntity attempt = loginAttemptRepository
                .findByUserIdAndTenantId(userId, tenantId);

        if (attempt == null) {
            attempt = LoginAttemptEntity.builder()
                    .userId(userId)
                    .tenantId(tenantId)
                    .failedAttempts(1)
                    .lastFailedTime(LocalDateTime.now())
                    .isLocked(OppositeEnums.NO)
                    .build();
        } else {
            attempt.recordFailedAttempt();
        }

        loginAttemptRepository.save(attempt);
    }

    /**
     * 清除登录失败记录（登录成功后调用）
     */
    public void clearLoginFailures(UserId userId, TenantId tenantId) {
        LoginAttemptEntity attempt = loginAttemptRepository.findByUserIdAndTenantId(userId, tenantId);
        if (attempt != null) {
            attempt.reset();
            loginAttemptRepository.save(attempt);
        }
    }

    /**
     * 检查黑白名单
     */
    public BlackWhiteCheckResult checkBlackWhiteList(
            IpAddress ipAddress,
            DeviceId deviceId,
            ClientId clientId,
            String identifier,
            TenantId tenantId
    ) {

        // 1. 获取所有启用的黑白名单规则
        List<BlackWhiteListEntity> allRules = blackWhiteListRepository
                .findByTenantIdOrNullAndEnabled(tenantId.getValue());

        if (allRules == null || allRules.isEmpty()) {
            return BlackWhiteCheckResult.allowed();
        }

        // 2. 按优先级排序（数字越大优先级越高）
        allRules.sort((a, b) -> {
            int pa = a.getPriority() != null ? a.getPriority() : 0;
            int pb = b.getPriority() != null ? b.getPriority() : 0;
            return pb - pa;
        });

        // 3. 逐条匹配
        for (BlackWhiteListEntity rule : allRules) {
            if (matchesRule(rule, ipAddress, deviceId, clientId, identifier)) {
                if (Objects.equals(rule.getAction(), OppositeEnums.DENY)) {
                    // DENY
                    return BlackWhiteCheckResult.blocked(
                            "规则匹配: " + rule.getListType() + "=" + rule.getListValue());
                } else if (Objects.equals(rule.getAction(), OppositeEnums.ALLOW)) {
                    // ALLOW
                    return BlackWhiteCheckResult.allowed();
                }
            }
        }

        return BlackWhiteCheckResult.allowed();
    }

    /**
     * 匹配黑白名单规则
     */
    private boolean matchesRule(
            BlackWhiteListEntity rule,
            IpAddress ipAddress,
            DeviceId deviceId,
            ClientId clientId,
            String identifier) {

        // 检查是否启用
        if (Objects.equals(rule.getIsEnabled(), OppositeEnums.ENABLED)) {
            return false;
        }

        // 检查有效期
        if (rule.getEffectiveFrom() != null && rule.getEffectiveFrom().isAfter(LocalDateTime.now())) {
            return false;
        }
        if (rule.getEffectiveTo() != null && rule.getEffectiveTo().isBefore(LocalDateTime.now())) {
            return false;
        }

        BlackWhiteListTypeEnums listType = rule.getListType();
        String listValue = rule.getListValue();

        if (Objects.isNull(listType) || !StringUtils.hasText(listValue)) {
            return false;
        }

        return switch (listType) {
            case IP -> matchIp(ipAddress.getValue(), listValue);
            case DEVICE -> matchDevice(deviceId.getValue(), listValue);
            case CLIENT -> matchClient(clientId.getValue(), listValue);
            case USER -> matchUser(identifier, listValue);
            default -> false;
        };
    }

    /**
     * IP匹配（支持CIDR）
     */
    private boolean matchIp(String ip, String pattern) {
        if (!StringUtils.hasText(ip) || !StringUtils.hasText(pattern)) {
            return false;
        }
        // 精确匹配
        if (ip.equals(pattern)) {
            return true;
        }
        // CIDR匹配（简化版）
        if (pattern.contains("/")) {
            String[] parts = pattern.split("/");
            if (parts.length == 2) {
                String subnet = parts[0];
                int prefixLen = Integer.parseInt(parts[1]);
                return ip.startsWith(subnet.substring(0, Math.min(subnet.length(), prefixLen)));
            }
        }
        return false;
    }

    private boolean matchDevice(String deviceId, String pattern) {
        return StringUtils.hasText(deviceId) && deviceId.equals(pattern);
    }

    private boolean matchClient(String clientId, String pattern) {
        return StringUtils.hasText(clientId) && clientId.equals(pattern);
    }

    private boolean matchUser(String identifier, String pattern) {
        return StringUtils.hasText(identifier) && identifier.equals(pattern);
    }

    /**
     * 获取最大登录尝试次数
     */
    private int getMaxLoginAttempts(TenantId tenantId) {
        Optional<TenantConfigEntity> config = tenantConfigRepository.findByTenantId(tenantId);
        if (config.isPresent() && config.get().getMaxLoginAttempts() != null) {
            return config.get().getMaxLoginAttempts();
        }
        return 5; // 默认5次
    }

    // ==================== 内部类 ====================

    @Data
    public static class BlackWhiteCheckResult {
        private boolean blocked;
        private String reason;

        public static BlackWhiteCheckResult allowed() {
            BlackWhiteCheckResult result = new BlackWhiteCheckResult();
            result.setBlocked(false);
            return result;
        }

        public static BlackWhiteCheckResult blocked(String reason) {
            BlackWhiteCheckResult result = new BlackWhiteCheckResult();
            result.setBlocked(true);
            result.setReason(reason);
            return result;
        }
    }

}
