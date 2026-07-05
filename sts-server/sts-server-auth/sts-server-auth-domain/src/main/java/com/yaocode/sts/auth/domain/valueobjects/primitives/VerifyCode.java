package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.auth.domain.constants.AuthDomainConstants;
import com.yaocode.sts.auth.domain.enums.VerifyCodeTypeEnums;
import com.yaocode.sts.auth.domain.rules.VerifyCodeRule;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;

import lombok.EqualsAndHashCode;
import lombok.Value;

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
@Value
@EqualsAndHashCode(callSuper = true)
public class VerifyCode extends Identifier<String> {

    /**
     * 验证码格式：6位数字
     */
    private static final Pattern CODE_PATTERN = RegexConstants.VERIFY_CODE_PATTERN_COMPILED;

    /**
     * 验证码有效期（默认5分钟，单位：秒）
     */
    private static final long DEFAULT_TTL_SECONDS = AuthDomainConstants.DEFAULT_TTL_SECONDS;

    /**
     * 验证码类型
     */
    VerifyCodeTypeEnums codeType;

    /**
     * 规则实例（延迟加载）
     */
    VerifyCodeRule verifyCodeRule;

    /**
     * 过期时间
     */
    LocalDateTime expireTime;

    /**
     * 验证码有效期（默认5分钟，单位：秒）
     */
    long ttlSeconds;

    /**
     * 验证码会话ID（防重放攻击）
     */
    String sessionId;

    /**
     * 是否已使用（防止重复使用）
     */
    boolean used;

    /**
     * 构造函数（使用默认有效期）
     */
    public VerifyCode(String code, VerifyCodeTypeEnums codeType, String sessionId, VerifyCodeRule verifyCodeRule) {
        this(code, codeType, verifyCodeRule, sessionId, DEFAULT_TTL_SECONDS); // 默认5分钟
    }

    /**
     * 全参构造函数
     */
    public VerifyCode(
            String code, VerifyCodeTypeEnums codeType, VerifyCodeRule verifyCodeRule,
            String sessionId, long ttlSeconds
    ) {
        this(code, codeType, verifyCodeRule, sessionId, ttlSeconds, null, false);
    }

    private VerifyCode(
            String value, VerifyCodeTypeEnums codeType, VerifyCodeRule verifyCodeRule, String sessionId,
            long ttlSeconds, LocalDateTime expireTime, boolean used
    ) {
        super(value);
        this.codeType = codeType != null ? codeType : VerifyCodeTypeEnums.LOGIN;
        this.verifyCodeRule = verifyCodeRule;
        this.sessionId = sessionId;
        this.ttlSeconds = ttlSeconds > 0 ? ttlSeconds : AuthDomainConstants.DEFAULT_TTL_SECONDS;
        this.expireTime = expireTime;
        this.used = used;
        validateFormat();
    }

    @Override
    public void validate(String value) {
        super.validate(value);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.VERIFY_CODE_CANNOT_BE_BLANK);
        }
    }

    /**
     * 格式校验（由构造函数调用）
     */
    private void validateFormat() {
        String value = getValue();
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.VERIFY_CODE_CANNOT_BE_BLANK);
        }

        VerifyCodeRule rule = codeType.getRule();
        if (!rule.validate(value)) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.VERIFY_CODE_FORMAT_INVALID);
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
    public VerifyCode markUsed() {
        return new VerifyCode(
                this.getValue(),
                this.codeType,
                this.verifyCodeRule,
                this.sessionId,
                this.ttlSeconds,
                this.expireTime,
                true
        );
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
        if (code == null || code.length() < AuthDomainConstants.MIN_LENGTH_FOR_MASK) {
            return AuthDomainConstants.MASK_STRING;
        }
        return code.substring(0, AuthDomainConstants.MASK_KEEP_LENGTH) +
                AuthDomainConstants.MASK_STRING +
                code.substring(code.length() - AuthDomainConstants.MASK_KEEP_LENGTH);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + SymbolConstants.LEFT_BRACKETS + mask()
                + ", type=" + codeType + SymbolConstants.RIGHT_BRACKETS;
    }
}