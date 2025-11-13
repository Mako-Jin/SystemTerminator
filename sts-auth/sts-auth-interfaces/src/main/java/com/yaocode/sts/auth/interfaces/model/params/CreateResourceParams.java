package com.yaocode.sts.auth.interfaces.model.params;

import com.yaocode.sts.common.web.annotation.CheckSqlInjection;
import com.yaocode.sts.common.web.annotation.CheckXss;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    @NotBlank(message = "资源名称不能为空")
    private String resourceName;

    /**
     * 资源值
     */
    @CheckXss
    @CheckSqlInjection
    @NotBlank(message = "资源编码不能为空")
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
    @NotNull(message = "资源类型不能为空")
    private Integer resourceType;

    /**
     * 接口请求地址
     */
    @CheckXss
    @CheckSqlInjection
    private String requestUrl;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
    @CheckXss
    @CheckSqlInjection
    private String requestMethod;

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
    private String menuIcon;

}
