package com.yaocode.sts.common.resources.model;

import com.yaocode.sts.common.resources.enums.ResourceTypeEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    private List<String> path;

    private Integer isWhiteList;

    private List<String> parentCode;

    private List<RequestMethod> requestMethod;

}
