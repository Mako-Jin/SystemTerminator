package com.yaocode.sts.auth.application.dto;

import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 22:52
 */
@Data
public class ResourceDto {

    /**
     * 资源id
     */
    private String resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源值
     */
    private String resourceValue;

    /**
     * 资源描述
     */
    private String resourceDesc;

    /**
     * 资源类型：0：系统；1：服务；2：模块；3：页面；4：接口
     */
    private Integer resourceType;

    /**
     * 接口请求地址
     */
    private String requestUrl;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
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
