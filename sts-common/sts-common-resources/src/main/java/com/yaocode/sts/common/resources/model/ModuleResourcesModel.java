package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 模块资源
 * @author: Jin-LiangBo
 * @date: 2025年11月21日 20:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ModuleResourcesModel extends ResourcesModel {

    public ModuleResourcesModel() {
        this.setType(ResourceTypeEnums.MODULE);
    }

    private List<String> path;

    private String icon;

    private Integer isWhiteList;

    private List<String> parentCode;

}
