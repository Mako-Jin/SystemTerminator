package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.common.crypto.password.PasswordEncoder;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * 密码值对象
 * @author: Jin-LiangBo
 * @date: 2025年10月13日 22:35
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class Password extends Identifier<String> {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$^+_()%*?&.\\-=]){8,20}$");

    private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]");

    /**
     * 创建时间
     */
    LocalDateTime createTime;
    /**
     * 过期时间
     */
    LocalDateTime expireTime;
    /**
     * 是否临时密码
     */
    boolean isTemporary;
    /**
     * 密码版本
     */
    int version;

    private Password(String value, LocalDateTime createTime, LocalDateTime expireTime, boolean isTemporary, int version) {
        super(value);
        this.createTime = createTime;
        this.expireTime = expireTime;
        this.isTemporary = isTemporary;
        this.version = version;
    }

    /**
     * 创建新密码（从明文）
     */
    public static Password of (String plainPassword, PasswordEncoder encoder) {
        // 在创建时校验明文密码格式
        validatePlainPassword(plainPassword);
        String encrypted = encoder.encode(plainPassword);
        return new Password(encrypted, LocalDateTime.now(), null, false, 1);
    }

    public static Password reconstruct(String value, LocalDateTime createTime, LocalDateTime expireTime, boolean isTemporary, int version) {
        return new Password(value, createTime, expireTime, isTemporary, version);
    }

    private static void validatePlainPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        if (CHINESE_PATTERN.matcher(plainPassword).find()) {
            throw new IllegalArgumentException("auth.password.rule.check.error");
        }
        if (!PASSWORD_PATTERN.matcher(plainPassword).matches()) {
            throw new IllegalArgumentException("auth.password.rule.check.error");
        }
    }

    @Override
    public void validate(String value) {
        super.validate(value);
        // validate() 用于校验存储的密文值非空即可
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("密码值不能为空");
        }
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param encoder 处理器
     * @return boolean
     */
    public boolean matches(String plainPassword, PasswordEncoder encoder) {
        if (!StringUtils.hasText(plainPassword)) {
            return false;
        }
        return encoder.matches(plainPassword, this.getValue());
    }

    /**
     * 是否已过期
     */
    public boolean isExpired() {
        if (expireTime == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(expireTime);
    }

    /**
     * 是否需要修改密码
     */
    public boolean needsChange() {
        // 临时密码需要修改
        if (isTemporary) {
            return true;
        }
        // 密码即将过期（如还剩7天）
        return expireTime != null && LocalDateTime.now().isAfter(expireTime.minusDays(7));
    }

}
