package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.CompanyId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Address;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import lombok.Getter;

import java.util.Objects;

/**
 * 公司信息实体（属于租户聚合）
 * 对应表：auth_tbl_company_info
 */
@Getter
public class CompanyInfoEntity {

    private final CompanyId companyId;
    private final TenantId tenantId;
    private String companyName;
    private String fullName;
    private Address address;
    private String contact;
    private PhoneNum phone;
    private String fax;
    private Email email;
    private String description;
    private String logo;
    private String domain;
    private String frontTitle;
    private String consoleDomain;
    private String consoleTitle;
    private String defaultUri;

    private CompanyInfoEntity(CompanyId companyId, TenantId tenantId) {
        this.companyId = companyId;
        this.tenantId = tenantId;
    }

    // ========== 工厂方法 ==========

    public static CompanyInfoEntity create(
            TenantId tenantId,
            String companyName,
            String fullName,
            Address address,
            String contact,
            PhoneNum phone,
            String fax,
            Email email,
            String description,
            String logo,
            String domain,
            String frontTitle,
            String consoleDomain,
            String consoleTitle,
            String defaultUri
    ) {
        CompanyInfoEntity entity = new CompanyInfoEntity(CompanyId.nextId(), tenantId);
        entity.companyName = companyName;
        entity.fullName = fullName;
        entity.address = address;
        entity.contact = contact;
        entity.phone = phone;
        entity.fax = fax;
        entity.email = email;
        entity.description = description;
        entity.logo = logo;
        entity.domain = domain;
        entity.frontTitle = frontTitle;
        entity.consoleDomain = consoleDomain;
        entity.consoleTitle = consoleTitle;
        entity.defaultUri = defaultUri;
        return entity;
    }

    public static CompanyInfoEntity reconstruct(
            CompanyId companyId,
            TenantId tenantId,
            String companyName,
            String fullName,
            Address address,
            String contact,
            PhoneNum phone,
            String fax,
            Email email,
            String description,
            String logo,
            String domain,
            String frontTitle,
            String consoleDomain,
            String consoleTitle,
            String defaultUri
    ) {
        CompanyInfoEntity entity = new CompanyInfoEntity(companyId, tenantId);
        entity.companyName = companyName;
        entity.fullName = fullName;
        entity.address = address;
        entity.contact = contact;
        entity.phone = phone;
        entity.fax = fax;
        entity.email = email;
        entity.description = description;
        entity.logo = logo;
        entity.domain = domain;
        entity.frontTitle = frontTitle;
        entity.consoleDomain = consoleDomain;
        entity.consoleTitle = consoleTitle;
        entity.defaultUri = defaultUri;
        return entity;
    }

    // ========== 业务行为 ==========

    public void updateCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty()) {
            throw new IllegalArgumentException("公司名称不能为空");
        }
        this.companyName = companyName.trim();
    }

    public void updateFullName(String fullName) {
        this.fullName = fullName;
    }

    public void updateAddress(Address address) {
        this.address = address;
    }

    public void updateContact(String contact, PhoneNum phone, String fax, Email email) {
        this.contact = contact;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
    }

    public void updateBranding(String logo, String frontTitle, String consoleTitle) {
        this.logo = logo;
        this.frontTitle = frontTitle;
        this.consoleTitle = consoleTitle;
    }

    public void updateDomain(String domain, String consoleDomain, String defaultUri) {
        this.domain = domain;
        this.consoleDomain = consoleDomain;
        this.defaultUri = defaultUri;
    }

    public String getDisplayName() {
        return frontTitle != null ? frontTitle : companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyInfoEntity that = (CompanyInfoEntity) o;
        return Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId);
    }
}
