package com.yaocode.sts.auth.interfaces.model.params.login;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.yaocode.sts.auth.domain.valueobjects.Credential;
import lombok.Data;

/**
 * 认证接口多态参数化
 * @author: Jin-LiangBo
 * @date: 2026年04月01日 11:09
 */
@Data
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "grantType",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PasswordLoginCredential.class, name = "password"),
        @JsonSubTypes.Type(value = MobileLoginCredential.class, name = "mobile"),
})
public abstract class AbstractLoginCredential implements Credential {

    private String grantType;

}
