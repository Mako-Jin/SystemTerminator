package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class TenantOrganizationDto {

    private String tenantId;

    private List<String> organizationIdList;

}
