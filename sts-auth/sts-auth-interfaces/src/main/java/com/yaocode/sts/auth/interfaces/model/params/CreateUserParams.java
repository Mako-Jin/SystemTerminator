package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 新增用户参数
 * @author: Jin-LiangBo
 * @date: 2025年10月12日 13:57
 */
@Data
public class CreateUserParams {

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "租户标识不能为空")
    private String tenantId;

    @CheckXss
    @CheckSqlInjection
    private String orgId;

    @CheckXss
    @CheckSqlInjection
    private String userGroupId;

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "用户名不能为空")
    private String username;

    @CheckXss
    @CheckSqlInjection
    private String email;

    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "电话号码不能为空")
    private String phoneNum;

    @CheckXss
    @CheckSqlInjection
    private String nickname;
}
