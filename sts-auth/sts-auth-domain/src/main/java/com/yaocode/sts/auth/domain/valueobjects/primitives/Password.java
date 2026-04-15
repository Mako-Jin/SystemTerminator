package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.PasswordEncoder;
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
        validatePassword(plainPassword);
        String encrypted = encoder.encode(plainPassword);
        return new Password(encrypted, LocalDateTime.now(), null, false, 1);
    }

    public static Password reconstruct(String value, LocalDateTime createTime, LocalDateTime expireTime, boolean isTemporary, int version) {
        return new Password(value, createTime, expireTime, isTemporary, version);
    }

    private static void validatePassword(String password) {
        if (CHINESE_PATTERN.matcher(password).find() || PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("auth.password.rule.check.error");
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

}
