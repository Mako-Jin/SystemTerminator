package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourcesIdentity;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Version;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.tools.StringUtils;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:09
 */
@Getter
public class ResourceEntity extends AbstractAggregate<ResourceId> {

    private ResourceEntity(ResourceId resourceId, ResourcesIdentity identity) {
        super(resourceId);
        this.identity = identity;
    }

    private final ResourcesIdentity identity;
    /**
     * 资源名称
     */
    private String resourceName;
    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 是否已弃用；0：未；1：已
     */
    private Integer isDeprecated;

    /**
     * 是否启用；0：未；1：已
     */
    private Integer isEnabled;

    /**
     * 是否白名单；0：不是；1：是
     */
    private Integer isWhiteList;

    /**
     * 菜单显示图标
     */
    private String icon;

    /**
     * 版本
     */
    private Version version;

    /**
     * 父资源编码列表，逗号分割
     */
    private List<String> parentCode;

    /**
     * 联系人信息
     */
    private List<ContactInfoEntity> contactInfoModelList;

    public static ResourceEntity build(
            ResourceValue resourceValue,
            String resourceName,
            String resourceDesc,
            Integer resourceType,
            List<String> requestUrl,
            List<String> requestMethod,
            List<String> parentCode,
            String icon,
            String versionStr
    ) {
        Version version = Version.of(versionStr);
        ResourcesIdentity identity = ResourcesIdentity.of(
                resourceValue,
                resourceType,
                requestUrl,
                requestMethod
        );
        // 创建实体
        ResourceEntity entity = new ResourceEntity(ResourceId.nextId(), identity);
        entity.resourceName = StringUtils.requireNonBlank(resourceName, "资源名称不能为空");
        entity.resourceDesc = resourceDesc;
        entity.icon = icon;
        entity.version = version;
        entity.parentCode = parentCode;
        entity.isEnabled = OppositeEnums.YES.getCode();
        entity.isDeprecated = OppositeEnums.FALSE.getCode();
        entity.isWhiteList = OppositeEnums.FALSE.getCode();
        return entity;
    }

    public ResourceEntity upgrade(ResourceEntity newResourceEntity) {
        Version newVersion = calculateNewVersion(newResourceEntity);
        ResourceEntity upgraded = new ResourceEntity(this.getId(), newResourceEntity.getIdentity());
        // 3. 复制新数据
        upgraded.resourceName = newResourceEntity.resourceName;
        upgraded.resourceDesc = newResourceEntity.resourceDesc;
        upgraded.isDeprecated = newResourceEntity.isDeprecated;
        upgraded.isEnabled = newResourceEntity.isEnabled;
        upgraded.isWhiteList = newResourceEntity.isWhiteList;
        upgraded.icon = newResourceEntity.icon;
        upgraded.version = newVersion;
        upgraded.parentCode = parentCode;
        upgraded.contactInfoModelList = newResourceEntity.contactInfoModelList;
        return upgraded;
    }

    private Version calculateNewVersion (ResourceEntity newResourceEntity) {
        // 1. 如果客户指定了版本，优先采用客户版本
        Version newVersion;
        if (Objects.isNull(newResourceEntity.getVersion())) {
            // 主要属性变更，主版本+1
            if (Objects.equals(this.identity, newResourceEntity.getIdentity())) {
                newVersion = this.version.bumpMajor();
            } else if (
                Objects.equals(this.isDeprecated, newResourceEntity.getIsDeprecated())
                && Objects.equals(this.isDeprecated, newResourceEntity.getIsDeprecated())
                && Objects.equals(this.isDeprecated, newResourceEntity.getIsDeprecated())
            ) {
                newVersion = this.version.bumpMinor();
            } else if (
                Objects.equals(this.isWhiteList, newResourceEntity.getIsWhiteList())
                && Objects.equals(this.resourceName, newResourceEntity.getResourceName())
                && Objects.equals(this.parentCode, newResourceEntity.getParentCode())
            ) {
                newVersion = this.version.bumpPatch();
            } else {
                newVersion = this.version.bumpBuild();
            }
        } else {
            if (newResourceEntity.getVersion().compareTo(this.version) >= 0) {
                newVersion = newResourceEntity.getVersion();
            } else {
                throw new IllegalArgumentException("客户版本不能低于当前版本");
            }
        }
        if (newResourceEntity.getVersion() != null && !newResourceEntity.getVersion().equals(this.version)) {
            // 验证客户版本是否合法（不能低于当前版本）
            if (newResourceEntity.getVersion().compareTo(this.version) > 0) {
                // 采用客户版本
                newVersion = newResourceEntity.getVersion();
            } else {
                throw new IllegalArgumentException("客户版本不能低于当前版本");
            }
        }
        return newVersion;
    }

    public boolean isSameAs(ResourceEntity other) {
        if (Objects.isNull(other)) {
            return false;
        }
        return this.identity.equals(other.identity);
    }

    /**
     * 开启白名单
     */
    public void activateWhiteList() {
        this.isWhiteList = OppositeEnums.TRUE.getCode();
        // 版本号+1
        this.version = version.bumpPatch();
    }

    /**
     * 关闭白名单
     */
    public void deactivateWhiteList() {
        this.isWhiteList = OppositeEnums.FALSE.getCode();
        // 版本号+1
        this.version = version.bumpPatch();
    }

    /**
     * 设置白名单参数  PO转Entity的时候版本是不加1的
     */
    public void reconstructionWhiteList(Integer isWhiteList) {
        this.isWhiteList = isWhiteList;
    }

    /**
     * 启用
     */
    public void enable() {
        this.isEnabled = OppositeEnums.TRUE.getCode();
        // 版本号+1
        this.version = version.bumpBuild();
    }

    /**
     * 停用
     */
    public void disable() {
        this.isEnabled = OppositeEnums.FALSE.getCode();
        // 版本号+1
        this.version = version.bumpBuild();
    }

    /**
     * 设置白名单参数
     */
    public void reconstructionEnable(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    /**
     * 标记过期
     */
    public void markDeprecated() {
        this.isDeprecated = OppositeEnums.TRUE.getCode();
        // 版本号+1
        this.version = version.bumpPatch();
    }

    /**
     * 停用
     */
    public void markNotDeprecated() {
        this.isDeprecated = OppositeEnums.FALSE.getCode();
        // 版本号+1
        this.version = version.bumpPatch();
    }

    /**
     * 设置白名单参数
     */
    public void reconstructionDeprecated(Integer isDeprecated) {
        this.isDeprecated = isDeprecated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResourceEntity entity = (ResourceEntity) o;

        if (!Objects.equals(identity, entity.identity)) {
            return false;
        }
        if (!Objects.equals(resourceName, entity.resourceName)) {
            return false;
        }
        if (!Objects.equals(resourceDesc, entity.resourceDesc)) {
            return false;
        }
        if (!Objects.equals(isDeprecated, entity.isDeprecated)) {
            return false;
        }
        if (!Objects.equals(isEnabled, entity.isEnabled)) {
            return false;
        }
        if (!Objects.equals(isWhiteList, entity.isWhiteList)) {
            return false;
        }
        if (!Objects.equals(icon, entity.icon)) {
            return false;
        }
        if (!Objects.equals(version, entity.version)) {
            return false;
        }
        if (!Objects.equals(parentCode, entity.parentCode)) {
            return false;
        }
        return Objects.equals(contactInfoModelList, entity.contactInfoModelList);
    }

    @Override
    public int hashCode() {
        int result = identity != null ? identity.hashCode() : 0;
        result = 31 * result + (resourceName != null ? resourceName.hashCode() : 0);
        result = 31 * result + (resourceDesc != null ? resourceDesc.hashCode() : 0);
        result = 31 * result + (isDeprecated != null ? isDeprecated.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        result = 31 * result + (isWhiteList != null ? isWhiteList.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        result = 31 * result + (contactInfoModelList != null ? contactInfoModelList.hashCode() : 0);
        return result;
    }
}
