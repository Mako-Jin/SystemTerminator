package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统资源模型
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemResourcesModel extends ResourcesModel {

    public SystemResourcesModel() {
        setType(ResourceTypeEnums.SYSTEM);
    }

    private String menuIcon;

    private List<ServerResourcesModel> serverResourcesModelList = new ArrayList<>();

    public void addServerResources(ServerResourcesModel serverResourcesModel) {
        serverResourcesModelList.add(serverResourcesModel);
    }

}
