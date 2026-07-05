package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.auth.interfaces.constants.AuthApiI18nKeyConstants;
import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:30
 */
@Data
public class CreateResourceParams {

    /**
     * 资源名称
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_RESOURCE_NAME_CANNOT_BE_BLANK)
    private String resourceName;

    /**
     * 资源值
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = AuthApiI18nKeyConstants.AUTH_RESOURCE_VALUE_CANNOT_BE_BLANK)
    private String resourceValue;

    /**
     * 资源描述
     */
    @CheckXss
    @CheckSqlInjection
    private String resourceDesc;

    /**
     * 资源类型：0：系统；1：服务；2：模块；3：页面；4：接口
     */
    @NotNull(message = AuthApiI18nKeyConstants.AUTH_PARAMS_VALIDATION_RESOURCE_TYPE_REQUIRED)
    private Integer resourceType;

    /**
     * 接口请求地址
     */
    @CheckXss
    @CheckSqlInjection
    private List<String> requestUrl;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
    @CheckXss
    @CheckSqlInjection
    private List<String> requestMethod;

    /**
     * 是否已弃用；0：未；1：已
     */
    private Integer isDeprecated;

    /**
     * 是否白名单；0：不是；1：是
     */
    private Integer isWhiteList;

    /**
     * 菜单显示图标
     */
    private String icon;

}
