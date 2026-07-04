package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.constants.AuthI18nKeyConstants;
import com.yaocode.sts.auth.domain.enums.OrganizationTypeEnums;
import com.yaocode.sts.auth.domain.events.organization.OrganizationAddUserEvent;
import com.yaocode.sts.auth.domain.events.organization.OrganizationCreatedEvent;
import com.yaocode.sts.auth.domain.events.organization.OrganizationDisabledEvent;
import com.yaocode.sts.auth.domain.events.organization.OrganizationEnabledEvent;
import com.yaocode.sts.auth.domain.events.organization.OrganizationRemoveUserEvent;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CompanyId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.OrganizationCode;
import com.yaocode.sts.common.basic.constants.SymbolConstants;
import com.yaocode.sts.common.basic.enums.EnableEnums;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * 组织聚合根
 * 管理组织的树形结构、成员关系
 * 对应表：auth_tbl_org_info
 */
@Getter
public class OrganizationInfoAggregate extends AbstractAggregate<OrganizationId> {

    // ============ 核心属性 ============
    private TenantId tenantId;
    private String organizationName;
    private String fullName;
    private OrganizationCode organizationCode;
    private String organizationDesc;
    private OrganizationTypeEnums organizationType;
    private Integer sort;
    private OrganizationId parentId;
    private String organizationCodePath;
    private Integer level;
    private YesNoEnums hasChild;
    private String contact;
    private String phone;
    private String fax;
    private String email;
    private EnableEnums status;
    private YesNoEnums isPrimary;
    private UserId managerId;
    private String managerName;
    private CompanyId companyId;

    // ============ 跨聚合引用 ============
    private Set<UserId> userIds = new HashSet<>();
    private Set<UserGroupId> userGroupIds = new HashSet<>();
    private Set<RoleId> roleIds = new HashSet<>();

    // ============ 子组织 ============
    private Set<OrganizationId> childOrganizationIds = new HashSet<>();

    // ============ 构造函数 ============
    private OrganizationInfoAggregate(OrganizationId organizationId) {
        super(organizationId);
        this.status = EnableEnums.ENABLED;
        this.hasChild = YesNoEnums.NO;
        this.isPrimary = YesNoEnums.NO;
        this.level = 1;
        this.organizationType = OrganizationTypeEnums.ENTITY;
    }

    // ============ 工厂方法 ============

    /**
     * 创建组织（根组织）
     */
    public static OrganizationInfoAggregate createRoot(
            TenantId tenantId,
            String organizationName,
            OrganizationCode organizationCode,
            String organizationDesc,
            OrganizationTypeEnums organizationType,
            String contact,
            String phone,
            String email
    ) {
        OrganizationInfoAggregate org = new OrganizationInfoAggregate(OrganizationId.nextId());
        org.tenantId = tenantId;
        org.organizationName = organizationName;
        org.fullName = organizationName;
        org.organizationCode = organizationCode;
        org.organizationDesc = organizationDesc;
        org.organizationType = organizationType != null ? organizationType : OrganizationTypeEnums.ENTITY;
        org.level = 1;
        org.organizationCodePath = organizationCode.getValue();
        org.contact = contact;
        org.phone = phone;
        org.email = email;

        org.registerEvent(new OrganizationCreatedEvent(org.getId(), organizationName));

        return org;
    }

    /**
     * 创建子组织
     */
    public static OrganizationInfoAggregate createChild(
            TenantId tenantId,
            OrganizationInfoAggregate parentOrg,
            String organizationName,
            OrganizationCode organizationCode,
            String organizationDesc,
            OrganizationTypeEnums organizationType,
            String contact,
            String phone,
            String email
    ) {
        OrganizationInfoAggregate org = new OrganizationInfoAggregate(OrganizationId.nextId());
        org.tenantId = tenantId;
        org.organizationName = organizationName;
        org.fullName = organizationName;
        org.organizationCode = organizationCode;
        org.organizationDesc = organizationDesc;
        org.organizationType = organizationType != null ? organizationType : OrganizationTypeEnums.ENTITY;
        org.parentId = parentOrg.getId();
        org.level = parentOrg.getLevel() + 1;
        org.organizationCodePath = parentOrg.getOrganizationCodePath() + SymbolConstants.FORWARD_SLASH + organizationCode.getValue();
        org.contact = contact;
        org.phone = phone;
        org.email = email;

        // 更新父组织的有子节点标记
        parentOrg.hasChild = YesNoEnums.YES;
        parentOrg.childOrganizationIds.add(org.getId());

        org.registerEvent(new OrganizationCreatedEvent(org.getId(), organizationName));

        return org;
    }

    /**
     * 从数据库重建组织聚合
     */
    public static OrganizationInfoAggregate reconstruct(
            OrganizationId organizationId,
            TenantId tenantId,
            String organizationName,
            String fullName,
            OrganizationCode organizationCode,
            String organizationDesc,
            OrganizationTypeEnums organizationType,
            Integer sort,
            OrganizationId parentId,
            String organizationCodePath,
            Integer level,
            YesNoEnums hasChild,
            String contact,
            String phone,
            String fax,
            String email,
            EnableEnums status,
            YesNoEnums isPrimary,
            UserId managerId,
            String managerName,
            CompanyId companyId,
            Set<UserId> userIds,
            Set<UserGroupId> userGroupIds,
            Set<RoleId> roleIds,
            Set<OrganizationId> childOrganizationIds
    ) {
        OrganizationInfoAggregate org = new OrganizationInfoAggregate(organizationId);
        org.tenantId = tenantId;
        org.organizationName = organizationName;
        org.fullName = fullName;
        org.organizationCode = organizationCode;
        org.organizationDesc = organizationDesc;
        org.organizationType = organizationType != null ? organizationType : OrganizationTypeEnums.ENTITY;
        org.sort = sort;
        org.parentId = parentId;
        org.organizationCodePath = organizationCodePath;
        org.level = level != null ? level : 1;
        org.hasChild = hasChild != null ? hasChild : YesNoEnums.NO;
        org.contact = contact;
        org.phone = phone;
        org.fax = fax;
        org.email = email;
        org.status = status != null ? status : EnableEnums.ENABLED;
        org.isPrimary = isPrimary != null ? isPrimary : YesNoEnums.NO;
        org.managerId = managerId;
        org.managerName = managerName;
        org.companyId = companyId;
        org.userIds = userIds != null ? new HashSet<>(userIds) : new HashSet<>();
        org.userGroupIds = userGroupIds != null ? new HashSet<>(userGroupIds) : new HashSet<>();
        org.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        org.childOrganizationIds = childOrganizationIds != null ? new HashSet<>(childOrganizationIds) : new HashSet<>();
        return org;
    }

    // ============ 业务行为 ============

    // ----- 基本信息管理 -----

    /**
     * 更新组织名称
     */
    public void updateName(String organizationName) {
        if (organizationName == null || organizationName.trim().isEmpty()) {
            throw new DomainException(AuthI18nKeyConstants.ORGANIZATION_NAME_CANNOT_BE_BLANK);
        }
        this.organizationName = organizationName.trim();
    }

    /**
     * 更新组织全称
     */
    public void updateFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * 更新组织描述
     */
    public void updateDescription(String organizationDesc) {
        this.organizationDesc = organizationDesc;
    }

    /**
     * 更新排序
     */
    public void updateSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 更新联系人信息
     */
    public void updateContact(String contact, String phone, String fax, String email) {
        this.contact = contact;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
    }

    /**
     * 更新负责人
     */
    public void updateManager(UserId managerId, String managerName) {
        this.managerId = managerId;
        this.managerName = managerName;
    }

    // ----- 状态管理 -----

    /**
     * 启用组织
     */
    public void enable() {
        this.status = EnableEnums.ENABLED;
        registerEvent(new OrganizationEnabledEvent(this.getId()));
    }

    /**
     * 禁用组织
     */
    public void disable() {
        this.status = EnableEnums.DISABLED;
        registerEvent(new OrganizationDisabledEvent(this.getId()));
    }

    /**
     * 标记为主组织
     */
    public void markPrimary() {
        this.isPrimary = YesNoEnums.YES;
    }

    /**
     * 取消主组织标记
     */
    public void unmarkPrimary() {
        this.isPrimary = YesNoEnums.NO;
    }

    // ----- 成员管理 -----

    /**
     * 添加成员
     */
    public void addMember(UserId userId) {
        if (userIds.contains(userId)) {
            throw new DomainException(AuthI18nKeyConstants.USER_ALREADY_IN_ORGANIZATION);
        }
        userIds.add(userId);
        registerEvent(new OrganizationAddUserEvent(this.getId(), userId));
    }

    /**
     * 移除成员
     */
    public void removeMember(UserId userId) {
        if (!userIds.contains(userId)) {
            throw new DomainException(AuthI18nKeyConstants.USER_NOT_IN_ORGANIZATION);
        }
        userIds.remove(userId);
        registerEvent(new OrganizationRemoveUserEvent(this.getId(), userId));
    }

    /**
     * 批量添加成员
     */
    public void addMembers(Set<UserId> userIds) {
        this.userIds.addAll(userIds);
    }

    /**
     * 添加用户组关联
     */
    public void addUserGroup(UserGroupId userGroupId) {
        if (userGroupIds.contains(userGroupId)) {
            throw new DomainException(AuthI18nKeyConstants.USER_GROUP_ALREADY_ASSOCIATED_WITH_ORGANIZATION);
        }
        userGroupIds.add(userGroupId);
    }

    /**
     * 移除用户组关联
     */
    public void removeUserGroup(UserGroupId userGroupId) {
        userGroupIds.remove(userGroupId);
    }

    /**
     * 添加角色关联
     */
    public void addRole(RoleId roleId) {
        if (roleIds.contains(roleId)) {
            throw new DomainException(AuthI18nKeyConstants.ROLE_ALREADY_ASSOCIATED_WITH_ORGANIZATION);
        }
        roleIds.add(roleId);
    }

    /**
     * 移除角色关联
     */
    public void removeRole(RoleId roleId) {
        roleIds.remove(roleId);
    }

    // ----- 树形结构管理 -----

    /**
     * 判断是否为根组织
     */
    public boolean isRoot() {
        return parentId == null;
    }

    /**
     * 判断是否有子组织
     */
    public boolean hasChildren() {
        return hasChild == YesNoEnums.YES;
    }

    // ----- 查询方法 -----

    /**
     * 判断组织是否启用
     */
    public boolean isEnabled() {
        return status == EnableEnums.ENABLED;
    }

    /**
     * 判断是否为主组织
     */
    public boolean isPrimaryOrganization() {
        return isPrimary == YesNoEnums.YES;
    }

    /**
     * 获取完整路径
     */
    public String getFullPath() {
        return organizationCodePath;
    }

    /**
     * 获取成员数量
     */
    public int getMemberCount() {
        return userIds.size();
    }
}