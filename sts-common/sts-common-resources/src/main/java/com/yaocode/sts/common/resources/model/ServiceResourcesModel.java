package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 服务资源模型
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceResourcesModel extends ResourcesModel {

    public ServiceResourcesModel() {
        setType(ResourceTypeEnums.SERVICE);
    }

    private String menuIcon;

    private boolean isEnable;

    private String path;

}
