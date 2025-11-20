package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;

/**
 * 资源模型
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:52
 */
@Data
public class ResourcesModel {

    private String code;
    private String name;
    private String desc;
    private String version;
    private ResourceTypeEnums type;

}
