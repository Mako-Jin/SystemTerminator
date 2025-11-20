package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务资源模型
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerResourcesModel extends ResourcesModel {

    public ServerResourcesModel() {
        this.setType(ResourceTypeEnums.SERVER);
    }

    private List<ServiceResourcesModel> serviceResourcesModelList = new ArrayList<>();

    private String menuIcon;

    private boolean isEnable;

    public void addServiceResources(ServiceResourcesModel serviceResourcesModel) {
        serviceResourcesModelList.add(serviceResourcesModel);
    }

}
