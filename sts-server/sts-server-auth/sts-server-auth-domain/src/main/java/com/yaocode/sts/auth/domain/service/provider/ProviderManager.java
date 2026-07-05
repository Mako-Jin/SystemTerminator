package com.yaocode.sts.auth.domain.service.provider;

import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProviderManager {

    private final Map<GrantTypeEnums, AuthenticationProvider<AbstractAuthCredential>> providerMap;

    public ProviderManager(List<AuthenticationProvider<AbstractAuthCredential>> providers) {
        this.providerMap = providers.stream()
                .collect(Collectors.toMap(
                        AuthenticationProvider::getGrantType,
                        Function.identity()
                ));
    }

    /**
     * 根据授权类型获取对应的Provider
     */
    public AuthenticationProvider<AbstractAuthCredential> getProvider(GrantTypeEnums grantType) {
        return providerMap.get(grantType);
    }

    /**
     * 判断是否支持指定的授权类型
     */
    public boolean supports(GrantTypeEnums grantType) {
        return providerMap.containsKey(grantType);
    }

    /**
     * 获取所有已注册的授权类型
     */
    public List<GrantTypeEnums> getSupportedGrantTypes() {
        return providerMap.keySet().stream().toList();
    }
}
