package com.yaocode.sts.auth.infrastructure.port;

import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Component
public class Hmac512JwtAdapter implements JwtTokenPort {

    @Override
    public String generate(Map<String, Object> payload, Duration ttl) {
        return "";
    }

    @Override
    public boolean verify(String jwt) {
        return false;
    }

    @Override
    public JwtPayload parse(String jwt) {
        return null;
    }

    @Override
    public JwtPayload verifyAndParse(String jwt) {
        return null;
    }

    @Override
    public String generateRememberMeToken(JwtPayload payload) {
        return "";
    }

    @Override
    public JwtPayload verifyAndParseRememberMe(String jwt) {
        return null;
    }

    @Override
    public String generateStateToken(JwtPayload payload) {
        return "";
    }

    @Override
    public JwtPayload verifyAndParseState(String jwt) {
        return null;
    }
}
