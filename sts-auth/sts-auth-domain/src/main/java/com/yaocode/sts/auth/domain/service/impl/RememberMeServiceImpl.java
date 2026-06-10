package com.yaocode.sts.auth.domain.service.impl;

import com.yaocode.sts.auth.domain.entity.RememberMeTokenEntity;
import com.yaocode.sts.auth.domain.port.JwtTokenPort;
import com.yaocode.sts.auth.domain.repository.RememberMeRepository;
import com.yaocode.sts.auth.domain.service.RememberMeService;
import com.yaocode.sts.auth.domain.valueobjects.composites.JwtPayload;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.ClientId;
import com.yaocode.sts.auth.domain.valueobjects.identifiers.DeviceId;
import com.yaocode.sts.auth.domain.valueobjects.primitives.Username;
import com.yaocode.sts.common.domain.valueobject.UserId;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RememberMeServiceImpl implements RememberMeService {

    @Resource
    private JwtTokenPort jwtTokenPort;

    @Resource
    private RememberMeRepository rememberMeRepository;

    public RememberMeTokenEntity create(
            UserId userId, Username username, ClientId clientId, DeviceId deviceId, int validityDays
    ) {
        Instant expiresAt = Instant.now().plusSeconds(validityDays * 86400L);

        // 2. 保存到数据库
        RememberMeTokenEntity token = new RememberMeTokenEntity(
                userId, username, clientId, deviceId, expiresAt
        );
        rememberMeRepository.save(token);

        // 3. 通过 Port 生成 JWT
//        JwtPayload payload = JwtPayload.builder()
//                .value(token.getId())
//                .userId(userId)
//                .username(username)
//                .clientId(clientId)
//                .deviceId(deviceId)
//                .issuedAt(Instant.now())
//                .expiresAt(expiresAt)
//                .build();
//
//        return jwtTokenPort.generateRememberMeToken(payload);
        return null;
    }

    @Override
    public RememberMeTokenEntity validate(String jwtValue, String clientId, String deviceId) {
        return null;
    }

    @Override
    public RememberMeTokenEntity renew(RememberMeTokenEntity token, int validityDays) {
        return null;
    }

    @Override
    public void revoke(String username) {

    }
}
