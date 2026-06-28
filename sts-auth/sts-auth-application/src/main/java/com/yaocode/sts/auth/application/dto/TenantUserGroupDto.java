package com.yaocode.sts.auth.application.dto;

import lombok.Data;

import java.util.List;

@Data
public class TenantUserGroupDto {

    private String tenantId;

    private List<String> userGroupIdList;

}
