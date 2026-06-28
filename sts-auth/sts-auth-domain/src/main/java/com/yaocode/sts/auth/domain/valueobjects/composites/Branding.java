package com.yaocode.sts.auth.domain.valueobjects.composites;

import lombok.Value;

import java.util.Objects;

/**
 * 品牌配置（值对象）
 * 封装品牌名称、Logo、主题色等品牌信息
 */
@Value
public class Branding {

    String brandName;
    String logoUrl;
    String loginTitle;
    String institution;
    String copyright;
    String primaryColor;
    String loginBackgroundUrl;

    private Branding(Builder builder) {
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
        private String primaryColor = "#1890ff";
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
            if (primaryColor != null && !primaryColor.matches("^#[0-9a-fA-F]{6}$")) {
                throw new IllegalArgumentException("颜色格式不正确，应为 #RRGGBB");
            }
            this.primaryColor = primaryColor;
            return this;
        }

        public Builder loginBackgroundUrl(String loginBackgroundUrl) {
            this.loginBackgroundUrl = loginBackgroundUrl;
            return this;
        }

        public Branding build() {
            return new Branding(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branding branding = (Branding) o;
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
