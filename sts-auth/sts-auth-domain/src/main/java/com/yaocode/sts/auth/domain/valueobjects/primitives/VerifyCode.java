package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.enums.VerifyCodeTypeEnums;
import com.yaocode.sts.auth.domain.rules.VerifyCodeRule;
import com.yaocode.sts.common.domain.valueobject.Identifier;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 验证码值对象
 * <p>
 * 职责：封装验证码的校验、时效性、类型等逻辑
 * 注意：验证码属于敏感信息，使用后应调用 clear() 清理
 * </p>
 *
 * @author: Jin-LiangBo
 * @date: 2026年03月31日 16:09
 */
@Getter
public class VerifyCode extends Identifier<String> {

    /**
     * 验证码格式：6位数字
     */
    private static final Pattern CODE_PATTERN = Pattern.compile("^\\d{6}$");

    /**
     * 验证码有效期（默认5分钟，单位：秒）
     */
    private static final long DEFAULT_TTL_SECONDS = 300;

    /**
     * 验证码类型
     */
    private final VerifyCodeTypeEnums codeType;

    /**
     * 规则实例（延迟加载）
     */
    private transient VerifyCodeRule verifyCodeRule;

    /**
     * 过期时间
     */
    private final LocalDateTime expireTime;

    /**
     * 验证码有效期（默认5分钟，单位：秒）
     */
    private final long ttlSeconds;

    /**
     * 验证码会话ID（防重放攻击）
     */
    private final String sessionId;

    /**
     * 是否已使用（防止重复使用）
     */
    private boolean used;

    /**
     * 构造函数（使用默认有效期）
     */
    public VerifyCode(String code, VerifyCodeTypeEnums codeType, String sessionId) {
        this(code, codeType, sessionId, DEFAULT_TTL_SECONDS); // 默认5分钟
    }

    /**
     * 全参构造函数
     */
    public VerifyCode(String code, VerifyCodeTypeEnums codeType, String sessionId, long ttlSeconds) {
        super(code);
        this.codeType = codeType != null ? codeType : VerifyCodeTypeEnums.LOGIN;
        this.sessionId = sessionId;
        this.ttlSeconds = ttlSeconds > 0 ? ttlSeconds : 300;
        this.expireTime = LocalDateTime.now().plusSeconds(this.ttlSeconds);
        this.used = false;
        validateFormat();
    }

    @Override
    public void validate(String value) {
        super.validate(value);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("验证码不能为空");
        }
    }

    /**
     * 格式校验（由构造函数调用）
     */
    private void validateFormat() {
        String value = getValue();
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("验证码不能为空");
        }

        VerifyCodeRule rule = codeType.getRule();
        if (!rule.validate(value)) {
            throw new IllegalArgumentException("验证码格式错误，应为：" + rule.getDescription());
        }
    }

    /**
     * 校验验证码是否过期
     */
    public boolean isExpired() {
        if (expireTime == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(expireTime);
    }

    /**
     * 校验验证码是否匹配
     *
     * @param inputCode 用户输入的验证码
     * @return 是否匹配
     */
    public boolean matches(String inputCode) {
        if (inputCode == null || inputCode.isBlank()) {
            return false;
        }
        if (used) {
            return false;
        }
        if (isExpired()) {
            return false;
        }

        String storedCode = getValue();
        VerifyCodeRule rule = codeType.getRule();
        String input = inputCode.trim();

        return rule.isCaseSensitive() ? storedCode.equals(input) : storedCode.equalsIgnoreCase(input);
    }

    /**
     * 标记验证码为已使用
     */
    public void markUsed() {
        this.used = true;
    }

    /**
     * 检查验证码是否可用（未使用、未过期）
     */
    public boolean isAvailable() {
        return !used && !isExpired();
    }

    /**
     * 获取剩余有效秒数
     */
    public long getRemainingSeconds() {
        if (expireTime == null) {
            return DEFAULT_TTL_SECONDS;
        }
        long seconds = java.time.Duration.between(LocalDateTime.now(), expireTime).getSeconds();
        return Math.max(0, seconds);
    }

    /**
     * 脱敏显示（用于日志）
     */
    public String mask() {
        String code = getValue();
        if (code == null || code.length() < 4) {
            return "****";
        }
        return code.substring(0, 2) + "****" + code.substring(code.length() - 2);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + mask() + ", type=" + codeType + "]";
    }
}
