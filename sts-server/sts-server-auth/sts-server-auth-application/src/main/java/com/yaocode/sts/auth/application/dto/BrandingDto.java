package com.yaocode.sts.auth.application.dto;

import com.yaocode.sts.auth.application.constants.AuthApplicationConstants;
import com.yaocode.sts.auth.application.enums.AuthErrorCodeEnums;
import com.yaocode.sts.auth.application.exception.AuthServerException;
import lombok.Getter;

import java.util.Objects;

/**
 * 品牌配置（值对象）
 * 封装品牌名称、Logo、主题色等品牌信息
 */
@Getter
public class BrandingDto {

    private final String brandName;
    private final String logoUrl;
    private final String loginTitle;
    private final String institution;
    private final String copyright;
    private final String primaryColor;
    private final String loginBackgroundUrl;

    private BrandingDto(Builder builder) {
        this.brandName = builder.brandName;
        this.logoUrl = builder.logoUrl;
        this.loginTitle = builder.loginTitle;
        this.institution = builder.institution;
        this.copyright = builder.copyright;
        this.primaryColor = builder.primaryColor;
        this.loginBackgroundUrl = builder.loginBackgroundUrl;
    }

    public static class Builder {
        private String brandName;
        private String logoUrl;
        private String loginTitle;
        private String institution;
        private String copyright;
        private String primaryColor = AuthApplicationConstants.DEFAULT_PRIMARY_COLOR;
        private String loginBackgroundUrl;

        public Builder brandName(String brandName) {
            this.brandName = brandName;
            return this;
        }

        public Builder logoUrl(String logoUrl) {
            this.logoUrl = logoUrl;
            return this;
        }

        public Builder loginTitle(String loginTitle) {
            this.loginTitle = loginTitle;
            return this;
        }

        public Builder institution(String institution) {
            this.institution = institution;
            return this;
        }

        public Builder copyright(String copyright) {
            this.copyright = copyright;
            return this;
        }

        public Builder primaryColor(String primaryColor) {
            // 简单颜色格式校验（#RRGGBB）
            if (primaryColor != null && !primaryColor.matches(AuthApplicationConstants.COLOR_FORMAT_REGEX)) {
                throw new AuthServerException(AuthErrorCodeEnums.BRANDING_COLOR_FORMAT_INVALID);
            }
            this.primaryColor = primaryColor;
            return this;
        }

        public Builder loginBackgroundUrl(String loginBackgroundUrl) {
            this.loginBackgroundUrl = loginBackgroundUrl;
            return this;
        }

        public BrandingDto build() {
            return new BrandingDto(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandingDto branding = (BrandingDto) o;
        return Objects.equals(brandName, branding.brandName) &&
                Objects.equals(logoUrl, branding.logoUrl) &&
                Objects.equals(loginTitle, branding.loginTitle) &&
                Objects.equals(institution, branding.institution) &&
                Objects.equals(copyright, branding.copyright) &&
                Objects.equals(primaryColor, branding.primaryColor) &&
                Objects.equals(loginBackgroundUrl, branding.loginBackgroundUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brandName, logoUrl, loginTitle, institution, copyright, primaryColor, loginBackgroundUrl);
    }

    @Override
    public String toString() {
        return "Branding{brandName='" + brandName + "'}";
    }
}
