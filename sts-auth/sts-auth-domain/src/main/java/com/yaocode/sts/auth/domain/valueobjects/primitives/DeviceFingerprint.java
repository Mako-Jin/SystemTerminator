package com.yaocode.sts.auth.domain.valueobjects.primitives;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 设备指纹（值对象）
 * 基于设备特征生成的唯一哈希值
 */
public class DeviceFingerprint {

    private static final Pattern PATTERN = Pattern.compile("^[a-fA-F0-9]{32,128}$");

    private final String value;

    public DeviceFingerprint(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("设备指纹不能为空");
        }
        String trimmed = value.trim();
        if (!PATTERN.matcher(trimmed).matches()) {
            throw new IllegalArgumentException("设备指纹格式不正确，应为十六进制字符串");
        }
        this.value = trimmed;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceFingerprint that = (DeviceFingerprint) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
