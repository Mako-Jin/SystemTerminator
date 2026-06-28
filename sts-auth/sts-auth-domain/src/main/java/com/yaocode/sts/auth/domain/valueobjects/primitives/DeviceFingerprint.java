package com.yaocode.sts.auth.domain.valueobjects.primitives;

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

    private static final Pattern PATTERN = Pattern.compile("^[a-fA-F0-9]{32,128}$");

    private DeviceFingerprint(String value) {
        super(value);
    }

    public static DeviceFingerprint of(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("设备指纹不能为空");
        }
        String trimmed = value.trim();
        if (!PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("设备指纹格式不正确，应为十六进制字符串");
        }
        return new DeviceFingerprint(trimmed);
    }

}
