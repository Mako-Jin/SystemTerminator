package com.yaocode.sts.auth.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 登录配置（值对象）
 */
@Getter
@Builder
public class LoginConfigDto {

    private List<TenantContextDto> tenantContextDtoList;

}
