package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口资源数据类
 * @author: Jin-LiangBo
 * @date: 2025年11月26日 22:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApiResourcesModel extends ResourcesModel {

    public ApiResourcesModel() {
        this.setType(ResourceTypeEnums.API);
    }

    private String path;

    private boolean isEnabled;

    private boolean isDeprecated;

    private boolean isWhiteList;

    private String requestMethod;

}
