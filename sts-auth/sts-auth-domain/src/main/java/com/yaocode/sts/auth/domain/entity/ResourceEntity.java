package com.yaocode.sts.auth.domain.entity;

import com.yaocode.sts.auth.domain.valueobjects.identifiers.ResourceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.ResourceValue;
import com.yaocode.sts.common.domain.model.AbstractAggregate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年11月13日 23:09
 */
@Getter
@Setter
public class ResourceEntity extends AbstractAggregate<ResourceId> {

    public ResourceEntity(ResourceId resourceId) {
        super(resourceId);
    }

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源值
     */
    private ResourceValue resourceValue;

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
    private List<String> requestUrl;

    /**
     * 请求方法，大写：POST,GET,PUT
     */
    private List<String> requestMethod;

    /**
     * 是否已弃用；0：未；1：已
     */
    private Integer isDeprecated;

    /**
     * 是否启用；0：未；1：已
     */
    private Integer isEnabled;

    /**
     * 是否白名单；0：不是；1：是
     */
    private Integer isWhiteList;

    /**
     * 菜单显示图标
     */
    private String icon;

    /**
     * 版本
     */
    private String version;

    /**
     * 父资源编码列表，逗号分割
     */
    private List<String> parentCode;

}
