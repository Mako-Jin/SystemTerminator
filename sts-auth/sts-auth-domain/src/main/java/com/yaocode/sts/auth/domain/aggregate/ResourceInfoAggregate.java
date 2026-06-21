package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.entity.ResourceContactEntity;
import com.yaocode.sts.auth.domain.events.resource.ResourceAddedToWhiteListEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceCreatedEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceDeprecatedEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceDisabledEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceEnabledEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceRemovedFromWhiteListEvent;
import com.yaocode.sts.auth.domain.events.resource.ResourceUpgradedEvent;
import com.yaocode.sts.auth.domain.valueobjects.composites.ResourcesIdentity;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Version;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 资源聚合根
 * 管理菜单、按钮、API等权限资源的定义和版本
 * 对应表：auth_tbl_resource
 */
@Getter
public class ResourceInfoAggregate extends AbstractAggregate<ResourceId> {

    // ============ 核心属性 ============
    private TenantId tenantId;
    private ResourcesIdentity identity;
    private String resourceName;
    private String resourceDesc;
    private String requestUrl;
    private String requestMethod;
    private OppositeEnums isDeprecated;
    private OppositeEnums isWhiteList;
    private OppositeEnums isEnabled;
    private String icon;
    private Version version;
    private List<String> parentCode;
    private Integer sort;

    // ============ 子实体 ============
    private List<ResourceContactEntity> contacts = new ArrayList<>();

    // ============ 构造函数 ============
    private ResourceInfoAggregate(ResourceId resourceId) {
        super(resourceId);
        this.isDeprecated = OppositeEnums.NO;
        this.isWhiteList = OppositeEnums.NO;
        this.isEnabled = OppositeEnums.YES;
        this.version = Version.initial();
    }

    // ============ 工厂方法 ============

    /**
     * 创建资源
     */
    public static ResourceInfoAggregate create(
            TenantId tenantId,
            ResourceValue resourceValue,
            ResourceTypeEnums resourceType,
            String resourceName,
            String resourceDesc,
            String requestUrl,
            String requestMethod,
            String icon,
            List<String> parentCode,
            Integer sort
    ) {
        ResourceInfoAggregate resource = new ResourceInfoAggregate(ResourceId.nextId());
        resource.tenantId = tenantId;
        resource.identity = ResourcesIdentity.of(resourceValue, resourceType, requestUrl != null ? List.of(requestUrl) : null, requestMethod != null ? List.of(requestMethod) : null);
        resource.resourceName = resourceName;
        resource.resourceDesc = resourceDesc;
        resource.requestUrl = requestUrl;
        resource.requestMethod = requestMethod;
        resource.icon = icon;
        resource.parentCode = parentCode;
        resource.sort = sort != null ? sort : 0;

        resource.registerEvent(new ResourceCreatedEvent(resource.getId(), resourceName));

        return resource;
    }

    /**
     * 创建API资源
     */
    public static ResourceInfoAggregate createApi(
            TenantId tenantId,
            ResourceValue resourceValue,
            String resourceName,
            String resourceDesc,
            String requestUrl,
            String requestMethod,
            List<String> parentCode
    ) {
        return create(
                tenantId,
                resourceValue,
                ResourceTypeEnums.API,
                resourceName,
                resourceDesc,
                requestUrl,
                requestMethod,
                null,
                parentCode,
                0
        );
    }

    /**
     * 创建菜单资源
     */
    public static ResourceInfoAggregate createMenu(
            TenantId tenantId,
            ResourceValue resourceValue,
            String resourceName,
            String resourceDesc,
            String icon,
            List<String> parentCode,
            Integer sort
    ) {
        return create(
                tenantId,
                resourceValue,
                ResourceTypeEnums.MENUS,
                resourceName,
                resourceDesc,
                null,
                null,
                icon,
                parentCode,
                sort
        );
    }

    /**
     * 从数据库重建资源聚合
     */
    public static ResourceInfoAggregate reconstruct(
            ResourceId resourceId,
            TenantId tenantId,
            ResourcesIdentity identity,
            String resourceName,
            String resourceDesc,
            String requestUrl,
            String requestMethod,
            OppositeEnums isDeprecated,
            OppositeEnums isWhiteList,
            OppositeEnums isEnabled,
            String icon,
            Version version,
            List<String> parentCode,
            Integer sort,
            List<ResourceContactEntity> contacts
    ) {
        ResourceInfoAggregate resource = new ResourceInfoAggregate(resourceId);
        resource.tenantId = tenantId;
        resource.identity = identity;
        resource.resourceName = resourceName;
        resource.resourceDesc = resourceDesc;
        resource.requestUrl = requestUrl;
        resource.requestMethod = requestMethod;
        resource.isDeprecated = isDeprecated != null ? isDeprecated : OppositeEnums.NO;
        resource.isWhiteList = isWhiteList != null ? isWhiteList : OppositeEnums.NO;
        resource.isEnabled = isEnabled != null ? isEnabled : OppositeEnums.YES;
        resource.icon = icon;
        resource.version = version != null ? version : Version.initial();
        resource.parentCode = parentCode;
        resource.sort = sort != null ? sort : 0;
        resource.contacts = contacts != null ? new ArrayList<>(contacts) : new ArrayList<>();
        return resource;
    }

    // ============ 业务行为 ============

    // ----- 基本信息管理 -----

    /**
     * 更新资源名称
     */
    public void updateName(String resourceName) {
        if (resourceName == null || resourceName.trim().isEmpty()) {
            throw new DomainException("资源名称不能为空");
        }
        this.resourceName = resourceName.trim();
        this.version = version.bumpPatch();
    }

    /**
     * 更新资源描述
     */
    public void updateDescription(String resourceDesc) {
        this.resourceDesc = resourceDesc;
        this.version = version.bumpPatch();
    }

    /**
     * 更新图标
     */
    public void updateIcon(String icon) {
        this.icon = icon;
        this.version = version.bumpPatch();
    }

    // ----- 状态管理 -----

    /**
     * 启用资源
     */
    public void enable() {
        if (this.isEnabled == OppositeEnums.YES) {
            throw new DomainException("资源已启用");
        }
        this.isEnabled = OppositeEnums.YES;
        this.version = version.bumpBuild();
        registerEvent(new ResourceEnabledEvent(this.getId()));
    }

    /**
     * 停用资源
     */
    public void disable() {
        if (this.isEnabled == OppositeEnums.NO) {
            throw new DomainException("资源已停用");
        }
        this.isEnabled = OppositeEnums.NO;
        this.version = version.bumpBuild();
        registerEvent(new ResourceDisabledEvent(this.getId()));
    }

    /**
     * 标记为废弃
     */
    public void markDeprecated() {
        if (this.isDeprecated == OppositeEnums.YES) {
            throw new DomainException("资源已标记为废弃");
        }
        this.isDeprecated = OppositeEnums.YES;
        this.version = version.bumpMajor();
        registerEvent(new ResourceDeprecatedEvent(this.getId()));
    }

    /**
     * 取消废弃标记
     */
    public void unmarkDeprecated() {
        if (this.isDeprecated == OppositeEnums.NO) {
            throw new DomainException("资源未标记为废弃");
        }
        this.isDeprecated = OppositeEnums.NO;
        this.version = version.bumpPatch();
    }

    /**
     * 加入白名单
     */
    public void addToWhiteList() {
        if (this.isWhiteList == OppositeEnums.YES) {
            throw new DomainException("资源已在白名单中");
        }
        this.isWhiteList = OppositeEnums.YES;
        this.version = version.bumpPatch();
        registerEvent(new ResourceAddedToWhiteListEvent(this.getId()));
    }

    /**
     * 移出白名单
     */
    public void removeFromWhiteList() {
        if (this.isWhiteList == OppositeEnums.NO) {
            throw new DomainException("资源不在白名单中");
        }
        this.isWhiteList = OppositeEnums.NO;
        this.version = version.bumpPatch();
        registerEvent(new ResourceRemovedFromWhiteListEvent(this.getId()));
    }

    // ----- 联系人管理 -----

    /**
     * 添加联系人
     */
    public void addContact(ResourceContactEntity contact) {
        if (!contact.getResourceId().equals(this.getId())) {
            throw new DomainException("联系人不属于当前资源");
        }
        // 如果是主要联系人，降级其他
        if (contact.isPrimaryContact()) {
            contacts.forEach(ResourceContactEntity::unmarkPrimary);
        }
        contacts.add(contact);
    }

    /**
     * 删除联系人
     */
    public void removeContact(ContactId contactId) {
        contacts.removeIf(c -> c.getContactId().equals(contactId));
    }

    /**
     * 设置主要联系人
     */
    public void setPrimaryContact(ContactId contactId) {
        contacts.forEach(ResourceContactEntity::unmarkPrimary);
        ResourceContactEntity contact = contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new DomainException("联系人不存在"));
        contact.markPrimary();
    }

    // ----- 版本升级 -----

    /**
     * 升级资源（版本+1）
     */
    public ResourceInfoAggregate upgrade(ResourceInfoAggregate newResource) {
        if (!this.identity.equals(newResource.identity)) {
            // 资源标识变化，主版本+1
            this.version = version.bumpMajor();
        } else {
            this.version = version.bumpMinor();
        }

        this.resourceName = newResource.resourceName;
        this.resourceDesc = newResource.resourceDesc;
        this.requestUrl = newResource.requestUrl;
        this.requestMethod = newResource.requestMethod;
        this.icon = newResource.icon;
        this.parentCode = newResource.parentCode;
        this.contacts = newResource.contacts;

        registerEvent(new ResourceUpgradedEvent(this.getId(), this.version));

        return this;
    }

    // ----- 查询方法 -----

    /**
     * 判断资源是否启用
     */
    public boolean isEnabled() {
        return isEnabled == OppositeEnums.YES;
    }

    /**
     * 判断资源是否废弃
     */
    public boolean isDeprecated() {
        return isDeprecated == OppositeEnums.YES;
    }

    /**
     * 判断资源是否在白名单中
     */
    public boolean isWhiteListed() {
        return isWhiteList == OppositeEnums.YES;
    }

    /**
     * 判断是否为菜单
     */
    public boolean isMenu() {
        return identity.getResourceType().isMenu();
    }

    /**
     * 判断是否为API
     */
    public boolean isApi() {
        return identity.getResourceType().isApi();
    }

    /**
     * 获取完整的资源路径
     */
    public String getFullPath() {
        if (parentCode == null || parentCode.isEmpty()) {
            return identity.getResourceValue().getValue();
        }
        return String.join("/", parentCode) + "/" + identity.getResourceValue().getValue();
    }

    /**
     * 获取主要联系人
     */
    public Optional<ResourceContactEntity> getPrimaryContact() {
        return contacts.stream().filter(ResourceContactEntity::isPrimaryContact).findFirst();
    }
}
