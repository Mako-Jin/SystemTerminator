package com.yaocode.sts.auth.application.dto;

import java.util.List;

import lombok.Data;

@Data
public class TenantUserGroupDto {

    private String tenantId;

    private List<String> userGroupIdList;

}
