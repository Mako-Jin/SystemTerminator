package com.yaocode.sts.auth.domain.service;

import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.enums.LoginMethodEnums;

import java.util.List;

public interface LoginConfigDomainService {

    List<LoginMethodEnums> getSupportedLoginMethods(TenantConfigEntity tenantConfig);

    LoginMethodEnums getDefaultLoginMethod(TenantConfigEntity config);

}
