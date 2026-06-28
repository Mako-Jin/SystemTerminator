package com.yaocode.sts.auth.domain.aggregate;

import com.yaocode.sts.auth.domain.entity.LoginAttemptEntity;
import com.yaocode.sts.auth.domain.entity.PasswordHistoryEntity;
import com.yaocode.sts.auth.domain.entity.UserContactEntity;
import com.yaocode.sts.auth.domain.entity.UserCredentialEntity;
import com.yaocode.sts.auth.domain.entity.UserEducationEntity;
import com.yaocode.sts.auth.domain.entity.UserEmergencyContactEntity;
import com.yaocode.sts.auth.domain.entity.UserEmploymentEntity;
import com.yaocode.sts.auth.domain.entity.UserProfileEntity;
import com.yaocode.sts.auth.domain.entity.UserSecretQuestionEntity;
import com.yaocode.sts.auth.domain.enums.ContactTypeEnums;
import com.yaocode.sts.auth.domain.enums.GenderEnums;
import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import com.yaocode.sts.auth.domain.enums.MaritalStatusEnums;
import com.yaocode.sts.auth.domain.enums.RegisterSourceEnums;
import com.yaocode.sts.auth.domain.enums.ThemeEnums;
import com.yaocode.sts.auth.domain.enums.UserStatusEnums;
import com.yaocode.sts.auth.domain.events.user.MFABoundEvent;
import com.yaocode.sts.auth.domain.events.user.MFAUnboundEvent;
import com.yaocode.sts.auth.domain.events.user.UserActivatedEvent;
import com.yaocode.sts.auth.domain.events.user.UserAssignedRoleEvent;
import com.yaocode.sts.auth.domain.events.user.UserAssignedToTenantEvent;
import com.yaocode.sts.auth.domain.events.user.UserContactAddedEvent;
import com.yaocode.sts.auth.domain.events.user.UserContactRemovedEvent;
import com.yaocode.sts.auth.domain.events.user.UserDeactivatedEvent;
import com.yaocode.sts.auth.domain.events.user.UserLockedEvent;
import com.yaocode.sts.auth.domain.events.user.UserRegisteredEvent;
import com.yaocode.sts.auth.domain.events.user.UserRemoveTenantEvent;
import com.yaocode.sts.auth.domain.events.user.UserRemovedRoleEvent;
import com.yaocode.sts.auth.domain.events.user.UserUnlockedEvent;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ContactId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.CredentialId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EducationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EmergencyContactId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.EmploymentId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.exception.DomainException;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * 用户聚合根
 * 管理用户的核心信息、档案、联系方式、证件、教育/工作经历等
 * 对应表：auth_tbl_user_info
 */
@Getter
public class UserInfoAggregate extends AbstractAggregate<UserId> {

    // ============ 核心属性（来自 UserInfoPo） ============
    private Username username;
    private UserStatusEnums status;
    private OppositeEnums onlineStatus;
    private LocalDateTime lockTime;
    private LocalDateTime unlockTime;
    private OppositeEnums mfaBound;
    private MFATypeEnums mfaType;
    private RegisterSourceEnums registerSource;

//    private Email email;
//    private PhoneNumber phone;
//    private Password password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;

    // ============ 子实体（1:1） ============
    private UserProfileEntity profile;

    // ============ 子实体（1:N） ============
    private List<UserContactEntity> contacts = new ArrayList<>();
    private List<UserCredentialEntity> credentials = new ArrayList<>();
    private List<UserEducationEntity> educations = new ArrayList<>();
    private List<UserEmploymentEntity> employments = new ArrayList<>();
    private List<UserEmergencyContactEntity> emergencyContacts = new ArrayList<>();
    private List<UserSecretQuestionEntity> secretQuestions = new ArrayList<>();
    private List<PasswordHistoryEntity> passwordHistories = new ArrayList<>();

    private List<LoginAttemptEntity> loginAttemptInfoEntityList = new ArrayList<>();

    // ============ 跨聚合引用（通过ID） ============
    private Set<TenantId> tenantIds = new HashSet<>();
    private Set<OrganizationId> organizationIds = new HashSet<>();
    private Set<RoleId> roleIds = new HashSet<>();
    private Set<UserGroupId> userGroupIds = new HashSet<>();
    private Set<DeviceId> deviceIds = new HashSet<>();

    // ============ 构造函数 ============
    private UserInfoAggregate(UserId userId) {
        super(userId);
        this.status = UserStatusEnums.INACTIVE;
        this.onlineStatus = OppositeEnums.NO;
        this.mfaBound = OppositeEnums.NO;
        this.createdAt = LocalDateTime.now();
        this.registerSource = RegisterSourceEnums.REGISTER;
    }

    // ============ 工厂方法 ============

    /**
     * 创建新用户（注册）
     */
    public static UserInfoAggregate register(
            Username username,
            String displayName,
            Email email,
            PhoneNum phoneNum,
            RegisterSourceEnums registerSource
    ) {
        UserInfoAggregate user = new UserInfoAggregate(UserId.nextId());
        user.username = username;
        user.registerSource = registerSource != null ? registerSource : RegisterSourceEnums.REGISTER;

        // 创建档案
        user.profile = UserProfileEntity.create(
                user.getId(),
                displayName != null ? displayName : username.getValue(),
                null,
                GenderEnums.UNKNOWN,
                null,
                MaritalStatusEnums.UNKNOWN,
                null,
                "zh-CN",
                "Asia/Shanghai",
                ThemeEnums.LIGHT,
                null
        );

        // 添加联系方式
        if (email != null) {
            user.contacts.add(UserContactEntity.createEmail(user.getId(), email));
        }
        if (phoneNum != null) {
            user.contacts.add(UserContactEntity.createMobile(user.getId(), phoneNum));
        }

        // 发布领域事件
        user.registerEvent(new UserRegisteredEvent(user, username));

        return user;
    }

    /**
     * 从数据库重建用户聚合
     */
    public static UserInfoAggregate reconstruct(
            UserId userId,
            Username username,
            UserStatusEnums status,
            OppositeEnums onlineStatus,
            LocalDateTime lockTime,
            LocalDateTime unlockTime,
            OppositeEnums mfaBound,
            MFATypeEnums mfaType,
            RegisterSourceEnums registerSource,
            LocalDateTime registeredAt,
            UserProfileEntity profile,
            List<UserContactEntity> contacts,
            List<UserCredentialEntity> credentials,
            List<UserEducationEntity> educations,
            List<UserEmploymentEntity> employments,
            List<UserEmergencyContactEntity> emergencyContacts,
            List<UserSecretQuestionEntity> secretQuestions,
            List<PasswordHistoryEntity> passwordHistories,
            Set<TenantId> tenantIds,
            Set<OrganizationId> organizationIds,
            Set<RoleId> roleIds,
            Set<UserGroupId> userGroupIds,
            Set<DeviceId> deviceIds
    ) {
        UserInfoAggregate user = new UserInfoAggregate(userId);
        user.username = username;
        user.status = status != null ? status : UserStatusEnums.INACTIVE;
        user.onlineStatus = onlineStatus != null ? onlineStatus : OppositeEnums.NO;
        user.lockTime = lockTime;
        user.unlockTime = unlockTime;
        user.mfaBound = mfaBound != null ? mfaBound : OppositeEnums.NO;
        user.mfaType = mfaType;
        user.registerSource = registerSource != null ? registerSource : RegisterSourceEnums.REGISTER;
        user.createdAt = registeredAt != null ? registeredAt : LocalDateTime.now();
        user.profile = profile;
        user.contacts = contacts != null ? new ArrayList<>(contacts) : new ArrayList<>();
        user.credentials = credentials != null ? new ArrayList<>(credentials) : new ArrayList<>();
        user.educations = educations != null ? new ArrayList<>(educations) : new ArrayList<>();
        user.employments = employments != null ? new ArrayList<>(employments) : new ArrayList<>();
        user.emergencyContacts = emergencyContacts != null ? new ArrayList<>(emergencyContacts) : new ArrayList<>();
        user.secretQuestions = secretQuestions != null ? new ArrayList<>(secretQuestions) : new ArrayList<>();
        user.passwordHistories = passwordHistories != null ? new ArrayList<>(passwordHistories) : new ArrayList<>();
        user.tenantIds = tenantIds != null ? new HashSet<>(tenantIds) : new HashSet<>();
        user.organizationIds = organizationIds != null ? new HashSet<>(organizationIds) : new HashSet<>();
        user.roleIds = roleIds != null ? new HashSet<>(roleIds) : new HashSet<>();
        user.userGroupIds = userGroupIds != null ? new HashSet<>(userGroupIds) : new HashSet<>();
        user.deviceIds = deviceIds != null ? new HashSet<>(deviceIds) : new HashSet<>();
        return user;
    }

    // ============ 业务行为 ============

    // ----- 状态管理 -----

    /**
     * 激活用户
     */
    public void activate() {
        if (this.status == UserStatusEnums.ACTIVE) {
            throw new DomainException("用户已激活");
        }
        this.status = UserStatusEnums.ACTIVE;
        registerEvent(new UserActivatedEvent(this.getId()));
    }

    /**
     * 停用用户
     */
    public void deactivate() {
        if (this.status == UserStatusEnums.INACTIVE) {
            throw new DomainException("用户已停用");
        }
        this.status = UserStatusEnums.INACTIVE;
        registerEvent(new UserDeactivatedEvent(this.getId()));
    }

    /**
     * 锁定用户
     */
    public void lock(String reason) {
        if (this.status == UserStatusEnums.LOCKED) {
            throw new DomainException("用户已锁定");
        }
        this.status = UserStatusEnums.LOCKED;
        this.lockTime = LocalDateTime.now();
        registerEvent(new UserLockedEvent(this.getId(), reason));
    }

    /**
     * 解锁用户
     */
    public void unlock() {
        if (this.status != UserStatusEnums.LOCKED) {
            throw new DomainException("用户未锁定");
        }
        this.status = UserStatusEnums.ACTIVE;
        this.unlockTime = LocalDateTime.now();
        registerEvent(new UserUnlockedEvent(this.getId()));
    }

    /**
     * 更新在线状态
     */
    public void updateOnlineStatus(OppositeEnums onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    // ----- 档案管理 -----

    /**
     * 更新显示名称
     */
    public void updateDisplayName(String displayName) {
        if (profile == null) {
            profile = UserProfileEntity.create(this.getId());
        }
        profile.updateDisplayName(displayName);
    }

    /**
     * 更新真实姓名
     */
    public void updateRealName(String realName) {
        if (profile == null) {
            profile = UserProfileEntity.create(this.getId());
        }
        profile.updateRealName(realName);
    }

    /**
     * 更新头像
     */
    public void updateAvatar(String avatarUrl) {
        if (profile == null) {
            profile = UserProfileEntity.create(this.getId());
        }
        profile.updateAvatar(avatarUrl);
    }

    /**
     * 更新性别
     */
    public void updateGender(GenderEnums gender) {
        if (profile == null) {
            profile = UserProfileEntity.create(this.getId());
        }
        profile.updateGender(gender);
    }

    // ----- 联系方式管理 -----

    /**
     * 添加联系方式
     */
    public void addContact(UserContactEntity contact) {
        if (!contact.getUserId().equals(this.getId())) {
            throw new DomainException("联系方式不属于当前用户");
        }
        // 如果是主联系方式，将其他主联系方式降级
        if (contact.isPrimaryContact()) {
            contacts.forEach(c -> {
                if (c.isPrimaryContact()) {
                    c.unmarkPrimary();
                }
            });
        }
        contacts.add(contact);
        registerEvent(new UserContactAddedEvent(this, contact.getContactId(), contact.getContactType()));
    }

    /**
     * 删除联系方式
     */
    public void removeContact(ContactId contactId) {
        UserContactEntity contact = contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new DomainException("联系方式不存在"));
        contacts.remove(contact);
        registerEvent(new UserContactRemovedEvent(this.getId(), contactId));
    }

    /**
     * 验证联系方式
     */
    public void verifyContact(ContactId contactId) {
        UserContactEntity contact = contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new DomainException("联系方式不存在"));
        contact.verify();
    }

    /**
     * 设置主联系方式
     */
    public void setPrimaryContact(ContactId contactId) {
        contacts.forEach(UserContactEntity::unmarkPrimary);
        UserContactEntity contact = contacts.stream()
                .filter(c -> c.getContactId().equals(contactId))
                .findFirst()
                .orElseThrow(() -> new DomainException("联系方式不存在"));
        contact.markPrimary();
    }

    // ----- 证件管理 -----

    /**
     * 添加证件
     */
    public void addCredential(UserCredentialEntity credential) {
        if (!credential.getUserId().equals(this.getId())) {
            throw new DomainException("证件不属于当前用户");
        }
        if (credential.isPrimaryCredential()) {
            credentials.forEach(UserCredentialEntity::unmarkPrimary);
        }
        credentials.add(credential);
    }

    /**
     * 删除证件
     */
    public void removeCredential(CredentialId credentialId) {
        credentials.removeIf(c -> c.getCredentialId().equals(credentialId));
    }

    // ----- 工作经历管理 -----

    /**
     * 添加工经历
     */
    public void addEmployment(UserEmploymentEntity employment) {
        if (!employment.getUserId().equals(this.getId())) {
            throw new DomainException("工作经历不属于当前用户");
        }
        employments.add(employment);
    }

    /**
     * 更新工作经历
     */
    public void updateEmployment(EmploymentId employmentId, String jobTitle, String jobLevel) {
        UserEmploymentEntity employment = employments.stream()
                .filter(e -> e.getEmploymentId().equals(employmentId))
                .findFirst()
                .orElseThrow(() -> new DomainException("工作经历不存在"));
        employment.updatePosition(jobTitle, jobLevel);
    }

    /**
     * 离职
     */
    public void resign(EmploymentId employmentId, LocalDate quitDate) {
        UserEmploymentEntity employment = employments.stream()
                .filter(e -> e.getEmploymentId().equals(employmentId))
                .findFirst()
                .orElseThrow(() -> new DomainException("工作经历不存在"));
        employment.resign(quitDate);
    }

    // ----- 教育经历管理 -----

    /**
     * 添加教育经历
     */
    public void addEducation(UserEducationEntity education) {
        if (!education.getUserId().equals(this.getId())) {
            throw new DomainException("教育经历不属于当前用户");
        }
        educations.add(education);
    }

    /**
     * 删除教育经历
     */
    public void removeEducation(EducationId educationId) {
        educations.removeIf(e -> e.getEducationId().equals(educationId));
    }

    // ----- 紧急联系人管理 -----

    /**
     * 添加紧急联系人
     */
    public void addEmergencyContact(UserEmergencyContactEntity emergencyContact) {
        if (!emergencyContact.getUserId().equals(this.getId())) {
            throw new DomainException("紧急联系人不属于当前用户");
        }
        emergencyContacts.add(emergencyContact);
    }

    /**
     * 删除紧急联系人
     */
    public void removeEmergencyContact(EmergencyContactId emergencyContactId) {
        emergencyContacts.removeIf(e -> e.getEmergencyContactId().equals(emergencyContactId));
    }

    // ----- MFA管理 -----

    /**
     * 绑定MFA
     */
    public void bindMFA(MFATypeEnums mfaType) {
        this.mfaBound = OppositeEnums.YES;
        this.mfaType = mfaType;
        registerEvent(new MFABoundEvent(this.getId(), mfaType));
    }

    /**
     * 解绑MFA
     */
    public void unbindMFA() {
        this.mfaBound = OppositeEnums.NO;
        this.mfaType = null;
        registerEvent(new MFAUnboundEvent(this.getId()));
    }

    // ----- 跨聚合关联管理 -----

    /**
     * 添加租户关联
     */
    public void assignToTenant(TenantId tenantId) {
        if (tenantIds.contains(tenantId)) {
            throw new DomainException("用户已关联该租户");
        }
        tenantIds.add(tenantId);
        registerEvent(new UserAssignedToTenantEvent(this.getId(), tenantId));
    }

    /**
     * 移除租户关联
     */
    public void removeFromTenant(TenantId tenantId) {
        if (!tenantIds.contains(tenantId)) {
            throw new DomainException("用户未关联该租户");
        }
        tenantIds.remove(tenantId);
        registerEvent(new UserRemoveTenantEvent(this.getId(), tenantId));
    }

    /**
     * 添加组织关联
     */
    public void assignToOrganization(OrganizationId organizationId) {
        if (organizationIds.contains(organizationId)) {
            throw new DomainException("用户已关联该组织");
        }
        organizationIds.add(organizationId);
    }

    /**
     * 移除组织关联
     */
    public void removeFromOrganization(OrganizationId organizationId) {
        organizationIds.remove(organizationId);
    }

    /**
     * 添加角色关联
     */
    public void assignRole(RoleId roleId) {
        if (roleIds.contains(roleId)) {
            throw new DomainException("用户已有该角色");
        }
        roleIds.add(roleId);
        registerEvent(new UserAssignedRoleEvent(this.getId(), roleId));
    }

    /**
     * 移除角色关联
     */
    public void removeRole(RoleId roleId) {
        if (!roleIds.contains(roleId)) {
            throw new DomainException("用户没有该角色");
        }
        roleIds.remove(roleId);
        registerEvent(new UserRemovedRoleEvent(this.getId(), roleId));
    }

    /**
     * 添加用户组关联
     */
    public void assignToUserGroup(UserGroupId userGroupId) {
        if (userGroupIds.contains(userGroupId)) {
            throw new DomainException("用户已在该用户组");
        }
        userGroupIds.add(userGroupId);
    }

    /**
     * 移除用户组关联
     */
    public void removeFromUserGroup(UserGroupId userGroupId) {
        userGroupIds.remove(userGroupId);
    }

    /**
     * 关联设备
     */
    public void associateDevice(DeviceId deviceId) {
        if (deviceIds.contains(deviceId)) {
            throw new DomainException("用户已关联该设备");
        }
        deviceIds.add(deviceId);
    }

    /**
     * 解除设备关联
     */
    public void disassociateDevice(DeviceId deviceId) {
        if (!deviceIds.contains(deviceId)) {
            throw new DomainException("用户未关联该设备");
        }
        deviceIds.remove(deviceId);
    }

    // ----- 查询方法 -----

    /**
     * 判断用户是否可以登录
     */
    public boolean canLogin() {
        return status.canLogin();
    }

    /**
     * 判断用户是否激活
     */
    public boolean isActive() {
        return status.isActive();
    }

    /**
     * 判断用户是否锁定
     */
    public boolean isLocked() {
        return status.isLocked();
    }

    /**
     * 获取主要联系方式（手机或邮箱）
     */
    public Optional<UserContactEntity> getPrimaryContact() {
        return contacts.stream().filter(UserContactEntity::isPrimaryContact).findFirst();
    }

    /**
     * 获取已验证的联系方式
     */
    public List<UserContactEntity> getVerifiedContacts() {
        return contacts.stream().filter(UserContactEntity::isVerified).toList();
    }

    /**
     * 判断用户是否有租户
     */
    public boolean hasTenant() {
        return !tenantIds.isEmpty();
    }

    /**
     * 获取用户显示名称
     */
    public String getDisplayName() {
        return profile != null && profile.getDisplayName() != null
                ? profile.getDisplayName()
                : username.getValue();
    }

    /**
     * 获取用户真实姓名
     */
    public String getRealName() {
        return profile != null ? profile.getRealName() : null;
    }

    /**
     * 获取用户邮箱（从联系方式中查找）
     */
    public Optional<Email> getEmail() {
        return contacts.stream()
                .filter(c -> c.getContactType() == ContactTypeEnums.EMAIL)
                .map(c -> Email.of(c.getContactValue()))
                .findFirst();
    }

    /**
     * 获取用户手机号（从联系方式中查找）
     */
    public Optional<PhoneNum> getPhoneNum() {
        return contacts.stream()
                .filter(c -> c.getContactType() == ContactTypeEnums.MOBILE)
                .map(c -> PhoneNum.of(c.getContactValue()))
                .findFirst();
    }
}
