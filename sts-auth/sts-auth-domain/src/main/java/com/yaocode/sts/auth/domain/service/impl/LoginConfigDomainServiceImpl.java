package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.TenantConfigEntity;
import com.yaocode.sts.auth.domain.enums.LoginMethodEnums;
import com.yaocode.sts.auth.domain.repository.TenantConfigRepository;
import com.yaocode.sts.auth.domain.service.LoginConfigDomainService;
import com.yaocode.sts.common.basic.enums.OppositeEnums;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 登录配置领域服务
 * 职责：登录方式相关的业务规则
 */
@Service
public class LoginConfigDomainServiceImpl implements LoginConfigDomainService {

    @Resource
    private TenantConfigRepository tenantConfigRepository;

    /**
     * 获取支持的登录方式列表
     */
    public List<LoginMethodEnums> getSupportedLoginMethods(TenantConfigEntity tenantConfig) {
        List<LoginMethodEnums> methods = new ArrayList<>();

        if (Objects.isNull(tenantConfig)) {
            methods.add(LoginMethodEnums.PASSWORD);
            return methods;
        }

        if (Objects.equals(tenantConfig.getPasswordLoginEnabled(), OppositeEnums.YES)) {
            methods.add(LoginMethodEnums.PASSWORD);
        }
        if (Objects.equals(tenantConfig.getSmsLoginEnabled(), OppositeEnums.YES)) {
            methods.add(LoginMethodEnums.SMS);
        }
        if (Objects.equals(tenantConfig.getEmailLoginEnabled(), OppositeEnums.YES)) {
            methods.add(LoginMethodEnums.EMAIL);
        }
        if (Objects.equals(tenantConfig.getQrCodeLoginEnabled(), OppositeEnums.YES)) {
            methods.add(LoginMethodEnums.QRCODE);
        }

        if (methods.isEmpty()) {
            methods.add(LoginMethodEnums.PASSWORD);
        }
        return methods;
    }

    /**
     * 获取默认登录方式
     */
    public LoginMethodEnums getDefaultLoginMethod(TenantConfigEntity config) {
        if (config == null) {
            return LoginMethodEnums.PASSWORD;
        }

        if (Objects.equals(config.getQrCodeLoginEnabled(), OppositeEnums.YES)) {
            return LoginMethodEnums.QRCODE;
        }
        if (Objects.equals(config.getSmsLoginEnabled(), OppositeEnums.YES)) {
            return LoginMethodEnums.SMS;
        }
        if (Objects.equals(config.getEmailLoginEnabled(), OppositeEnums.YES)) {
            return LoginMethodEnums.EMAIL;
        }
        return LoginMethodEnums.PASSWORD;
    }

}
