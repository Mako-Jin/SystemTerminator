package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenPort accessTokenPort;
    private final JwtTokenPort refreshTokenPort;
    private final JwtTokenPort rememberMeTokenPort;
    private final JwtTokenPort stateTokenPort;

    public JwtTokenServiceImpl(
            @Qualifier("accessTokenPort") JwtTokenPort accessTokenPort,
            @Qualifier("refreshTokenPort") JwtTokenPort refreshTokenPort,
            @Qualifier("rememberMeTokenPort") JwtTokenPort rememberMeTokenPort,
            @Qualifier("stateTokenPort") JwtTokenPort stateTokenPort
    ) {
        this.accessTokenPort = accessTokenPort;
        this.refreshTokenPort = refreshTokenPort;
        this.rememberMeTokenPort = rememberMeTokenPort;
        this.stateTokenPort = stateTokenPort;
    }


    @Override
    public String generateAccessToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoEntity.getId().getValue());
        payload.put("clientId", clientId);
        payload.put("deviceId", deviceId);
        payload.put("tokenType", TokenTypeEnums.ACCESS_TOKEN.getTokenType());
        payload.put("jti", IdFactory.generate(IdGeneratorType.UUID).toString());
        return accessTokenPort.generate(payload);
    }

    @Override
    public String generateRefreshToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId, TokenId refreshTokenId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoEntity.getId().getValue());
        payload.put("refreshTokenId", refreshTokenId.getValue());
        payload.put("clientId", clientId.getValue());
        payload.put("deviceId", deviceId.getValue());
        payload.put("tokenType", TokenTypeEnums.REFRESH_TOKEN.getTokenType());
        payload.put("jti", IdFactory.generate(IdGeneratorType.UUID).toString());
        return refreshTokenPort.generate(payload);
    }

    @Override
    public String generateRememberMeToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId, String series) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoEntity.getId().getValue());
        payload.put("clientId", clientId.getValue());
        payload.put("deviceId", deviceId.getValue());
        payload.put("series", series);
        payload.put("tokenType", TokenTypeEnums.REMEMBER_ME.getTokenType());
        payload.put("jti", IdFactory.generate(IdGeneratorType.UUID).toString());
        return rememberMeTokenPort.generate(payload);
    }

    @Override
    public String generateStateToken(String state, String redirectUri, ClientId clientId, DeviceId deviceId) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("state", state);
        payload.put("redirectUri", redirectUri);
        payload.put("clientId", clientId.getValue());
        payload.put("deviceId", deviceId.getValue());
        payload.put("tokenType", TokenTypeEnums.STATE_TOKEN.getTokenType());
        payload.put("jti", IdFactory.generate(IdGeneratorType.UUID).toString());
        return stateTokenPort.generate(payload);
    }
}
