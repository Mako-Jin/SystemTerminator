package com.yaocode.sts.auth.domain.valueobjects.primitives;

import lombok.Getter;

import java.util.Objects;

/**
 * 地址（值对象）
 * 封装国家、省份、城市、街道等地址信息
 */
@Getter
public class Address {

    private final String country;
    private final String region;      // 省份/地区
    private final String locality;    // 城市
    private final String street;      // 街道
    private final String detail;      // 详细地址
    private final String postalCode;  // 邮政编码

    private Address(Builder builder) {
        this.country = builder.country;
        this.region = builder.region;
        this.locality = builder.locality;
        this.street = builder.street;
        this.detail = builder.detail;
        this.postalCode = builder.postalCode;
    }

    public static class Builder {
        private String country;
        private String region;
        private String locality;
        private String street;
        private String detail;
        private String postalCode;

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder region(String region) {
            this.region = region;
            return this;
        }

        public Builder locality(String locality) {
            this.locality = locality;
            return this;
        }

        public Builder street(String street) {
            this.street = street;
            return this;
        }

        public Builder detail(String detail) {
            this.detail = detail;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * 获取完整地址
     */
    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        if (country != null) sb.append(country);
        if (region != null) sb.append(region);
        if (locality != null) sb.append(locality);
        if (street != null) sb.append(street);
        if (detail != null) sb.append(detail);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(country, address.country) &&
                Objects.equals(region, address.region) &&
                Objects.equals(locality, address.locality) &&
                Objects.equals(street, address.street) &&
                Objects.equals(detail, address.detail) &&
                Objects.equals(postalCode, address.postalCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, locality, street, detail, postalCode);
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}
