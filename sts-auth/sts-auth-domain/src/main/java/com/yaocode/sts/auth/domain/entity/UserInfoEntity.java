package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.enums.MFATypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Email;
import com.yaocode.sts.auth.domain.valueobjects.primitives.PhoneNum;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import com.yaocode.sts.common.domain.valueobject.UserId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 用户信息实体类
 * @author: Jin-LiangBo
 * @date: 2025年10月07日 21:55
 */
@Getter
@Builder
public class UserInfoEntity extends AbstractAggregate<UserId> {

    private UserId userId;
    /**
     * 联合ID（钉钉风格，跨企业唯一标识）
     * 一个用户在多个企业中使用同一个unionId
     */
    private String unionId;
    /**
     * 用户名
     */
    private Username username;
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
    private OppositeEnums isEnabled;
    /**
     * 是否已绑定MFA
     */
    private OppositeEnums mfaBound;

    /**
     * MFA类型：TOTP, SMS, EMAIL
     */
    private MFATypeEnums mfaType;
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
