package com.yaocode.sts.auth.interfaces.model.params.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 移动端登录认证凭证
 * @author: Jin-LiangBo
 * @date: 2026年04月01日 11:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MobileLoginCredential extends AbstractLoginCredential {

    @NotBlank(message = "手机号不能为空")
    private String phoneNum;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
