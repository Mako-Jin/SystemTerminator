package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * IP地址（值对象）
 * 支持 IPv4 和 IPv6
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class IpAddress extends Identifier<String> {

    private static final Pattern IPV4_PATTERN = RegexConstants.IPV4_PATTERN_COMPILED;

    private static final Pattern IPV6_PATTERN = RegexConstants.IPV6_PATTERN_COMPILED;

    private IpAddress(String value) {
        super(value);
    }

    public static IpAddress of (String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IP_ADDRESS_CANNOT_BE_BLANK);
        }
        String trimmed = value.trim();
        if (!IPV4_PATTERN.matcher(trimmed).matches() && !IPV6_PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.IP_ADDRESS_FORMAT_INVALID);
        }
        return new IpAddress(trimmed);
    }

    public boolean isIPv4() {
        return IPV4_PATTERN.matcher(getValue()).matches();
    }

    public boolean isIPv6() {
        return IPV6_PATTERN.matcher(getValue()).matches();
    }

    /**
     * 判断是否为内网IP
     */
    public boolean isPrivate() {
        if (!isIPv4()) return false;
        String[] parts = getValue().split(RegexConstants.REGEX_DOT);
        int first = Integer.parseInt(parts[0]);
        int second = Integer.parseInt(parts[1]);
        // 10.0.0.0/8
        if (first == 10) return true;
        // 172.16.0.0/12
        if (first == 172 && second >= 16 && second <= 31) return true;
        // 192.168.0.0/16
        return first == 192 && second == 168;
    }

}