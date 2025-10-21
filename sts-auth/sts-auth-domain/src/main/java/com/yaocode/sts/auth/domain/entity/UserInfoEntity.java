package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.OrganizationId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.RoleId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TenantId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.UserId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:55
 */
@Getter
@Setter
public class UserInfoEntity extends AbstractAggregate<UserId> {

    public UserInfoEntity(UserId userId) {
        super(userId);
    }

    /**
     * 用户名
     */
    private Username username;
    /**
     * 密码
     */
    private Password password;
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
    private List<RoleId> userGroupIdList;
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

    // public void changePassword(String newPassword, PasswordEncoder encoder) {
    //     this.password = encoder.encode(newPassword);
    // }
    //
    // public void changePhoneNum(String newPassword, PasswordEncoder encoder) {
    //     this.password = encoder.encode(newPassword);
    // }
    //
    // public void changeEmail(String newEmail) {
    //     this.email = newEmail;
    //     registerEvent(new UserEmailChangedEvent(this.id, newEmail));
    // }
    //
    // public void deactivate() {
    //     this.status = UserStatus.INACTIVE;
    // }
    //
    // public void activate() {
    //     this.status = UserStatus.ACTIVE;
    //     registerEvent(new UserActivatedEvent(this.id));
    // }

}
