package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Password;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import lombok.Getter;

/**
 * 用户名密码认证方式
 * @author: Jin-LiangBo
 * @date: 2026年03月30日 19:07
 */
@Getter
public class PasswordAuthCredential extends AbstractAuthCredential {

    /**
     * 用户名
     */
    private final Username username;
    /**
     * 密码
     */
    private final Password password;

    public PasswordAuthCredential(Username username, Password password) {
        this.grantType = GrantTypeEnums.PASSWORD;
        this.username = username;
        this.password = password;
    }

    // @Override
    // public void validate() {
    //     if (Objects.isNull(username) || Objects.isNull(password)) {
    //         throw new IllegalArgumentException("用户名和密码不能为空");
    //     }
    // }
}
