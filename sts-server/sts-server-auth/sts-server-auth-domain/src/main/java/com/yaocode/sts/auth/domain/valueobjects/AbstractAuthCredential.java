package com.yaocode.sts.auth.domain.valueobjects;

import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import lombok.Getter;

/**
 * 凭证值对象
 * @author: Jin-LiangBo
 * @date: 2026年03月30日 16:56
 */
@Getter
public abstract class AbstractAuthCredential implements Credential {

    protected final GrantTypeEnums grantType;

     protected final ClientId clientId;

     protected final DeviceId deviceId;

    public AbstractAuthCredential(GrantTypeEnums grantType, ClientId clientId, DeviceId deviceId) {
        this.grantType = grantType;
        this.clientId = clientId;
        this.deviceId = deviceId;
    }

    /**
     * 凭证校验 - 子类实现具体校验逻辑
     */
    public abstract void validate();

    public void destroy() {}

    /**
     * 是否启用记住我功能
     * 子类可重写，默认 false
     */
    public boolean isRememberMe() {
        return false;
    }

    /**
     * 是否需要生成 Refresh Token
     * 默认 true，子类可重写
     */
    public boolean needRefreshToken() {
        return true;
    }

}
