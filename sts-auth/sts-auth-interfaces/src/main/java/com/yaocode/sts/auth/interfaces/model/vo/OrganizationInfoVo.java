package com.yaocode.sts.auth.interfaces.model.vo;

import lombok.Data;

/**
 * 组织结构视图层模型
 * @author: Jin-LiangBo
 * @date: 2026年01月06日 18:38
 */
@Data
public class OrganizationInfoVo {

    /**
     * 组织机构id
     */
    private String organizationId;
    /**
     * 组织机构名称
     */
    private String organizationName;
    /**
     * 组织机构编码
     */
    private String organizationCode;
    /**
     * 组织机构描述
     */
    private String organizationDesc;
    /**
     * 排序
     */
    private String sort;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 租户id
     */
    private String tenantId;

}
