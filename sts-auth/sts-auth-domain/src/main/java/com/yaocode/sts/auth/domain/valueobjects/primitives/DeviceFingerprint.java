package com.yaocode.sts.auth.domain.valueobjects.primitives;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.constants.RegexConstants;
import com.yaocode.sts.common.domain.valueobject.Identifier;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.regex.Pattern;

/**
 * 设备指纹（值对象）
 * 基于设备特征生成的唯一哈希值
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class DeviceFingerprint extends Identifier<String> {

    private static final Pattern PATTERN = RegexConstants.DEVICE_FINGERPRINT_PATTERN_COMPILED;

    private DeviceFingerprint(String value) {
        super(value);
    }

    public static DeviceFingerprint of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.DEVICE_FINGERPRINT_CANNOT_BE_BLANK);
        }
        String trimmed = value.trim();
        if (!PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException(AuthI18nKeyConstants.DEVICE_FINGERPRINT_FORMAT_INVALID);
        }
        return new DeviceFingerprint(trimmed);
    }

}