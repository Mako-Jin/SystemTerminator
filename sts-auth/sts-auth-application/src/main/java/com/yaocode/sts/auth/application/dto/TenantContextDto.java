package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.util.List;


@Data
public class TenantContextDto {

    private String tenantId;
    private String tenantCode;
    private String tenantName;
    private List<String> organizationIdList;
    private List<String> roleIdList;
    private List<String> userGroupIdList;

}
