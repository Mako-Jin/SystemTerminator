package com.yaocode.sts.auth.domain.valueobjects;

import com.yaocode.sts.auth.domain.enums.GrantTypeEnums;
import lombok.Data;

/**
 * 凭证值对象
 * @author: Jin-LiangBo
 * @date: 2026年03月30日 16:56
 */
@Data
public abstract class AbstractAuthCredential implements Credential {

    protected GrantTypeEnums grantType;

    // protected ClientInfo clientInfo;
    //
    // protected DeviceInfo deviceInfo;
    //
    // protected SecurityParams securityParams;

}
