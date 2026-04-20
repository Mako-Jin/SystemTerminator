package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.common.domain.valueobject.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserGroupId;
import com.yaocode.sts.common.domain.valueobject.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:55
 */
@Getter
public class UserInfoEntity extends AbstractAggregate<UserId> {

    private UserInfoEntity(UserId userId) {
        super(userId);
    }

    /**
     * 用户名
     */
    private Username username;
    /**
     * 租户id
     */
    private List<TenantId> tenantIdList;
    /**
     * 组织id
     */
    private List<OrganizationId> organizationIdList;
    /**
     * 角色id
     */
    private List<RoleId> roleIdList;
    /**
     * 用户组id
     */
    private List<UserGroupId> userGroupIdList;
    /**
     * 邮箱
     */
    private Email email;
    /**
     * 手机
     */
    private PhoneNum phoneNum;
    /**
     * 是否激活：0：没有；1：有
     */
    private Integer isEnabled;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    public static UserInfoEntity build(
            UserId userId,
            Username username,
            List<TenantId> tenantIdList,
            List<OrganizationId> organizationIdList,
            List<RoleId> roleIdList,
            List<UserGroupId> userGroupIdList,
            Email email,
            PhoneNum phoneNum,
            Integer isEnabled
    ) {
        UserInfoEntity entity = new UserInfoEntity(userId);
        entity.username = username;
        entity.tenantIdList = tenantIdList;
        entity.organizationIdList = organizationIdList;
        entity.roleIdList = roleIdList;
        entity.userGroupIdList = userGroupIdList;
        entity.email = email;
        entity.phoneNum = phoneNum;
        entity.isEnabled = isEnabled;
        return entity;
    }

    public static UserInfoEntity build(
            Username username,
            List<TenantId> tenantIdList,
            List<OrganizationId> organizationIdList,
            List<RoleId> roleIdList,
            List<UserGroupId> userGroupIdList,
            Email email,
            PhoneNum phoneNum,
            Integer isEnabled
    ) {
        UserInfoEntity entity = new UserInfoEntity(UserId.nextId());
        entity.username = username;
        entity.tenantIdList = tenantIdList;
        entity.organizationIdList = organizationIdList;
        entity.roleIdList = roleIdList;
        entity.userGroupIdList = userGroupIdList;
        entity.email = email;
        entity.phoneNum = phoneNum;
        entity.isEnabled = isEnabled;
        return entity;
    }

    // public void changePassword(String newPassword, PasswordEncoder encoder) {
    //     this.password = encoder.encode(newPassword);
    // }
    //
    // public void changePhoneNum(String newPassword, PasswordEncoder encoder) {
    //     this.password = encoder.encode(newPassword);
    // }

    // public void changeEmail(String newEmail) {
    //     this.email = newEmail;
    //     registerEvent(new UserEmailChangedEvent(this.id, newEmail));
    // }
    //
    // public void deactivate() {
    //     this.status = UserStatus.INACTIVE;
    // }

    // public void activate() {
    //     this.status = UserStatus.ACTIVE;
    //     registerEvent(new UserActivatedEvent(this.id));
    // }

}
