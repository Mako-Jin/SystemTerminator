package com.yaocode.sts.auth.domain.valueobjects.composites;

import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import com.yaocode.sts.auth.domain.valueobjects.AbstractAuthCredential;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import lombok.Getter;

@Getter
public class RememberAuthCredential extends AbstractAuthCredential {

    /**
     * 用户名
     */
    private final String rememberMeToken;

    public RememberAuthCredential(String rememberMeToken, ClientId clientId, DeviceId deviceId) {
        super(GrantTypeEnums.REMEMBER_ME, clientId, deviceId);
        this.rememberMeToken = rememberMeToken;
    }

    @Override
    public void validate() {

    }
}
