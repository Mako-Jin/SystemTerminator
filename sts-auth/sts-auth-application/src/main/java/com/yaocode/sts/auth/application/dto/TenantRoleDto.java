package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class TenantRoleDto {

    private String tenantId;

    private List<String> roleIdList;
}
