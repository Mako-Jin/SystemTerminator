package com.yaocode.sts.auth.infrastructure.port;

import com.yaocode.sts.auth.domain.port.JwtTokenConfigPort;
import com.yaocode.sts.auth.infrastructure.config.properties.JwtTokenProperties;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenConfigAdapter implements JwtTokenConfigPort {
    private final JwtTokenProperties properties;

    public JwtTokenConfigAdapter(JwtTokenProperties properties) {
        this.properties = properties;
    }

    @Override
    public long getAccessTokenTtl() {
        return properties.getAccess().getTtl().getSeconds();
    }

    @Override
    public long getRefreshTokenTtl() {
        return properties.getRefresh().getTtl().getSeconds();
    }

    @Override
    public long getRememberMeTtl() {
        return properties.getRememberMe().getTtl().getSeconds();
    }

    @Override
    public long getStateTokenTtl() {
        return properties.getState().getTtl().getSeconds();
    }
}
