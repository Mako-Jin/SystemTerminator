package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import lombok.Value;

import java.util.Objects;
import java.util.Set;

/**
 * MFA配置（值对象）
 */
@Value
public class MFAConfig {

    boolean required;
    Set<MFATypeEnums> supportedTypes;

    private MFAConfig(boolean required, Set<MFATypeEnums> supportedTypes) {
        if (required && (supportedTypes == null || supportedTypes.isEmpty())) {
            throw new IllegalArgumentException("启用MFA时必须指定至少一种MFA类型");
        }
        this.required = required;
        this.supportedTypes = supportedTypes;
    }

    public static MFAConfig of(boolean required, Set<MFATypeEnums> supportedTypes) {
        return new MFAConfig(required, supportedTypes);
    }

    public static MFAConfig disabled() {
        return new MFAConfig(false, null);
    }

    /**
     * 是否支持指定的MFA类型
     */
    public boolean supports(MFATypeEnums type) {
        return supportedTypes != null && supportedTypes.contains(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MFAConfig mfaConfig = (MFAConfig) o;
        return required == mfaConfig.required &&
                Objects.equals(supportedTypes, mfaConfig.supportedTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(required, supportedTypes);
    }
}
