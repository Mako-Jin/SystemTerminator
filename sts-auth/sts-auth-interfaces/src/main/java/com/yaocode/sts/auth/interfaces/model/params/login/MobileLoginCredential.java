package com.yaocode.sts.auth.interfaces.model.params.login;

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

    private String phoneNum;

    private String verifyCode;
}
