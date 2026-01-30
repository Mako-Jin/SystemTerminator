package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;

import java.util.List;

/**
 * 资源模型
 * @author: Jin-LiangBo
 * @date: 2025年11月19日 20:52
 */
@Data
public class ResourcesModel {

    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源描述
     */
    private String desc;
    /**
     * 资源版本
     */
    private String version;
    /**
     * 是否启用
     */
    private Integer isEnabled;
    /**
     * 是否过期
     */
    private Integer isDeprecated;
    /**
     * 资源类别
     */
    private ResourceTypeEnums type;
    /**
     * 作者信息
     */
    private List<ContactInfoModel> contactInfoModelList;

}
