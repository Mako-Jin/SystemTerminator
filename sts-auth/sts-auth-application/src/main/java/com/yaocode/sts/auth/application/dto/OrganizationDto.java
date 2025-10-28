package com.yaocode.sts.auth.application.dto;

import lombok.Data;

/**
 *
 * @author: Jin-LiangBo
 * @date: 2025年10月25日 17:25
 */
@Data
public class OrganizationDto {

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
