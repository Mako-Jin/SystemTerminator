package com.yaocode.sts.auth.interfaces.model.params.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 密码认证凭证
 * @author: Jin-LiangBo
 * @date: 2026年04月01日 11:10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PasswordLoginCredential extends AbstractLoginCredential {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
