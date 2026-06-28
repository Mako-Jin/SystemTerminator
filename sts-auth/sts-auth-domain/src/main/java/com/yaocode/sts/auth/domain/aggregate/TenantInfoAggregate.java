package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.entity.BrandConfigEntity;
import com.yaocode.sts.auth.domain.entity.CompanyInfoEntity;
import com.yaocode.sts.auth.domain.entity.InstanceInfoEntity;
import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.entity.TenantSecurityEntity;
import com.yaocode.sts.auth.domain.enums.BrandTargetTypeEnums;
import com.yaocode.sts.auth.domain.enums.TenantStatusEnums;
import com.yaocode.sts.auth.domain.events.client.InstanceAddedToTenantEvent;
import com.yaocode.sts.auth.domain.events.client.InstanceRemovedFromTenantEvent;
import com.yaocode.sts.auth.domain.events.tenant.CompanyAddedToTenantEvent;
import com.yaocode.sts.auth.domain.events.tenant.CompanyRemovedFromTenantEvent;
import com.yaocode.sts.auth.domain.events.tenant.TenantActivatedEvent;
import com.yaocode.sts.auth.domain.events.tenant.TenantCreatedEvent;
import com.yaocode.sts.auth.domain.events.tenant.TenantRemoveUserEvent;
import com.yaocode.sts.auth.domain.events.tenant.TenantSuspendedEvent;
import com.yaocode.sts.auth.domain.events.tenant.UserAddedToTenantEvent;
import com.yaocode.sts.auth.domain.valueobjects.composites.Branding;
import com.yaocode.sts.auth.domain.valueobjects.composites.LoginConfig;
import com.yaocode.sts.auth.domain.valueobjects.composites.MFAConfig;
import com.yaocode.sts.auth.domain.valueobjects.composites.PasswordPolicy;
import com.yaocode.sts.auth.domain.valueobjects.composites.SessionConfig;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.BrandConfigId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CompanyId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.InstanceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.TenantCode;
import com.yaocode.sts.common.basic.enums.YesNoEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 租户聚合根
 * 管理租户信息、配置、品牌、公司、实例等
 * 对应表：auth_tbl_tenant
 */
@Getter
public class TenantInfoAggregate extends AbstractAggregate<TenantId> {

    // ============ 核心属性 ============
    private String tenantName;
    private TenantCode tenantCode;
    private String tenantDesc;
    private TenantStatusEnums tenantStatus;
    private Integer tenantLevel;
    private YesNoEnums allowRegister;
    private YesNoEnums allowAdd;
    private TenantId parentId;

    // ============ 子实体 ============
    private TenantConfigEntity config;
    private List<BrandConfigEntity> brandConfigs = new ArrayList<>();
    private List<CompanyInfoEntity> companies = new ArrayList<>();
    private List<InstanceInfoEntity> instances = new ArrayList<>();
    private final List<TenantSecurityEntity> securityConfigs = new ArrayList<>();

    // ============ 跨聚合引用 ============
    private Set<UserId> userIds = new HashSet<>();
    private Set<OrganizationId> organizationIds = new HashSet<>();
    private Set<RoleId> roleIds = new HashSet<>();
    private Set<UserGroupId> userGroupIds = new HashSet<>();

    // ============ 构造函数 ============
    private TenantInfoAggregate(TenantId tenantId) {
        super(tenantId);
    }

    // ============ 工厂方法 ============

    /**
     * 创建租户
     */
    public static TenantInfoAggregate create(
            String tenantName,
            TenantCode tenantCode,
            String tenantDesc,
            Integer tenantLevel,
            TenantId parentId,
            TenantStatusEnums tenantStatus
    ) {
        TenantInfoAggregate tenant = new TenantInfoAggregate(TenantId.nextId());
        tenant.tenantName = tenantName;
        tenant.tenantCode = tenantCode;
        tenant.tenantDesc = tenantDesc;
        tenant.tenantLevel = tenantLevel;
        tenant.parentId = parentId;
        tenant.tenantStatus = tenantStatus != null ? tenantStatus : TenantStatusEnums.ACTIVATE;

        // 创建默认配置
        tenant.config = TenantConfigEntity.create(
                tenant.getId(),
                PasswordPolicy.defaultPolicy(),
                SessionConfig.defaultConfig(),
                MFAConfig.disabled(),
                LoginConfig.defaultConfig(),
                null,
                null
        );

        // 发布领域事件
        tenant.registerEvent(new TenantCreatedEvent(tenant.getId(), tenantName, tenantCode));

        return tenant;
    }

    /**
     * 从数据库重建租户聚合
     */
    public static TenantInfoAggregate reconstruct(
            TenantId tenantId,
            String tenantName,
            TenantCode tenantCode,
            String tenantDesc,
            Integer tenantLevel,
            TenantId parentId,
            TenantStatusEnums tenantStatus,
            TenantConfigEntity config,
            List<BrandConfigEntity> brandConfigs,
            List<CompanyInfoEntity> companies,
            List<InstanceInfoEntity> instances,
            Set<UserId> userIds,
            Set<OrganizationId> organizationIds,
            Set<RoleId> roleIds,
            Set<UserGroupId> userGroupIds
    ) {
        TenantInfoAggregate tenant = new TenantInfoAggregate(tenantId);
        tenant.tenantName = tenantName;
        tenant.tenantCode = tenantCode;
        tenant.tenantDesc = tenantDesc;
        tenant.tenantLevel = tenantLevel;
        tenant.parentId = parentId;
        tenant.tenantStatus = tenantStatus;
        tenant.config = config;
        tenant.brandConfigs = brandConfigs != null ? new ArrayList<>(brandConfigs) : new ArrayList<>();
        tenant.companies = companies != null ? new ArrayList<>(companies) : new ArrayList<>();
        tenant.instances = instances != null ? new ArrayList<>(instances) : new ArrayList<>();
        tenant.userIds = userIds != null ? new HashSet<>(userIds) : new HashSet<>();
        tenant.organizationIds = organizationIds != null ? new HashSet<>(organizationIds) : new HashSet<>();
        tenant.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        tenant.userGroupIds = userGroupIds != null ? new HashSet<>(userGroupIds) : new HashSet<>();
        return tenant;
    }

    // ============ 业务行为 ============

    // ----- 租户基本信息管理 -----

    /**
     * 激活租户
     */
    public void activate() {
        this.tenantStatus = TenantStatusEnums.ACTIVATE;
        registerEvent(new TenantActivatedEvent(this.getId()));
    }

    /**
     * 停用租户
     */
    public void suspend() {
        this.tenantStatus = TenantStatusEnums.SUSPENDED;
        registerEvent(new TenantSuspendedEvent(this.getId()));
    }

    /**
     * 更新租户名称
     */
    public void updateName(String tenantName) {
        if (tenantName == null || tenantName.trim().isEmpty()) {
            throw new IllegalArgumentException("租户名称不能为空");
        }
        this.tenantName = tenantName.trim();
    }

    /**
     * 更新租户描述
     */
    public void updateDescription(String tenantDesc) {
        this.tenantDesc = tenantDesc;
    }

    /**
     * 更新允许注册
     */
    public void updateAllowRegister(YesNoEnums allowRegister) {
        this.allowRegister = allowRegister;
    }

    /**
     * 更新允许添加用户
     */
    public void updateAllowAdd(YesNoEnums allowAdd) {
        this.allowAdd = allowAdd;
    }

    // ----- 配置管理 -----

    /**
     * 更新密码策略
     */
    public void updatePasswordPolicy(PasswordPolicy passwordPolicy) {
        config.updatePasswordPolicy(passwordPolicy);
    }

    /**
     * 更新会话配置
     */
    public void updateSessionConfig(SessionConfig sessionConfig) {
        config.updateSessionConfig(sessionConfig);
    }

    /**
     * 更新MFA配置
     */
    public void updateMFAConfig(MFAConfig mfaConfig) {
        config.updateMFAConfig(mfaConfig);
    }

    /**
     * 更新登录配置
     */
    public void updateLoginConfig(LoginConfig loginConfig) {
        config.updateLoginConfig(loginConfig);
    }

    // ----- 品牌管理 -----

    /**
     * 添加品牌配置
     */
    public void addBrandConfig(BrandConfigEntity brandConfig) {
        brandConfigs.add(brandConfig);
    }

    /**
     * 设置品牌配置
     */
    public void setBrandConfig(BrandTargetTypeEnums targetType, String targetId, Branding branding) {
        // 如果已存在，更新；否则新增
        Optional<BrandConfigEntity> existing = brandConfigs.stream()
                .filter(b -> b.getTargetType() == targetType && b.getTargetId().equals(targetId))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().updateBranding(branding);
        } else {
            BrandConfigEntity brandConfig = BrandConfigEntity.create(targetType, targetId, branding, 0);
            brandConfigs.add(brandConfig);
        }
    }

    /**
     * 启用品牌配置
     */
    public void enableBrandConfig(BrandConfigId brandConfigId) {
        BrandConfigEntity brandConfig = brandConfigs.stream()
                .filter(b -> b.getId().equals(brandConfigId))
                .findFirst()
                .orElseThrow(() -> new DomainException("品牌配置不存在"));
        brandConfig.enable();
    }

    /**
     * 禁用品牌配置
     */
    public void disableBrandConfig(BrandConfigId brandConfigId) {
        BrandConfigEntity brandConfig = brandConfigs.stream()
                .filter(b -> b.getId().equals(brandConfigId))
                .findFirst()
                .orElseThrow(() -> new DomainException("品牌配置不存在"));
        brandConfig.disable();
    }

    // ----- 公司管理 -----

    /**
     * 添加公司
     */
    public void addCompany(CompanyInfoEntity company) {
        if (!company.getTenantId().equals(this.getId())) {
            throw new DomainException("公司不属于当前租户");
        }
        companies.add(company);
        registerEvent(new CompanyAddedToTenantEvent(this, company.getCompanyId(), company.getCompanyName()));
    }

    /**
     * 移除公司
     */
    public void removeCompany(CompanyId companyId) {
        companies.removeIf(c -> c.getCompanyId().equals(companyId));
        registerEvent(new CompanyRemovedFromTenantEvent(this.getId(), companyId));
    }

    /**
     * 更新公司信息
     */
    public void updateCompany(CompanyId companyId, CompanyInfoEntity updatedCompany) {
        CompanyInfoEntity existing = companies.stream()
                .filter(c -> c.getCompanyId().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new DomainException("公司不存在"));
        // 更新逻辑
        companies.remove(existing);
        companies.add(updatedCompany);
    }

    // ----- 实例管理 -----

    /**
     * 添加实例
     */
    public void addInstance(InstanceInfoEntity instance) {
        if (!instance.getTenantId().equals(this.getId())) {
            throw new DomainException("实例不属于当前租户");
        }
        instances.add(instance);
        registerEvent(new InstanceAddedToTenantEvent(this.getId(), instance.getInstanceId()));
    }

    /**
     * 移除实例
     */
    public void removeInstance(InstanceId instanceId) {
        boolean removed = instances.removeIf(i -> i.getInstanceId().equals(instanceId));
        if (!removed) {
            throw new DomainException("实例不存在");
        }
        registerEvent(new InstanceRemovedFromTenantEvent(this.getId(), instanceId));
    }

    /**
     * 启动实例
     */
    public void startInstance(InstanceId instanceId) {
        InstanceInfoEntity instance = instances.stream()
                .filter(i -> i.getInstanceId().equals(instanceId))
                .findFirst()
                .orElseThrow(() -> new DomainException("实例不存在"));
        instance.start();
    }

    /**
     * 停止实例
     */
    public void stopInstance(InstanceId instanceId) {
        InstanceInfoEntity instance = instances.stream()
                .filter(i -> i.getInstanceId().equals(instanceId))
                .findFirst()
                .orElseThrow(() -> new DomainException("实例不存在"));
        instance.stop();
    }

    // ----- 跨聚合关联管理 -----

    /**
     * 添加用户到租户
     */
    public void addUser(UserId userId) {
        if (userIds.contains(userId)) {
            throw new DomainException("用户已在该租户中");
        }
        userIds.add(userId);
        registerEvent(new UserAddedToTenantEvent(this.getId(), userId));
    }

    /**
     * 从租户移除用户
     */
    public void removeUser(UserId userId) {
        if (!userIds.contains(userId)) {
            throw new DomainException("用户不在该租户中");
        }
        userIds.remove(userId);
        registerEvent(new TenantRemoveUserEvent(this.getId(), userId));
    }

    /**
     * 添加组织到租户
     */
    public void addOrganization(OrganizationId organizationId) {
        if (organizationIds.contains(organizationId)) {
            throw new DomainException("组织已在该租户中");
        }
        organizationIds.add(organizationId);
    }

    /**
     * 添加角色到租户
     */
    public void addRole(RoleId roleId) {
        if (roleIds.contains(roleId)) {
            throw new DomainException("角色已在该租户中");
        }
        roleIds.add(roleId);
    }

    /**
     * 添加用户组到租户
     */
    public void addUserGroup(UserGroupId userGroupId) {
        if (userGroupIds.contains(userGroupId)) {
            throw new DomainException("用户组已在该租户中");
        }
        userGroupIds.add(userGroupId);
    }

    // ----- 查询方法 -----

    /**
     * 判断租户是否激活
     */
    public boolean isActive() {
        return this.tenantStatus == TenantStatusEnums.ACTIVATE;
    }

    /**
     * 判断租户是否可用
     */
    public boolean isAvailable() {
        return tenantStatus == TenantStatusEnums.ACTIVATE || tenantStatus == TenantStatusEnums.INACTIVE;
    }

    /**
     * 获取当前生效的品牌配置
     */
    public Optional<BrandConfigEntity> getActiveBrandConfig() {
        return brandConfigs.stream()
                .filter(BrandConfigEntity::isEnabled)
                .findFirst();
    }

    /**
     * 获取公司数量
     */
    public int getCompanyCount() {
        return companies.size();
    }

    /**
     * 获取用户数量
     */
    public int getUserCount() {
        return userIds.size();
    }
}
