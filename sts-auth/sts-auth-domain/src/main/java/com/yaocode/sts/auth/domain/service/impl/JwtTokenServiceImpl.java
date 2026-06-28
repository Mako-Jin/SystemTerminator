package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.RefreshTokenEntity;
import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.entity.UserInfoEntity;
import com.yaocode.sts.auth.domain.enums.TokenTypeEnums;
import com.yaocode.sts.auth.domain.port.JwtTokenConfigPort;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.repository.RefreshTokenRepository;
import com.yaocode.sts.auth.domain.repository.RememberMeTokenRepository;
import com.yaocode.sts.auth.domain.service.JwtTokenService;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.TokenId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.IpAddress;
import com.yaocode.sts.common.tools.id.IdFactory;
import com.yaocode.sts.common.tools.id.IdGeneratorType;
import com.yaocode.sts.common.web.context.RequestContextHolder;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenPort accessTokenPort;
    private final JwtTokenPort refreshTokenPort;
    private final JwtTokenPort rememberMeTokenPort;
    private final JwtTokenPort stateTokenPort;

    @Resource
    private RefreshTokenRepository refreshTokenRepository;

    @Resource
    private RememberMeTokenRepository rememberMeTokenRepository;

    @Resource
    private JwtTokenConfigPort jwtTokenConfigPort;

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
    public String generateRefreshToken(UserInfoEntity userInfoEntity, ClientId clientId, DeviceId deviceId, TokenId refreshTokenId, String jti) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", userInfoEntity.getId().getValue());
        payload.put("refreshTokenId", refreshTokenId.getValue());
        payload.put("clientId", clientId.getValue());
        payload.put("deviceId", deviceId.getValue());
        payload.put("tokenType", TokenTypeEnums.REFRESH_TOKEN.getTokenType());
        payload.put("jti", jti);
        String refreshToken = refreshTokenPort.generate(payload);
        // 2. 保存到数据库
        RefreshTokenEntity entity = RefreshTokenEntity.create(
                userInfoEntity.getUserId(),
                clientId,
                deviceId,
                jti,
                refreshToken,
                Instant.now().plusSeconds(jwtTokenConfigPort.getRefreshTokenTtl())
        );
        refreshTokenRepository.save(entity);
        return refreshToken;
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
        String rememberMeToken = rememberMeTokenPort.generate(payload);
        // 2. 保存到数据库
        RememberMeTokenEntity entity = RememberMeTokenEntity.create(
                userInfoEntity.getUserId(),
                userInfoEntity.getUsername(),
                clientId,
                deviceId,
                rememberMeToken,
                series,
                Instant.now().plusSeconds(jwtTokenConfigPort.getRememberMeTtl()),
                IpAddress.of(RequestContextHolder.getIpAddress()),
                RequestContextHolder.getUserAgent()
        );
        rememberMeTokenRepository.save(entity);
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

    @Override
    public JwtPayload parseRememberMe(String token) {
        // 1. 基本校验
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token不能为空");
        }

        // 2. 使用 RememberMe Token Port 解析
        JwtPayload payload = rememberMeTokenPort.parse(token);
        if (payload == null) {
            throw new IllegalArgumentException("Token解析失败");
        }

        // 3. 校验 Token 类型必须是 REMEMBER_ME
        if (payload.getTokenType() != TokenTypeEnums.REMEMBER_ME) {
            throw new IllegalArgumentException("Token类型不匹配，期望 REMEMBER_ME");
        }

        // 4. 校验是否过期
        if (payload.isExpired()) {
            throw new IllegalArgumentException("Token已过期");
        }

        return payload;
    }

}
