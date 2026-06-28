package com.yaocode.sts.auth.application.dto;

import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

/**
 * MFA配置（值对象）
 */
@Getter
public class MFAConfigDto {

    private final boolean required;
    private final Set<MFATypeEnums> supportedTypes;

    private MFAConfigDto(boolean required, Set<MFATypeEnums> supportedTypes) {
        if (required && (supportedTypes == null || supportedTypes.isEmpty())) {
            throw new IllegalArgumentException("启用MFA时必须指定至少一种MFA类型");
        }
        this.required = required;
        this.supportedTypes = supportedTypes;
    }

    public static MFAConfigDto of(boolean required, Set<MFATypeEnums> supportedTypes) {
        return new MFAConfigDto(required, supportedTypes);
    }

    public static MFAConfigDto disabled() {
        return new MFAConfigDto(false, null);
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
        MFAConfigDto mfaConfig = (MFAConfigDto) o;
        return required == mfaConfig.required &&
                Objects.equals(supportedTypes, mfaConfig.supportedTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(required, supportedTypes);
    }
}
